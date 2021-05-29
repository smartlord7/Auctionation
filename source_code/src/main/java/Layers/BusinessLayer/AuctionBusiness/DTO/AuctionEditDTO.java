package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

public class AuctionEditDTO extends BaseEditDTO {
    public String auctionName, description, itemEAN, itemDescription, itemOrigin;
    public Float currentBidValue,
            initialValue;
    public Integer currentState;
    public Timestamp startTimestamp,
            expirationTimestamp,
            endTimestamp;
}
