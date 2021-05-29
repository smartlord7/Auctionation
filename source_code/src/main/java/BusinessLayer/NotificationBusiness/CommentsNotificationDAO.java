package BusinessLayer.NotificationBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class CommentsNotificationDAO<CommentsNotificationEditDTO> extends BaseDAO {
    public CommentsNotificationDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
