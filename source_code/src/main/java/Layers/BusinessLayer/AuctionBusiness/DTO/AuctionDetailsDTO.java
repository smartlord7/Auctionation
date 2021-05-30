package Layers.BusinessLayer.AuctionBusiness.DTO;

import Layers.BusinessLayer.Base.Subentity;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;
import Layers.BusinessLayer.CommentBusiness.CommentListDTO;

import java.sql.Timestamp;
import java.util.List;

public class AuctionDetailsDTO {
    public Integer hostUserId;
    public String auctionName, description, itemEAN, itemName, itemDescription, itemOrigin;
    public Float initialValue;
    public Timestamp startTimestamp,
            expirationTimestamp;

    @Subentity
    public List<BidListDTO> bidHistory;

    @Subentity
    public List<CommentListDTO> comments;

    public AuctionDetailsDTO() {
    }
}
