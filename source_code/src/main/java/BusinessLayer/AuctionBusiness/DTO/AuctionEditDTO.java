package BusinessLayer.AuctionBusiness.DTO;

import BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

public class AuctionEditDTO extends BaseEditDTO {
    public Integer auctionId;
    public String description;
    public Float currentBidValue,
            initialValue;
    public Integer currentState;
    public Timestamp startTimestamp,
            expirationTimestamp,
            endTimestamp;
}
