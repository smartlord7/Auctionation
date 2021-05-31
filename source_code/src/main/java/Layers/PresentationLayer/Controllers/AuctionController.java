package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.*;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private static ErrorResponse errorResponse = new ErrorResponse();
    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static final AuctionDAO auctionDAO = new AuctionDAO(ConnectionFactory.getConnection(), errorResponse);

    @Authorization(allowAnonymous = true)
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody AuctionEditDTO payload) {
        return ResponseEntity.ok(((AuctionEditDTO) auctionDAO.create(payload)));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/delete/{auctionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.deleteById(auctionId));
    }

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/details/{auctionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.getDetails(auctionId));
    }

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/edit/{auctionId}", consumes = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody AuctionEditDTO payload, @PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok((auctionDAO.updateById(payload, auctionId)));
    }

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(auctionDAO.getbyProp(null, null));
    }

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/search/{keyword}", consumes = "text/plain", produces = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, @RequestBody String value) {
        return ResponseEntity.ok(auctionDAO.getbyProp(keyword, value));
    }

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/user/{userId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listByUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(auctionDAO.getAllByUser(userId));
    }

    @Authorization(roles = {ROLE_SCHEDULER})
    @RequestMapping(value = "/terminate/{auctionId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> terminate(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.terminateById(auctionId));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/cancel/{auctionId}/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> cancel(@PathVariable("auctionId") int auctionId, @PathVariable("adminId") int adminId) {
        return ResponseEntity.ok(auctionDAO.cancelById(auctionId, adminId));
    }
}