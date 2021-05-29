package BusinessLayer.AuctionBusiness;

import BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {
    public AuctionDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
