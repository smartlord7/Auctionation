package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

/**
 * @Class DTO object used to store data about the auction when a list is required.
 */
public class AuctionListDTO extends BaseListDTO {
    public Integer auctionId;
    public String description;

    /**
     * Constructor.
     */
    public AuctionListDTO() {
    }

    /**
     * Constructor.
     * @param auctionId ID to be assigned to the auction.
     * @param description Description to be associated with the auction.
     */
    public AuctionListDTO(Integer auctionId, String description) {
        this.auctionId = auctionId;
        this.description = description;
    }
}
