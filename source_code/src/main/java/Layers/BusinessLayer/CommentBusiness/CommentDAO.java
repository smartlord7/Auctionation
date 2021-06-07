package Layers.BusinessLayer.CommentBusiness;

import Layers.BusinessLayer.Base.BaseDAO;


import static Layers.BusinessLayer.Base.TableNames.COMMENT;

/**
 * DAO object to access data of a comment.
 */
public class CommentDAO extends BaseDAO<CommentEditDTO,CommentListDTO>{
    public CommentDAO() {
        super(COMMENT, true);
    }
}
