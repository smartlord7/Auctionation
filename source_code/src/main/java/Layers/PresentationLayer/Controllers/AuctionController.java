package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.*;

@RestController
@RequestMapping("/auctionation/api/auction")
public class AuctionController {
    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static final AuctionDAO auctionDAO = new AuctionDAO();

    /**
     *
     * @param payload
     * @return
     */
    @Authorization(allowAnonymous = true)
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody AuctionEditDTO payload) {
        AuctionEditDTO result = (AuctionEditDTO) auctionDAO.create(payload);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(auctionDAO.getError());
        }
        return ResponseEntity.ok((result.id));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/delete/{auctionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.deleteById(auctionId));
    }


    /**
     *
     * @param auctionId
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/details/{auctionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.getDetails(auctionId));
    }

    /**
     *
     * @param payload
     * @param auctionId
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/edit/{auctionId}", consumes = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody AuctionEditDTO payload, @PathVariable("auctionId") int auctionId) {
        AuctionEditDTO result = (AuctionEditDTO) auctionDAO.updateById(payload, auctionId);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(auctionDAO.getError());
        }
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param userId
     * @return
     */
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

    // It is obvious that this method (passing the adminId through the route) is not the most correct,
    // but we did it that way since the implementation of a more complex session structure would require extra time to do it :-).
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/cancel/{auctionId}/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> cancel(@PathVariable("auctionId") int auctionId, @PathVariable("adminId") int adminId) {
        return ResponseEntity.ok(auctionDAO.cancelById(auctionId, adminId));
    }
}