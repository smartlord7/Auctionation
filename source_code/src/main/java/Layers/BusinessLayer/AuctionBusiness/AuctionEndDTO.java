package Layers.BusinessLayer.AuctionBusiness;

public class AuctionEndDTO {
    public String username;
    public float bidValue;
    public int bidId, auctionId, userId;

    public AuctionEndDTO(String username, float bidValue, int userId, int bidId, int auctionId) {
        this.username = username;
        this.bidValue = bidValue;
        this.userId = userId;
        this.bidId = bidId;
        this.auctionId = auctionId;
    }

    public String toString() {
        return "username: " + username + "\nbidvalue: " + bidValue + "\nuserid: " + userId;
    }

}
