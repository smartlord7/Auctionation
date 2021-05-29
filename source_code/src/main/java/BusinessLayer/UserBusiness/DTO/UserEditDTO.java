package BusinessLayer.UserBusiness.DTO;

import BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Date;
import java.sql.Timestamp;

public class UserEditDTO extends BaseEditDTO {
    private String userName,
            firstName,
            lastName,
            email,
            phoneNumber,
            address,
            passwordHash;
    private Date birthDate;
    private Float wallet;
    private Timestamp banTimestamp;

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Float getWallet() {
        return wallet;
    }

    public Timestamp getBanTimestamp() {
        return banTimestamp;
    }

    @Override
    public String getTableName() {
        return "User";
    }
}
