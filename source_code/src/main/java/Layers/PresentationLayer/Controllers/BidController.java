package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.BidBusiness.BidDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Startup.ConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("/bid")
public class BidController {
    private static final BidDAO bidDAO = new BidDAO();

    /**
     *
     * @param payload
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody BidEditDTO payload) {

        BidEditDTO result = bidDAO.bid(payload);
        if(result == null) {
            return ResponseEntity.ok(bidDAO.getError());
        } else if (result.id == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction doesn't exist or has ended!");
        }
        return ResponseEntity.ok((result));
    }

    /**
     *
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(bidDAO.getbyProp(null, null));
    }
}