package Layers.BusinessLayer.CommentBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;
import Layers.BusinessLayer.Base.InsertIgnore;

/**
 * DTO object to transfer data of a comment.
 */
public class CommentEditDTO extends BaseEditDTO {
    public String text;

    @InsertIgnore
    public int commentId;

    public int auctionId, userId;
}