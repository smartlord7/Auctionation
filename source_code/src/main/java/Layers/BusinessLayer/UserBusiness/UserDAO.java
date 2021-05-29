package Layers.BusinessLayer.UserBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;

import java.sql.Connection;

public class UserDAO extends BaseDAO<UserEditDTO, UserListDTO>{
    public UserDAO(Connection conn) {
        super(conn, "User", true);
    }
}
