package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

/**
 * DAO object used to access the history of the auctions.
 */
public class AuctionEditHistoryDAO extends BaseDAO<AuctionEditHistoryEditDTO,AuctionEditHistoryListDTO>{
    public AuctionEditHistoryDAO() {
        super("AuctionEditHistory", false);
    }

}
