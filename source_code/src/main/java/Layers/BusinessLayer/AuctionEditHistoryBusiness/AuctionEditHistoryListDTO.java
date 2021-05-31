package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.sql.Timestamp;

public class AuctionEditHistoryListDTO extends BaseListDTO {
    public Integer version;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin;
    public Timestamp editTimestamp;
}
