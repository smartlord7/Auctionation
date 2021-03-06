package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.StatsBusiness.StatsDAO;
import Helpers.config.Authorization;
import Helpers.config.ErrorResponse;
import Helpers.config.TokenResponse;
import Layers.BusinessLayer.StatsBusiness.StatsEditDTO;
import Layers.BusinessLayer.StatsBusiness.StatsListDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserAuthDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Helpers.config.AuthHelper.authenticate;
import static Helpers.config.Authorization.ROLE_ADMIN;

@RestController
@RequestMapping("/auctionation/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserDAO userDAO = new UserDAO();

    /**
     * Endpoint to create a user.
     * @param payload Data of the user.
     * @return Returns the ID assigned.
     */
    @Authorization(allowAnonymous = true)
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserEditDTO payload) {
        UserEditDTO result = (UserEditDTO) userDAO.create(payload);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDAO.getError());
        }
        return ResponseEntity.ok((result.id));
    }

    /**
     * Endpoint to login a user.
     * @param payload Data required for the login.
     * @return Returns a token response.
     */
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

    /**
     * Endoint to search a user.
     * @param userId ID of the user.
     * @return Returns the data about the user.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/details/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.getbyProp("userId", Integer.toString(userId)).get(0));
    }

    /**
     * Endpoint to delete a user.
     * @param userId
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(userDAO.deleteById(userId));
    }

    /**
     * Endpoint to list of all of the users.
     * @return List with of all of the users.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> listUsers() {
        List<UserListDTO> dto;
        if ((dto = userDAO.getbyProp(null, null)) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDAO.getError());
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint to ban a user.
     * @param userId ID of the user.
     * @param adminId ID of the admin.
     * @return
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value ="/ban/{userId}/{adminId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> banUser(@PathVariable("userId") int userId, @PathVariable("adminId") int adminId) {

        if (!userDAO.banUser(adminId, userId)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDAO.getError());
        }
        return ResponseEntity.ok(userId);
    }

    /**
     * Endpoint to get the stats about a user.
     * @return Returns stats about the user.
     */
    @Authorization(roles = {ROLE_ADMIN})
    @RequestMapping(value = "/stats", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getStats() {
        StatsDAO statsDAO = new StatsDAO();
        StatsListDTO dto;
        if ((dto = statsDAO.getStats()) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statsDAO.getError());
        }
        return ResponseEntity.ok(dto);
    }
}