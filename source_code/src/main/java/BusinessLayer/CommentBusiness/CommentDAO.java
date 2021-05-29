package BusinessLayer.CommentBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class CommentDAO<CommentEditDTO,CommentListDTO> extends BaseDAO{
    public CommentDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
