package Layers.BusinessLayer.CommentBusiness;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

import static Layers.BusinessLayer.Base.TableNames.COMMENT;

public class CommentDAO extends BaseDAO<CommentEditDTO,CommentListDTO>{
    public CommentDAO() {
        super(COMMENT, true);
    }

    public CommentDAO(Connection connection, ErrorResponse errorResponse) {
        super(COMMENT, true);
    }
}
