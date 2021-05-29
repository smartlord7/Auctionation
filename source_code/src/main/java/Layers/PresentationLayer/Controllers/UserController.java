package Layers.PresentationLayer.Controllers;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.BusinessLayer.UserBusiness.UserDAO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Startup.ConnectionFactory;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final BaseDAO<UserEditDTO, UserListDTO> userDAO = new UserDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseBody
    public UserEditDTO create(@RequestBody UserEditDTO payload) {
         return (UserEditDTO) userDAO.create(payload);
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public List<UserListDTO> list() {
        return userDAO.getAll(null, null);
    }

}