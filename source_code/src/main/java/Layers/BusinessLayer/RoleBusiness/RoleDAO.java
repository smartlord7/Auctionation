package Layers.BusinessLayer.RoleBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

/**
 * DAO object to access data of a role.
 */
public class RoleDAO extends BaseDAO<RoleEditDTO,RoleListDTO>{
    public RoleDAO(Connection conn) {
        super("Role", false);
    }
}

