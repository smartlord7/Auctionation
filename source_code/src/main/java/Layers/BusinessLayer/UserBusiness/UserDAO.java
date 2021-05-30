package Layers.BusinessLayer.UserBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Startup.ConnectionFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO extends BaseDAO<UserEditDTO, UserListDTO>{

    private static final String AUCTION_COMMENT_BAN = "We are sorry to inform that this auction is no longer active.";

    public UserDAO(Connection conn) {
        super("User", true);
    }

    public boolean banUser(int adminUserId, int bannedUserId) {
        String query = "{CALL banUser(?, ?)}";
        CallableStatement cs;
        Connection conn = ConnectionFactory.getConnection();

        try {
            cs = conn.prepareCall(query);
            cs.setInt(1, adminUserId);
            cs.setInt(2, bannedUserId);
            cs.setString(3, AUCTION_COMMENT_BAN);

            boolean feedback = cs.execute();
            saveChanges(conn);
            return feedback;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
