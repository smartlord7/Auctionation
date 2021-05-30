package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class AuctionEditHistoryDAO extends BaseDAO<AuctionEditHistoryEditDTO,AuctionEditHistoryListDTO>{
    public AuctionEditHistoryDAO(Connection conn) {
        super("AuctionEditHistory", false);
    }
}
