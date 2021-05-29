package BusinessLayer.AuctionBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionDAO<AuctionEditDTO, AuctionListDTO> extends BaseDAO {
    public AuctionDAO(Connection conn) {
        super(conn);
    }
}
