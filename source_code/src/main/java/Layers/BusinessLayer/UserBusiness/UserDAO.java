package Layers.BusinessLayer.UserBusiness;

import Helpers.config.AuthHelper;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.Base.DTO.BaseEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Startup.ConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static Layers.BusinessLayer.Base.TableNames.USER;

public class UserDAO extends BaseDAO<UserEditDTO, UserListDTO>{

    private static final String AUCTION_COMMENT_BAN = "We are sorry to inform that this auction is no longer active.";

    public UserDAO() {
        super(USER, true);
    }

    @Override
    public BaseEditDTO create(BaseEditDTO dto) {
        UserEditDTO createDTO = (UserEditDTO) dto;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(createDTO.password.getBytes());
        byte[] digest = md.digest();

        createDTO.passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        return super.create(createDTO);
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
