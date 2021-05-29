package Layers.BusinessLayer.Base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO<BaseEditDTO, BaseListDTO> {
    protected final Connection conn;
    protected final String tableName;
    protected final boolean auditable;
    private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    public BaseDAO(Connection conn, String tableName, boolean auditable) {
        this.conn = conn;
        this.tableName = tableName;
        this.auditable = auditable;
    }

    protected void saveChanges() throws SQLException {
        try {
            conn.commit();
        } catch (SQLException ex1) {
            conn.rollback();
            conn.close();
        }
    }

    public boolean deleteById(int id) throws SQLException {
        StringBuilder sb = new StringBuilder("UPDATE ");

        sb.append(tableName)
                .append(" SET deleteTimestamp = ? WHERE ")
                .append(tableName)
                .append("Id = ? ");

        PreparedStatement ps = conn.prepareStatement(sb.toString());
        ps.setTimestamp(1, Timestamp.from(Instant.now()));
        ps.setInt(2, id);


        int numAffectedRows = ps.executeUpdate();

        if (numAffectedRows == 1) {
            logger.info("Entry on table " + tableName + " successfully soft deleted!");
            saveChanges();
            return true;
        }

        return false;
    }

    public boolean updateById(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto) throws SQLException, IllegalAccessException {
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> expectedClass = (Class<?>) (dtoType).getActualTypeArguments()[0], dtoClass = dto.getClass();

        if (expectedClass != dtoClass) {
            throw new ClassCastException("Expected class: " + expectedClass + ", have: " + dtoClass);
        }

        int i;
        Field[] fields;
        StringBuilder sb = new StringBuilder("UPDATE ");
        fields = dtoClass.getDeclaredFields();
        PreparedStatement ps;

        sb.append(tableName).append( " SET ");

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(" = ?,");
        }

        sb.append(fields[i].getName())
                .append("= ?,updateTimestamp = ? WHERE ")
                .append(tableName)
                .append("Id = ?");

        ps = conn.prepareStatement(sb.toString());
        for (i = 0; i < fields.length; i++) {
            ps.setObject(i + 1, fields[i].get(dto));
        }

        ps.setObject(++i, Timestamp.from(Instant.now()));
        ps.setObject(++i, dto.id);

        int numAffectedRows = ps.executeUpdate();
        if (numAffectedRows == 1) {
            logger.info("Entry on table " + tableName + " successfully updated!");
            saveChanges();

            return true;
        }

        return false;
    }

    public Layers.BusinessLayer.Base.DTO.BaseEditDTO create(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto) throws SQLException, IllegalAccessException {
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> expectedClass = (Class<?>) (dtoType).getActualTypeArguments()[0], dtoClass = dto.getClass();

        if (expectedClass != dtoClass) {
            throw new ClassCastException("Expected class: " + expectedClass + ", have: " + dtoClass);
        }

        int i;
        Field[] fields;
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        fields = dtoClass.getDeclaredFields();
        PreparedStatement ps;

        sb.append(tableName).append(" (");

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }

        sb.append(fields[i].getName());
        if (auditable) {
            sb.append(", createTimestamp, updateTimestamp, deleteTimestamp");
        }
        sb.append(")");

        sb.append(" VALUES ( ");
        for (i = 0; i < fields.length - 1; i++) {
            sb.append("?, ");
        }

        sb.append("?");
        if (auditable) {
            sb.append(", ?, ?, ?");
        }
        sb.append(")");

        ps = conn.prepareStatement(sb.toString());
        for (i = 0; i < fields.length; i++) {
            ps.setObject(i + 1, fields[i].get(dto));
        }

        if (auditable) {
            ps.setObject(++i, Timestamp.from(Instant.now()));
            ps.setObject(++i, Timestamp.from(Instant.now()));
            ps.setObject(++i, null);
        }

        int numAffectedRows = ps.executeUpdate();

        if (numAffectedRows == 1) {
            logger.info("New entry on table " + tableName + " successfully created!");
            saveChanges();
            return dto;
        }

        return null;
    }

    public List<BaseListDTO> getAll(String propertyName, Object propertyValue) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        int i;
        List<BaseListDTO> result = new ArrayList<>();
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<BaseListDTO> dtoClass = (Class<BaseListDTO>) (dtoType).getActualTypeArguments()[1];

        StringBuilder sb = new StringBuilder("SELECT ");
        Field[] fields = dtoClass.getDeclaredFields();
        PreparedStatement ps;
        ResultSet rows;

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }
        sb.append(fields[i].getName()).append(" FROM ").append(tableName);

        if (propertyName != null) {
            propertyValue = propertyValue == null ? "NULL" : propertyValue;

            sb.append(" WHERE ").append(propertyName).append(" = ?");
        }

        ps = conn.prepareStatement(sb.toString());

        if (propertyName != null) {
            ps.setObject(1, propertyValue);
        }

        rows = ps.executeQuery();
        BaseListDTO dto = dtoClass.newInstance();

        while (rows.next()) {
            for (i = 0; i < fields.length; i++) {
                fields[i].set(dto, rows.getObject(i + 1));

            }
            result.add(dto);
        }

        return result;
    }

    public Connection getConn() {
        return conn;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isAuditable() {
        return auditable;
    }
}