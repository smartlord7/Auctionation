package BusinessLayer.NotificationBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionNotificationDAO<AuctionNotificationEditDTO> extends BaseDAO {
    public AuctionNotificationDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}