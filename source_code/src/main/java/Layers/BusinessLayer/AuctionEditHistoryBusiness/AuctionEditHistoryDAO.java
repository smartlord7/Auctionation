package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

public class AuctionEditHistoryDAO extends BaseDAO<AuctionEditHistoryEditDTO,AuctionEditHistoryListDTO>{
    public AuctionEditHistoryDAO() {
        super("AuctionEditHistory", false);
    }

}
