package BusinessLayer.NotificationBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class NotificationDAO<NotificationEditDTO> extends BaseDAO {
    public NotificationDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}