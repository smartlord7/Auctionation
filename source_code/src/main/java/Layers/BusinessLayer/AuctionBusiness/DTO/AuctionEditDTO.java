package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.math.BigInteger;
import java.sql.Timestamp;

public class AuctionEditDTO extends BaseEditDTO {
    public Integer hostUserId;
    public String auctionName, description, itemEAN, itemName, itemDescription, itemOrigin;
    public Float initialValue;
    public Timestamp startTimestamp,
            expirationTimestamp;

    @Override
    public String toString() {
        return "AuctionEditDTO{" +
                "hostUserId=" + hostUserId +
                ", auctionName='" + auctionName + '\'' +
                ", description='" + description + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemOrigin='" + itemOrigin + '\'' +
                ", itemEAN=" + itemEAN +
                ", initialValue=" + initialValue +
                ", startTimestamp=" + startTimestamp +
                ", expirationTimestamp=" + expirationTimestamp +
                '}';
    }
}
