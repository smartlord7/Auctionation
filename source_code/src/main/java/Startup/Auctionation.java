package Startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("Layers")
@ComponentScan("Startup")
public class Auctionation {
    private static final Logger logger = LoggerFactory.getLogger(Auctionation.class);

    public Auctionation() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        Auctionation app = new Auctionation();

        app.authenticate();
        SpringApplication.run(Auctionation.class, args);

    }

    public void authenticate() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        String filePath = "connection_config";

        if (ConnectionFactory.authenticator.authenticateConnection()) {
            System.out.println("Database authentication successful.");
            ConnectionFactory.authenticator.connectionConfig.writeBinConfigs(filePath);
        } else {
            System.exit(0);
        }
    }
}
