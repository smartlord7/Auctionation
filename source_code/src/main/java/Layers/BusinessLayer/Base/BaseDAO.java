package Layers.BusinessLayer.Base;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.ExceptionBusiness.DTO.ExceptionEditDTO;
import Layers.BusinessLayer.ExceptionBusiness.ExceptionDAO;
import Startup.ConnectionFactory;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDAO<BaseEditDTO, BaseListDTO> {
    protected final String tableName;
    protected final boolean auditable;
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);
    protected ErrorResponse error;

    public BaseDAO(String tableName, boolean auditable) {
        this.tableName = tableName;
        this.auditable = auditable;
        this.error = new ErrorResponse();
    }

    protected void auditError(Exception e, Connection conn) {
        e.printStackTrace();
        if (e instanceof SQLException) {
            logger.error(e.getMessage());

            SQLException sqlEx = (SQLException) e;
            ExceptionDAO exDAO = new ExceptionDAO(conn);
            ExceptionEditDTO dto = new ExceptionEditDTO(sqlEx.getErrorCode(), sqlEx.getSQLState(), sqlEx.getMessage(), Timestamp.from(Instant.now()));
            exDAO.create(dto);
            error = new ErrorResponse(sqlEx.getSQLState(), sqlEx.getMessage());
        } else {
            error = new ErrorResponse(e.getCause().getMessage(), e.getMessage());
        }

        try {
            conn.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    protected void saveChanges(Connection conn) throws SQLException {
        try {
            conn.commit();
        } catch (SQLException ex1) {
            conn.rollback();
        }
        conn.close();
    }

    public boolean deleteById(int id) {
        Connection conn = ConnectionFactory.getConnection();
        StringBuilder sb = new StringBuilder("UPDATE ");

        StringBuilder append = sb.append(tableName)
                .append(" SET deleteTimestamp = ? WHERE ")
                .append(tableName.replace("\"", ""))
                        .append("Id = ? ");

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sb.toString());
            ps.setTimestamp(1, Timestamp.from(Instant.now()));
            ps.setInt(2, id);


            int numAffectedRows = ps.executeUpdate();
            saveChanges(conn);

            if (numAffectedRows == 1) {
                logger.info("Entry on table " + tableName + " successfully soft deleted!");
                return true;
            }

        } catch (SQLException e) {
            auditError(e, conn);
        }
        return false;
    }

    public boolean updateById(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto, int id) {
        validateClass(dto);
        int i;
        List<Field> fields;
        StringBuilder sb = new StringBuilder("UPDATE ");
        fields = Arrays.stream(dto.getClass().getDeclaredFields()).filter(filter -> !filter.isAnnotationPresent(InsertIgnore.class)).collect(Collectors.toList());
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;

        sb.append(tableName).append( " SET ");

        for (i = 0; i < fields.size() - 1; i++) {
            sb.append(fields.get(i).getName()).append(" = ?,");
        }

        sb.append(fields.get(i).getName())
                .append("= ? ");
        if (auditable) {
            sb.append(",updateTimestamp = ? ");
        }
        sb.append("WHERE ")
                .append(tableName.replace("\"", ""))
                .append("Id = ?");

        try {
            ps = conn.prepareStatement(sb.toString());
            for (i = 0; i < fields.size(); i++) {
                ps.setObject(i + 1, fields.get(i).get(dto));
            }

            if (auditable) {
                ps.setObject(++i, Timestamp.from(Instant.now()));
            }
            ps.setObject(++i, id);

            int numAffectedRows = ps.executeUpdate();
            if (numAffectedRows == 1) {
                logger.info("Entry on table " + tableName + " successfully updated!");
                saveChanges(conn);

                return true;
            }
        } catch (SQLException | IllegalAccessException e) {
            auditError(e, conn);
        }

        return false;
    }

    public Layers.BusinessLayer.Base.DTO.BaseEditDTO create(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto) {
        validateClass(dto);
        List<Field> fields;
        int i;
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        fields = Arrays.stream(dto.getClass().getDeclaredFields()).filter(filter -> !filter.isAnnotationPresent(InsertIgnore.class)).collect(Collectors.toList());
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;

        sb.append(tableName).append(" (");

        for (i = 0; i < fields.size() - 1; i++) {
            sb.append(fields.get(i).getName()).append(", ");
        }

        sb.append(fields.get(i).getName());
        if (auditable) {
            sb.append(", createTimestamp, updateTimestamp");
        }
        sb.append(")");

        sb.append(" VALUES ( ");
        for (i = 0; i < fields.size() - 1; i++) {
            sb.append("?, ");
        }

        sb.append("?");
        if (auditable) {
            sb.append(", ?, ?");
        }
        sb.append(")");

        try {
            ps = conn.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            for (i = 0; i < fields.size(); i++) {
                try {
                    ps.setObject(i + 1, fields.get(i).get(dto));
                } catch (SQLException | IllegalAccessException e) {
                    auditError(e, conn);
                    return null;
                }
            }
            if (auditable) {
                ps.setObject(++i, Timestamp.from(Instant.now()));
                ps.setObject(++i, Timestamp.from(Instant.now()));
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 1) {
                logger.info("New entry on table " + tableName + " successfully created!");
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    dto.id = rs.getInt(1);
                }

                saveChanges(conn);

                return dto;
            }

        } catch (SQLException e) {
            auditError(e, conn);
        }

        return null;
    }

    private void validateClass(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto) {
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> expectedClass = (Class<?>) (dtoType).getActualTypeArguments()[0], dtoClass = dto.getClass();

        if (expectedClass != dtoClass) {
            throw new ClassCastException("Expected class: " + expectedClass + ", have: " + dtoClass);
        }
    }

    @SuppressWarnings("unchecked")
    public List<BaseListDTO> getbyProp(String propertyName, String propertyValue) {
        int i;
        List<BaseListDTO> result = new ArrayList<>();
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<BaseListDTO> dtoClass = (Class<BaseListDTO>) (dtoType).getActualTypeArguments()[1];

        StringBuilder sb = new StringBuilder("SELECT ");
        Field[] fields = dtoClass.getDeclaredFields();
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rows;

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }
        sb.append(fields[i].getName()).append(" FROM ").append(tableName);

        if (propertyName != null) {
            propertyValue = propertyValue == null ? "NULL" : propertyValue;

            sb.append(" WHERE ").append(propertyName)
                    .append(" = '")
                    .append(propertyValue)
                    .append("'");
            if (auditable) {
                sb.append(" AND deleteTimestamp IS NULL");
            }
        }

        try {
            ps = conn.prepareStatement(sb.toString());
            rows = ps.executeQuery();

            while (rows.next()) {
                BaseListDTO dto = dtoClass.newInstance();
                for (i = 0; i < fields.length; i++) {
                    fields[i].set(dto, rows.getObject(i + 1));

                }
                result.add(dto);
            }

            return result;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            auditError(e, conn);
        }

        return result;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isAuditable() {
        return auditable;
    }

    public ErrorResponse getError() {
        return error;
    }
}
