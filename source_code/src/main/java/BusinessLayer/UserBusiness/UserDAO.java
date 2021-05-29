package BusinessLayer.UserBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class UserDAO<UserEditDTO, UserListDTO> extends BaseDAO{
    public UserDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
