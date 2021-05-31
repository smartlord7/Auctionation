package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static final AuctionDAO auctionDAO = new AuctionDAO(ConnectionFactory.getConnection());

    @Authorization(allowAnonymous = true)
    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody AuctionEditDTO payload) {
        return ResponseEntity.ok(((AuctionEditDTO) auctionDAO.create(payload)));
    }

    @Authorization(roleId = {ROLE_ADMIN})
    @DeleteMapping(value = "/delete/{auctionId}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.deleteById(auctionId));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/details/{auctionId}")
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.getDetails(auctionId));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @PutMapping(value = "/edit/{auctionId}", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody AuctionEditDTO payload, @PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok((auctionDAO.updateById(payload, auctionId)));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(auctionDAO.getbyProp(null, null));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @PutMapping(value = "/search/{keyword}", consumes = "text/plain", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, @RequestBody String value) {
        return ResponseEntity.ok(auctionDAO.getbyProp(keyword, value));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
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