package Helpers.config;

import Layers.BusinessLayer.UserBusiness.DTO.UserAuthDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AuthHelper {

    public static HashMap<String, UserSession> sessions = new HashMap<>();
    private static final int TOKEN_DURATION = ((14 * 60) + 59) * 1000, TOKEN_LENGTH = 256;
    public static final String TOKEN_HEADER_KEY_NAME = "custom-token";
    public static ErrorResponse errorResponse;

    public static TokenResponse authenticate(UserAuthDTO dto, ErrorResponse response) {
        UserListDTO user;
        UserSession session;
        String token, hash;

        List<UserListDTO> result = new UserDAO().getbyProp("username", dto.username);

        if (result.size() == 0) {
            response.errorCode = "autherror";
            response.errorMessage = "wrong username";

            return null;
        }

        user = result.get(0);

        hash = EncryptHelper.sha256Encrypt(dto.password);

        if (!hash.equals(user.passwordHash)) {
            response.errorCode = "autherror";
            response.errorMessage = "wrong password";
            return null;
        }

        System.out.println("User " + dto.username + " successfully logged!");

        session = new UserSession(user.userId,
                user.roleId,
                user.userName,
                TOKEN_DURATION,
                Date.from(Instant.now()));


        token = generateToken();
        sessions.putIfAbsent(token, session);

        return new TokenResponse(token, TOKEN_DURATION);
    }

    private static String generateToken() {
        int leftLimit = 48, rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(TOKEN_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
