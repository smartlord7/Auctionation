package Layers.PresentationLayer.Controllers;



import Layers.BusinessLayer.BidBusiness.BidDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Startup.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bid")
public class BidController {
    private static final BidDAO bidDAO = new BidDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody BidEditDTO payload) {
        return ResponseEntity.ok((bidDAO.bid(payload)));
    }
}