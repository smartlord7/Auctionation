package BusinessLayer.AuctionBusiness.DTO;

import BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

public class AuctionEditDTO extends BaseEditDTO {
    private Long auctionId;
    private String description;
    private Float currentBidValue,
            initialValue;
    private Integer currentState;
    private Timestamp startTimestamp,
            expirationTimestamp,
            endTimestamp;

    @Override
    public String getTableName() {
        return "Auction";
    }
}
