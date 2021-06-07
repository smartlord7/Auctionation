package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Layers.BusinessLayer.NotificationBusiness.NotificationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("/auctionation/api/notification")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private static final NotificationDAO notificationDAO = new NotificationDAO();

    /**
     * Endpoint to list of the notifications.
     * @return List of notifications.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listALl() {
        return ResponseEntity.ok(notificationDAO.getbyProp(null, null));
    }

    /**
     * Endpoint to list notifications by user.
     * @param userId ID of the user.
     * @return List of notifications.
     */
    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/listByUser/{userId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listByUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(notificationDAO.listByUser(userId));
    }
}
