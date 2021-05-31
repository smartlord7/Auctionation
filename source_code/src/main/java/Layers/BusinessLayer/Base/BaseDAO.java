package Layers.BusinessLayer.Base;

import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO<BaseEditDTO, BaseListDTO> {
    protected final String tableName;
    protected final boolean auditable;
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    public BaseDAO(String tableName, boolean auditable) {
        this.tableName = tableName;
        this.auditable = auditable;
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

        sb.append(tableName)
                .append(" SET deleteTimestamp = ? WHERE ")
                .append(tableName)
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean updateById(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto, int id) {
        validateClass(dto);
        Class<?> dtoClass = dto.getClass();
        int i;
        Field[] fields;
        StringBuilder sb = new StringBuilder("UPDATE ");
        fields = dtoClass.getDeclaredFields();
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;

        sb.append(tableName).append( " SET ");

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(" = ?,");
        }

        sb.append(fields[i].getName())
                .append("= ? ");
        if (auditable) {
            sb.append(",updateTimestamp = ? ");
        }
        sb.append("WHERE ")
                .append(tableName)
                .append("Id = ?");

        try {
            ps = conn.prepareStatement(sb.toString());
            for (i = 0; i < fields.length; i++) {
                ps.setObject(i + 1, fields[i].get(dto));
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
            e.printStackTrace();
        }

        return false;
    }

    public Layers.BusinessLayer.Base.DTO.BaseEditDTO create(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto) {
        validateClass(dto);
        Class<?> dtoClass = dto.getClass();
        Field[] fields;
        int i;
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        fields = dtoClass.getDeclaredFields();
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;

        sb.append(tableName).append(" (");

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }

        sb.append(fields[i].getName());
        if (auditable) {
            sb.append(", createTimestamp, updateTimestamp");
        }
        sb.append(")");

        sb.append(" VALUES ( ");
        for (i = 0; i < fields.length - 1; i++) {
            sb.append("?, ");
        }

        sb.append("?");
        if (auditable) {
            sb.append(", ?, ?");
        }
        sb.append(")");

        try {
            ps = conn.prepareStatement(sb.toString());
            for (i = 0; i < fields.length; i++) {
                try {
                    ps.setObject(i + 1, fields[i].get(dto));
                } catch (SQLException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (auditable) {
                ps.setObject(++i, Timestamp.from(Instant.now()));
                ps.setObject(++i, Timestamp.from(Instant.now()));
            }

            int affectedRows = ps.executeUpdate();
            saveChanges(conn);

            if (affectedRows == 1) {
                logger.info("New entry on table " + tableName + " successfully created!");
                return dto;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return result;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isAuditable() {
        return auditable;
    }
}
