package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Layers.BusinessLayer.AuctionEditHistoryBusiness.AuctionEditHistoryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("/history")
public class AuctionHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHistoryController.class);
    private static final AuctionEditHistoryDAO historyDAO = new AuctionEditHistoryDAO();

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/search/{keyword}", consumes = "text/plain", produces = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> listByKeyword(@PathVariable("keyword") String keyword, @RequestBody String value) {
        return ResponseEntity.ok(historyDAO.getbyProp(keyword, value));
    }

}
