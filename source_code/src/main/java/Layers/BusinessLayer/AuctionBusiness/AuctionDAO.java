package Layers.BusinessLayer.AuctionBusiness;

import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {
    public AuctionDAO(Connection conn) {
        super(conn, "Auction", true);
    }
}
