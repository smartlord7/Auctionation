package BusinessLayer.AuctionEditHistoryBusiness;

import BusinessLayer.Base.DTO.BaseEditDTO;

public class AuctionEditHistoryEditDTO extends BaseEditDTO {

    public Integer version;
    public String auctionName,
            description,
            itemName,
            itemDescription,
            itemOrigin,
            editTimestamp;
}
