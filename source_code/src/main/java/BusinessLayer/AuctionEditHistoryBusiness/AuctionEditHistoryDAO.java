package BusinessLayer.AuctionEditHistoryBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionEditHistoryDAO<AuctionEditHistoryEditDTO,AuctionEditHistoryListDTO> extends BaseDAO{
    public AuctionEditHistoryDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
