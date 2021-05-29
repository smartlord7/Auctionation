package auctionaction.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {

    public PasswordEncrypter(){}

    public byte[] getPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            return null;
        }
    }

    public String bytesToHex(byte[] hash) {
        StringBuilder builder = new StringBuilder();

        for(byte b: hash){
            int num = (int) b & 0xff;

            String hex = Integer.toHexString(num);
            if(hex.length() % 2 == 1) {
                hex = "0" + hex;
            }
            builder.append(hex);
        }

        return builder.toString();
    }

}
