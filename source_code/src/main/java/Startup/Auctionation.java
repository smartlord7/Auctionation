package Startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("Layers")
@ComponentScan("Startup")
public class Auctionation {
    private static final Logger logger = LoggerFactory.getLogger(Auctionation.class);

    public static void main(String[] args) {
        SpringApplication.run(Auctionation.class, args);

    }
}
