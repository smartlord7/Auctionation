package Layers.PresentationLayer.Controllers;

import Layers.BusinessLayer.NotificationBusiness.NotificationDAO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private static final NotificationDAO notificationDAO = new NotificationDAO(ConnectionFactory.getConnection());

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listALl() {
        return ResponseEntity.ok(notificationDAO.getbyProp(null, null));
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listByUser() {
        return ResponseEntity.ok(notificationDAO.getbyProp(null, null));
    }
}
