package Layers.BusinessLayer.UserBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;
import Layers.BusinessLayer.Base.InsertIgnore;

import java.sql.Date;
import java.sql.Timestamp;

public class UserEditDTO extends BaseEditDTO {
    public String userName,
            firstName,
            lastName,
            email,
            phoneNumber,
            address,
            passwordHash;
    public Timestamp birthdate;
    public Integer roleId;

    @InsertIgnore
    public String password;
}
