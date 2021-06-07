package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

/**
 * DTO object used to store and transfer data about an old version of an auction.
 */
public class AuctionEditHistoryEditDTO extends BaseEditDTO {

    public Integer version, auctionId;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin;
    public Timestamp editTimestamp;
}
