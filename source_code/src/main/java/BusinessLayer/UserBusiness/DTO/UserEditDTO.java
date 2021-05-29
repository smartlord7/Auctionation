package BusinessLayer.UserBusiness.DTO;

import BusinessLayer.Base.DTO.BaseEditDTO;

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
    public Date birthDate;
    public Float wallet;
    public Timestamp banTimestamp;
}
