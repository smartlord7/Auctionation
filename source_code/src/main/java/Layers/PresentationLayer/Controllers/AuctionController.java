package Layers.PresentationLayer.Controllers;

import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static final AuctionDAO auctionDAO = new AuctionDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody AuctionEditDTO payload) {
        return ResponseEntity.ok(((AuctionEditDTO) auctionDAO.create(payload)));
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody AuctionEditDTO payload) {
        return ResponseEntity.ok((auctionDAO.updateById(payload)));
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(auctionDAO.getbyProp(null, null));
    }

    @GetMapping(value = "/{keyword}", consumes = "text/plain", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, Object value) {
        return ResponseEntity.ok(auctionDAO.getbyProp(keyword, value));
    }

    @GetMapping(value = "/user/{userId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(auctionDAO.getAllByUser(userId));
    }
}
