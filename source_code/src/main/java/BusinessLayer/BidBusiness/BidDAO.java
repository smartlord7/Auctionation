package BusinessLayer.BidBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class BidDAO <BidEditDTO, BidListDTO> extends BaseDAO{

    public BidDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
