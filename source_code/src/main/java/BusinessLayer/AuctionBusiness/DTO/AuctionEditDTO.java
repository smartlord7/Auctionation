package BusinessLayer.AuctionBusiness.DTO;

import java.sql.Timestamp;

public class AuctionEditDTO {
    private Long auctionId;
    private String description;
    private Float currentBidValue,
            initialValue;
    private Integer CurrentState;
    private Timestamp startTimestamp,
            expirationTimestamp,
            endTimestamp;


}
