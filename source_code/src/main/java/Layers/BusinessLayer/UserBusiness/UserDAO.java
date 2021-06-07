package Layers.BusinessLayer.UserBusiness;

import Helpers.config.EncryptHelper;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.Base.DTO.BaseEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Startup.ConnectionFactory;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static Layers.BusinessLayer.Base.TableNames.USER;

/**
 * DAO object to access data of an user.
 */
public class UserDAO extends BaseDAO<UserEditDTO, UserListDTO>{

    private static final String AUCTION_COMMENT_BAN = "We are sorry to inform that this auction is no longer active.";

    public UserDAO() {
        super(USER, true);
    }

    /**
     * Function that creates an user.
     * @param dto DTO object with data required.
     * @return Returns the ID assigned.
     */
    @Override
    public BaseEditDTO create(BaseEditDTO dto) {
        UserEditDTO createDTO = (UserEditDTO) dto;
        createDTO.passwordHash = EncryptHelper.sha256Encrypt(createDTO.password);

        return super.create(createDTO);
    }

    /**
     * Function that bans a user.
     * @param adminUserId ID of the admin.
     * @param bannedUserId ID of the user.
     * @return Returns true if successful.
     */
    public boolean banUser(int adminUserId, int bannedUserId) {
        String query = "CALL banuser(?, ?, ?)";
        CallableStatement cs;
        Connection conn = ConnectionFactory.getConnection();

        try {
            cs = conn.prepareCall(query);
            cs.setInt(1, adminUserId);
            cs.setInt(2, bannedUserId);
            cs.setString(3, AUCTION_COMMENT_BAN);

            cs.execute();
            saveChanges(conn);
        } catch (SQLException e) {
            auditError(e, conn);
            return false;
        }

        return true;
    }
}
