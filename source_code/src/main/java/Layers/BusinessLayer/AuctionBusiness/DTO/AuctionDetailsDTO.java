package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.Subentity;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;
import Layers.BusinessLayer.CommentBusiness.CommentListDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Class DTO object used to transfer details about a certain auction.
 */
public class AuctionDetailsDTO {
    public Integer auctionId;
    public Long hostUserId;
    public String auctionName, description, itemEAN, itemName, itemDescription, itemOrigin;
    public Double initialValue;
    public Timestamp startTimestamp,
            expirationTimestamp;

    @Subentity
    public List<BidListDTO> bidHistory;

    @Subentity
    public List<CommentListDTO> comments;

    public AuctionDetailsDTO() {
    }
}
