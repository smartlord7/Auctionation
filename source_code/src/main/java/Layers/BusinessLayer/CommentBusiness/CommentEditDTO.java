package Layers.BusinessLayer.CommentBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

/**
 * DTO object to transfer data of a comment.
 */
public class CommentEditDTO extends BaseEditDTO {
    public String text;
    public int commentId, auctionId, userId;
}