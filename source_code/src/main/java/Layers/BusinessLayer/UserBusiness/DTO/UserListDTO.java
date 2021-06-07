package Layers.BusinessLayer.UserBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

/**
 * DTO object to transfer data of a user in listing operations.
 */
public class UserListDTO extends BaseListDTO {
    public String userName,
            email,
            address,
            passwordHash;
    public Integer userId;
    public Long roleId;
}
