package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.StatsBusiness.StatsDAO;
import Layers.BusinessLayer.StatsBusiness.StatsListDTO;
import Helpers.config.Authorization;
import Helpers.config.ErrorResponse;
import Helpers.config.TokenResponse;
import Layers.BusinessLayer.UserBusiness.DTO.UserAuthDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static Helpers.config.AuthHelper.authenticate;
import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserDAO userDAO = new UserDAO();

    @Authorization(roleId = {ROLE_ADMIN, ROLE_USER})
    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserEditDTO payload) {
         return ResponseEntity.ok((UserEditDTO) userDAO.create(payload));
    }

    @Authorization(allowAnonymous = true)
    @PutMapping(value = "/login", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody UserAuthDTO payload) {
        TokenResponse token;
        ErrorResponse error = new ErrorResponse();
        if (( token = authenticate(payload, error)) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(error);
        } else {
            return ResponseEntity.ok(token);
        }
    }

    @Authorization(roleId = {ROLE_ADMIN})
    @GetMapping(value = "/details/{userId}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.getbyProp("userId", Integer.toString(userId)).get(0));
    }

    @Authorization(roleId = {ROLE_ADMIN})
    @DeleteMapping(value = "/delete/{userId}")
    @ResponseBody
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.deleteById(userId));
    }

    @Authorization(roleId = {ROLE_ADMIN})
    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userDAO.getbyProp(null, null));
    }

    @Authorization(roleId = {ROLE_ADMIN})
    @GetMapping(value ="/ban/{userId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> banUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.banUser(1, userId));
    }

    @GetMapping(value = "/stats", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> listAuctionsWon() {
        return ResponseEntity.ok(StatsDAO.getStats());
    }





}