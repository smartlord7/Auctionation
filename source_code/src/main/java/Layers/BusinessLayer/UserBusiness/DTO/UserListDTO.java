package Layers.BusinessLayer.UserBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

public class UserListDTO extends BaseListDTO {
    public String userName,
            email,
            address,
            passwordHash;
    public Integer userId;
    public Long roleId;
}
