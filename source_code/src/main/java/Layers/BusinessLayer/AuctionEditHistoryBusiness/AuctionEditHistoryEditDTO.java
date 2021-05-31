package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

public class AuctionEditHistoryEditDTO extends BaseEditDTO {

    public Integer version, auctionId;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin;
    public Timestamp editTimestamp;
}
