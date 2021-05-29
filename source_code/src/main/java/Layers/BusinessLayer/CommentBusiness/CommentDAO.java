package Layers.BusinessLayer.CommentBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class CommentDAO extends BaseDAO<CommentEditDTO,CommentListDTO>{
    public CommentDAO(Connection conn) {
        super(conn, "Comment", true);
    }
}
