package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

/**
 * @Class DTO object used to store and transfer data of edited data about a certain auction.
 */
public class AuctionEditDTO extends BaseEditDTO {
    public Integer hostUserId;
    public String auctionName, description, itemEAN, itemName, itemDescription, itemOrigin;
    public Float initialValue;
    public Timestamp expirationTimestamp;

    /**
     * Converts a AuctionEditDTO to a string.
     * @return String with the attributes and their corresponding values.
     */
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
                ", expirationTimestamp=" + expirationTimestamp +
                '}';
    }
}
