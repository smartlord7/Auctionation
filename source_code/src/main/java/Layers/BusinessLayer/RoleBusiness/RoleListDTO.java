package Layers.BusinessLayer.RoleBusiness;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

/**
 * DTO object to transfer data of a role in listing operations.
 */
public class RoleListDTO extends BaseListDTO {
    public Long roleId;
    public String RoleName, description;
}
