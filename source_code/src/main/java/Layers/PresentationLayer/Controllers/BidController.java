package Layers.PresentationLayer.Controllers;



import Helpers.config.Authorization;
import Layers.BusinessLayer.BidBusiness.BidDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Startup.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("/bid")
public class BidController {
    private static final BidDAO bidDAO = new BidDAO(ConnectionFactory.getConnection());

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody BidEditDTO payload) {
        return ResponseEntity.ok((bidDAO.bid(payload)));
    }
}