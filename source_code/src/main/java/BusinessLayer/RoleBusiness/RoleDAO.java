package BusinessLayer.RoleBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class RoleDAO<ToleEditDTO,RoleListDTO> extends BaseDAO{
    public RoleDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}

