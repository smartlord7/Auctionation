package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.sql.Timestamp;

/**
 * DTO object used to store and transfer data about an old version of an auction when a listing operation is required.
 */
public class AuctionEditHistoryListDTO extends BaseListDTO {
    public Integer version;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin;
    public Timestamp editTimestamp;
}
