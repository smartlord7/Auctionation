package startup;

import BusinessLayer.AuctionBusiness.AuctionDAO;
import BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import io.sentry.Sentry;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SourceCodeApplication {

    public static void main(String[] args) {
        System.out.println("Hello, world!");

        try {
            AuctionEditDTO dto = new AuctionEditDTO();
        } catch (Exception e) {
            AuctionDAO<AuctionEditDTO, AuctionListDTO> dao = new AuctionDAO(null);
            Sentry.captureException(e);
        }
    }
}
