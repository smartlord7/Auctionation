package auctionaction.source_code;

import auctionaction.config.DatabaseConnectionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SourceCodeApplication {
    private static final Logger logger = LoggerFactory.getLogger(SourceCodeApplication.class);
    private static String JDBC_USER, JDBC_PASS, jdbcURL;
    private static DatabaseConnectionConfig connectionConfig;

    public SourceCodeApplication() throws NoSuchAlgorithmException {
        connectionConfig = new DatabaseConnectionConfig();
        myMain();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SourceCodeApplication myApplication = new SourceCodeApplication();
    }

    public void myMain() throws NoSuchAlgorithmException {
        String filePath = "connection_config";

        if (authenticateConnection()) {
            System.out.println("Authentication successful.");

            System.out.println(connectionConfig.toString());

            connectionConfig.writeBinConfigs(filePath);

            //SpringApplication.run(SourceCodeApplication.class);

        } else {
            System.out.println("Failed to authenticate database connection.");
        }
    }

    /**
     * Connection is started with {@code AUTO_COMMIT = false}
     *
     * @return a connection to the postgresql database.
     */
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, JDBC_USER, JDBC_PASS);
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Could not obtain connection to the database: ", ex);
        }
        return null;
    }

    public boolean authenticateConnection() throws NoSuchAlgorithmException {
        String filePath = "connection_config", password, user, hash;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        byte[] passHash;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        if(connectionConfig.loadDatabaseConfigs(filePath)) {

            try {
                System.out.print("Database user: ");
                user = input.readLine();
                System.out.print("Password: ");
                password = input.readLine();

                passHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                hash = bytesToHex(passHash);

                if(connectionConfig.getJDBC_USER().equals(user)) {

                    if(connectionConfig.getJDBC_PASS().equals(hash)) {
                        JDBC_USER = connectionConfig.getJDBC_USER();
                        JDBC_PASS = password;
                        jdbcURL = connectionConfig.getJDBC_PSQL_CONN_LOCAL();

                        return true;
                    }
                    System.out.println("Password not correct.");

                    System.out.println(hash);

                    return false;
                }
                System.out.println("Username not correct");

                return false;
            } catch(IOException exception) {
                return false;
            }

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