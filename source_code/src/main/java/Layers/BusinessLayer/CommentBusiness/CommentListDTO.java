package Layers.BusinessLayer.CommentBusiness;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

/**
 * DTO object to transfer data of a comment in listing operations.
 */
public class CommentListDTO extends BaseListDTO {
    public Long userId;
    public String text;
}
