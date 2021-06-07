package Helpers.config;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to encrypt strings.
 */
public class EncryptHelper {
    /**
     * Function that generates an encrypted string.
     * @param str String to be encrypted.
     * @return Returns the encrypted string.
     */
    public static String sha256Encrypt(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(str.getBytes());
        byte[] digest = md.digest();

        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
