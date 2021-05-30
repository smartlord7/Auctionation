package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

public class AuctionEditDTO extends BaseEditDTO {
    public String auctionName, description, itemEAN, itemName, itemDescription, itemOrigin;
    public Float initialValue;
    public Timestamp startTimestamp,
            expirationTimestamp;
}
