package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.Base.DTO.BaseEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Startup.ConnectionFactory;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final BaseDAO<UserEditDTO, UserListDTO> userDAO = new UserDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseBody
    public BaseEditDTO createUser(@RequestBody UserEditDTO payload) throws SQLException, IllegalAccessException {
         userDAO.create(payload);
            return null;
    }

    @GetMapping(value = "/", consumes = "application/json")
    @ResponseBody
    public String test() {
        return "String\n";
    }
}