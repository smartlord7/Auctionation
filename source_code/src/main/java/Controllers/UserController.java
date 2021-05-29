package Controllers;


import BusinessLayer.AuctionBusiness.AuctionDAO;
import BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import BusinessLayer.Base.BaseDAO;
import BusinessLayer.UserBusiness.DTO.UserEditDTO;
import BusinessLayer.UserBusiness.DTO.UserListDTO;
import BusinessLayer.UserBusiness.UserDAO;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.sentry.Sentry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import startup.SourceCodeApplication;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final Connection conn = null;
    private static final BaseDAO<UserEditDTO, UserListDTO> dao = new UserDAO<>(null, "User");

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public String createUser(@RequestBody UserEditDTO payload) throws SQLException, IllegalAccessException {

        logger.info("###              DEMO: POST /user              ###");
        logger.debug("---- new user  ----");
        logger.debug("payload: {}", payload);


        dao.create(payload);


        /*try (PreparedStatement ps = conn.prepareStatement("SELECT userid FROM 'User' WHERE username = ?")) {
            ps.setString(1, payload.getUserName());
            ResultSet rows = ps.executeQuery();
            if (rows.next()) {
                long userid = rows.getInt("userid");
                return "userId :" + userid;
            }
        } catch (SQLException ex) {
            logger.error("Error in DB", ex);
        }

        return "Failed";*/

        return "Sucess";
    }



}