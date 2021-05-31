package Layers.BusinessLayer.BidBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

public class BidEditDTO extends BaseEditDTO {
    public String description;
    public Float amount;
    public int userId, auctionId;
}
