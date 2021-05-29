package auctionaction.source_code;

import auctionaction.config.DatabaseAuthenticator;
import auctionaction.config.DatabaseConnectionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SourceCodeApplication {
    private static final Logger logger = LoggerFactory.getLogger(SourceCodeApplication.class);
    private static DatabaseAuthenticator authenticator;


    public SourceCodeApplication() throws NoSuchAlgorithmException {
        authenticator = new DatabaseAuthenticator();
        myMain();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SpringApplication.run(SourceCodeApplication.class, args);

        SourceCodeApplication myApplication = new SourceCodeApplication();
    }

    public void myMain() throws NoSuchAlgorithmException {
        String filePath = "connection_config";

        if (authenticator.authenticateConnection()) {
            System.out.println("Authentication successful.");

            authenticator.connectionConfig.writeBinConfigs(filePath);

            System.out.println("done");
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
            Connection conn = DriverManager.getConnection(authenticator.getJdbcURL(), authenticator.getJDBC_USER(), authenticator.getJDBC_PASS());
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Could not obtain connection to the database: ", ex);
        }
        return null;
    }


}