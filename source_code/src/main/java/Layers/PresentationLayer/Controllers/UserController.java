package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.StatsBusiness.StatsDAO;
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

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserDAO userDAO = new UserDAO();

    @Authorization(allowAnonymous = true)
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody UserEditDTO payload) {
         return ResponseEntity.ok((UserEditDTO) userDAO.create(payload));
    }

    @Authorization(allowAnonymous = true)
    @RequestMapping(value = "/login", consumes = "application/json", method = RequestMethod.PUT)
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

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/details/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.getbyProp("userId", Integer.toString(userId)).get(0));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.deleteById(userId));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userDAO.getbyProp(null, null));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value ="/ban/{userId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> banUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.banUser(1, userId));
    }

    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/stats", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listAuctionsWon() {
        return ResponseEntity.ok(new StatsDAO().getStats());
    }





}