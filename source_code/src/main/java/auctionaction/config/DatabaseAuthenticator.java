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
    private PasswordEncrypter encoder;

    public DatabaseAuthenticator() {
        encoder = new PasswordEncrypter();
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

    public boolean authenticateConnection() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        String filePath = "connection_config", password, user, hash;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        byte[] passHash;

        if (connectionConfig.loadDatabaseConfigs(filePath)) {

            try {
                System.out.print("Database user: ");
                user = input.readLine();
                System.out.print("Password: ");
                password = input.readLine();

                passHash = encoder.getPasswordHash(password);
                hash = encoder.bytesToHex(passHash);

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

        System.out.print("Username not correct. ");
        return false;
    }
}
