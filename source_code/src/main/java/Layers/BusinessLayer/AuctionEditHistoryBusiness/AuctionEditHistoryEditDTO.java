package Layers.BusinessLayer.AuctionEditHistoryBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

public class AuctionEditHistoryEditDTO extends BaseEditDTO {

    public Integer version;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin,
            editTimestamp;
}
