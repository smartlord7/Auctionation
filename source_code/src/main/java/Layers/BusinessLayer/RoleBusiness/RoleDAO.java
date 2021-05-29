package Layers.BusinessLayer.RoleBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class RoleDAO extends BaseDAO<RoleEditDTO,RoleListDTO>{
    public RoleDAO(Connection conn) {
        super(conn, "Role", false);
    }
}

