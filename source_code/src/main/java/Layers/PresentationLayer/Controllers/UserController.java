package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Startup.ConnectionFactory;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserDAO userDAO = new UserDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserEditDTO payload) {
         return ResponseEntity.ok((UserEditDTO) userDAO.create(payload));
    }

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.getbyProp("userId", userId).get(0));
    }

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.deleteById(userId));
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userDAO.getbyProp(null, null));
    }

    @GetMapping(value ="/ban/{userId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> banUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.banUser(1, userId));
    }
}