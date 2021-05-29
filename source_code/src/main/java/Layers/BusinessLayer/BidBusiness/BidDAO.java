package Layers.BusinessLayer.BidBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;

import java.sql.Connection;

public class BidDAO extends BaseDAO<BidEditDTO, BidListDTO>{

    public BidDAO(Connection conn) {
        super(conn, "Bid", true);
    }
}
