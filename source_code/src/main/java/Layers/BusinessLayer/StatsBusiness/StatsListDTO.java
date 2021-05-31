package Layers.BusinessLayer.StatsBusiness;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.util.ArrayList;
import java.util.List;

public class StatsListDTO extends BaseListDTO {
    public ArrayList<UserAuction> topAuctionOpeners, topAuctionWinners;
    public int totalLastAuctions;

    public StatsListDTO() {
        topAuctionOpeners = new ArrayList<UserAuction>();
        topAuctionWinners = new ArrayList<UserAuction>();
        totalLastAuctions=0;
    }

    public static class UserAuction{
        public int userId, auctionCount;

        public UserAuction(int userId, int auctionCount) {
            this.userId = userId;
            this.auctionCount = auctionCount;
        }
    }
}
