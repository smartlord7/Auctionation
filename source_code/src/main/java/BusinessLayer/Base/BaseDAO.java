package BusinessLayer.Base;

import BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import BusinessLayer.Base.DTO.BaseListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO<BaseEditDTO, BaseListDTO> {
    private final Connection conn;
    private final String tableName;
    private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    public BaseDAO(Connection conn, String tableName) {
        this.conn = conn;
        this.tableName = tableName;
    }

    public void delete(int id) throws SQLException {
        StringBuilder sb = new StringBuilder("UPDATE ");

        sb.append(tableName)
                .append(" SET deleteTimestamp = ? WHERE ")
                .append(tableName)
                .append("Id = ? ");

        PreparedStatement ps = conn.prepareStatement(sb.toString());
        ps.setTimestamp(1, Timestamp.from(Instant.now()));
        ps.setInt(2, id);


        int numAffectedRows = ps.executeUpdate();
        conn.commit();

        if (numAffectedRows == 1) {
            logger.info("Entry on table " + tableName + " successfully soft deleted!");
        }
    }

    public void create(BusinessLayer.Base.DTO.BaseEditDTO dto) throws SQLException, IllegalAccessException {
        int i;
        Field[] fields;
        Class<?> dtoClass = dto.getClass();
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        fields = dtoClass.getDeclaredFields();

        sb.append(tableName).append(" VALUES (");

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }
        sb.append(fields[i].getName()).append(", createTimestamp, updateTimestamp, deleteTimestamp)");

        sb.append("( ");
        for (i = 0; i < fields.length - 1; i++) {
            sb.append("?, ");
        }
        sb.append("?, ?, ?, ?)");

        PreparedStatement ps = conn.prepareStatement(sb.toString());
        for (i = 0; i < fields.length; i++) {
            ps.setObject(i + 1, fields[i].get(dto));
        }

        ps.setObject(++i, Timestamp.from(Instant.now()));
        ps.setObject(++i, Timestamp.from(Instant.now()));
        ps.setObject(++i, null);

        int numAffectedRows = ps.executeUpdate();
        conn.commit();

        if (numAffectedRows == 1) {
            logger.info("New entry on table " + tableName + " successfully created!");
        }
    }

    public List<BaseListDTO> list() throws SQLException, IllegalAccessException, InstantiationException {
        int i;
        List<BaseListDTO> result = new ArrayList<>();
        final ParameterizedType dtoType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<BaseListDTO> dtoClass = (Class<BaseListDTO>) (dtoType).getActualTypeArguments()[1];

        StringBuilder sb = new StringBuilder("SELECT ");
        Field[] fields = dtoClass.getDeclaredFields();
        ResultSet rows;

        for (i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(", ");
        }
        sb.append(fields[i].getName()).append(" FROM ").append(tableName);

        PreparedStatement ps = conn.prepareStatement(sb.toString());
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
}
