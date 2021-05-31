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
     * Endpoint to create an auction.
     * @param payload Data required to create the auction.
     * @return Returns the ID assigned to the auction or an error code.
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

    /**
     * Endpoint to delete an auction.
     * @param auctionId ID of the auction.
     * @return Returns true if the operation was successful.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/delete/{auctionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.deleteById(auctionId));
    }


    /**
     * Endpoint to get the details about an auction.
     * @param auctionId ID of the auction.
     * @return Returns a AuctionDetailsDTO that contains the details.
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/details/{auctionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getDetails(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.getDetails(auctionId));
    }

    /**
     * Endpoint to update an auction.
     * @param payload Data to be updated.
     * @param auctionId ID of the auction.
     * @return Returns a AuctionEditDTO object that contains the updated data.
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
     * Endpoint to list of the auctions.
     * @return Returns a list of AuctionListDTO objects with data about the auctions.
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(auctionDAO.getbyProp(null, null));
    }

    /**
     * Endpoint to list of the auctions that possess the value of the keyword.
     * @param keyword Field correspondent to the value to be searched.
     * @param value Value to be searched.
     * @return Returns a list of AuctionListDTO objects with data about the auctions found.
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/search/{keyword}", consumes = "text/plain", produces = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, @RequestBody String value) {
        return ResponseEntity.ok(auctionDAO.getbyProp(keyword, value));
    }

    /**
     * Endpoint to list all of the auctions where the user is involved.
     * @param userId ID of the user.
     * @return Returns a list of AuctionListDTO objects with the auctions found.
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/user/{userId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listByUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(auctionDAO.getAllByUser(userId));
    }

    /**
     * Endpoint to terminate an auction.
     * @param auctionId ID of the auction.
     * @return Returns data about the winning bet.
     */
    @Authorization(roles = {ROLE_SCHEDULER})
    @RequestMapping(value = "/terminate/{auctionId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> terminate(@PathVariable("auctionId") int auctionId) {
        return ResponseEntity.ok(auctionDAO.terminateById(auctionId));
    }


    /**
     * Endpoint to cancel an auction.
     * @param auctionId ID of the auction.
     * @param adminId ID of the admin.
     * @return Returns true if successful.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/cancel/{auctionId}/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> cancel(@PathVariable("auctionId") int auctionId, @PathVariable("adminId") int adminId) {
        return ResponseEntity.ok(auctionDAO.cancelById(auctionId, adminId));
    }
}