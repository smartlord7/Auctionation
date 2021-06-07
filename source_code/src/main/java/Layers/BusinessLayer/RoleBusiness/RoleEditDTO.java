package Layers.BusinessLayer.RoleBusiness;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

/**
 * DTO object to transfer data of a role.
 */
public class RoleEditDTO extends BaseEditDTO {
    public Long roleId;
    public String RoleName, description;
}
