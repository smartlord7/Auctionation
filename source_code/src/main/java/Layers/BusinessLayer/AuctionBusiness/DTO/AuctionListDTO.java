package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

public class AuctionListDTO extends BaseListDTO {
    public Integer auctionId;
    public String description;

    public AuctionListDTO() {
    }

    public AuctionListDTO(Integer auctionId, String description) {
        this.auctionId = auctionId;
        this.description = description;
    }
}
