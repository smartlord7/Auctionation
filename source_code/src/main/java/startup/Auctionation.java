package startup;

import BusinessLayer.AuctionBusiness.AuctionDAO;
import auctionaction.config.DatabaseAuthenticator;
import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Auctionation {
    private static final Logger logger = LoggerFactory.getLogger(Auctionation.class);
    private final DatabaseAuthenticator authenticator;

    public Auctionation() {
        authenticator = new DatabaseAuthenticator();
        Connection conn = null;

        try {
            authenticate();
            conn = getConnection();

            AuctionDAO dao = new AuctionDAO(conn, "Auction");
            dao.list();
        } catch (Exception ex) {
            Sentry.captureException(ex);
            logger.error(ex.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex2) {
                    logger.warn("Couldn't rollback!");
                    try {
                        conn.close();
                    } catch (SQLException ex3) {
                        logger.error("Couldn't close connection!");
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Auctionation.class, args);
    }

    public void authenticate() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        String filePath = "connection_config";

        if (authenticator.authenticateConnection()) {
            System.out.println("Database authentication successful.");
            authenticator.connectionConfig.writeBinConfigs(filePath);
        } else {
            System.exit(0);
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
