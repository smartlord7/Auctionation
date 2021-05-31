package Helpers.config;

import Layers.BusinessLayer.UserBusiness.DTO.UserAuthDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AuthHelper {

    public static HashMap<String, UserSession> sessions = new HashMap<>();
    private static final int TOKEN_DURATION = ((14 * 60) + 59) * 1000;
    public static final String TOKEN_HEADER_KEY_NAME = "custom-token";

    public static TokenResponse authenticate(UserAuthDTO dto, ErrorResponse response) {
        UserListDTO user;
        UserSession session;
        String token, hash;

        List<UserListDTO> result = new UserDAO().getbyProp("userName", dto.userName);

        if (result.size() == 0) {
            response.error = "autherror";
            response.errorMessage = "wrong username";

            return null;
        }

        user = result.get(0);

        hash = EncryptHelper.sha256Encrypt(dto.password);

        if (!hash.equals(user.passwordHash)) {
            response.error = "autherror";
            response.errorMessage = "wrong password";
            return null;
        }

        System.out.println("User " + dto.userName + " successfully logged!");

        session = new UserSession(user.userId,
                user.roleId,
                user.userName,
                TOKEN_DURATION,
                Date.from(Instant.now()));


        token = generateToken(user.userName, user.passwordHash);
        sessions.putIfAbsent(token, session);

        return new TokenResponse(token, TOKEN_DURATION);
    }

    private static String generateToken(String userName, String passwordHash) {
        return EncryptHelper.sha256Encrypt(Double.toString(Math.random() * 100) + userName +  passwordHash + Timestamp.from(Instant.now()));
    }
}
