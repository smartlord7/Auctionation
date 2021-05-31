package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Layers.BusinessLayer.NotificationBusiness.NotificationDAO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private static final NotificationDAO notificationDAO = new NotificationDAO(ConnectionFactory.getConnection());

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listALl() {
        return ResponseEntity.ok(notificationDAO.getbyProp(null, null));
    }

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByUser() {
        return ResponseEntity.ok(notificationDAO.getbyProp(null, null));
    }
}
