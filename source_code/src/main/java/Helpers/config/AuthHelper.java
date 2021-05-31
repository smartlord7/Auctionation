package Helpers.config;

import Layers.BusinessLayer.UserBusiness.DTO.UserAuthDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class used in token authentication.
 */
public class AuthHelper {

    public static HashMap<String, UserSession> sessions = new HashMap<>();
    private static final int TOKEN_DURATION = ((14 * 60) + 59) * 1000, TOKEN_LENGTH = 256;
    public static final String TOKEN_HEADER_KEY_NAME = "custom-token";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthHelper.class);

    /**
     * Function that authenticates a certain user.
     * @param dto Data about the user.
     * @param response Object to store the error code in case of error.
     * @return Returns a token response.
     */
    public static TokenResponse authenticate(UserAuthDTO dto, ErrorResponse response) {
        UserListDTO user;
        UserSession session;
        String token, hash;

        List<UserListDTO> result = new UserDAO().getbyProp("username", dto.username);

        //check the username
        if (result.size() == 0) {
            response.errorCode = "autherror";
            response.errorMessage = "wrong username";

            return null;
        }

        user = result.get(0);

        //check the password by hash matching
        hash = EncryptHelper.sha256Encrypt(dto.password);

        if (!hash.equals(user.passwordHash)) {
            response.errorCode = "autherror";
            response.errorMessage = "wrong password";
            return null;
        }

        logger.info("User " + dto.username + " successfully logged!");

        session = new UserSession(user.userId,
                user.roleId,
                user.userName,
                TOKEN_DURATION,
                Date.from(Instant.now()));


        token = generateToken();
        sessions.putIfAbsent(token, session);

        return new TokenResponse(token, TOKEN_DURATION);
    }

    /**
     * Function that generates a token.
     * @return Returns the generated token.
     */
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
