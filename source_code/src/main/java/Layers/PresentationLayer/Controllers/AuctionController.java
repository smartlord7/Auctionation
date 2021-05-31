package Layers.PresentationLayer.Controllers;

import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(value = "/delete/{auctionId}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.deleteById(auctionId));
    }

    @GetMapping(value = "/details/{auctionId}")
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.getDetails(auctionId));
    }

    @PutMapping(value = "/edit/{auctionId}", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody AuctionEditDTO payload, @PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok((auctionDAO.updateById(payload, auctionId)));
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(auctionDAO.getbyProp(null, null));
    }

    @PutMapping(value = "/search/{keyword}", consumes = "text/plain", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, @RequestBody String value) {
        return ResponseEntity.ok(auctionDAO.getbyProp(keyword, value));
    }

    @GetMapping(value = "/user/{userId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(auctionDAO.getAllByUser(userId));
    }

    @GetMapping(value = "/terminate/{auctionId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> terminate(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.terminateById(auctionId));
    }

    @GetMapping(value = "/cancel/{auctionId}/{adminId}")
    @ResponseBody
    public ResponseEntity<?> cancel(@PathVariable("auctionId") int auctionId, @PathVariable("adminId") int adminId) {
        return ResponseEntity.ok(auctionDAO.cancelById(auctionId, adminId));
    }
}