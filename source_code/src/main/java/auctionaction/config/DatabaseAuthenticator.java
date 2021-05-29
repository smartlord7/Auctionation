package auctionaction.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseAuthenticator {
    public DatabaseConnectionConfig connectionConfig;
    private String JDBC_USER, JDBC_PASS, jdbcURL;

    public DatabaseAuthenticator() {
        connectionConfig = new DatabaseConnectionConfig();
    }

    public String getJDBC_USER() {
        return JDBC_USER;
    }

    public void setJDBC_USER(String JDBC_USER) {
        this.JDBC_USER = JDBC_USER;
    }

    public String getJDBC_PASS() {
        return JDBC_PASS;
    }

    public void setJDBC_PASS(String JDBC_PASS) {
        this.JDBC_PASS = JDBC_PASS;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public void setJdbcURL() {

    }

    public boolean authenticateConnection() {
        String filePath = "connection_config", password, user, hash;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        byte[] passHash;

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            if (connectionConfig.loadDatabaseConfigs(filePath)) {

                try {
                    System.out.print("Database user: ");
                    user = input.readLine();
                    System.out.print("Password: ");
                    password = input.readLine();

                    passHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    hash = bytesToHex(passHash);

                    if (connectionConfig.getJDBC_USER().equals(user)) {

                        if (connectionConfig.getJDBC_PASS().equals(hash)) {
                            JDBC_USER = connectionConfig.getJDBC_USER();
                            JDBC_PASS = password;
                            jdbcURL = connectionConfig.getJDBC_PSQL_CONN_LOCAL();

                            return true;
                        }
                        System.out.print("Password not correct. ");

                        return false;
                    }
                    System.out.print("Username not correct. ");

                    return false;
                } catch (IOException exception) {
                    return false;
                }

            }
        } catch (NoSuchAlgorithmException exception) {
            return false;
        }

        return false;
    }

    private String bytesToHex(byte[] hash) {
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
