package startup;

import auctionaction.config.DatabaseAuthenticator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SourceCodeApplication {


    public SourceCodeApplication() {
        authenticator = new DatabaseAuthenticator();
        myMain();
    }

    public static void main(String[] args) {
        SpringApplication.run(SourceCodeApplication.class, args);
    }

    public void myMain() {
        String filePath = "connection_config";

        if (authenticator.authenticateConnection()) {
            System.out.println("Authentication successful.");

            authenticator.connectionConfig.writeBinConfigs(filePath);
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
