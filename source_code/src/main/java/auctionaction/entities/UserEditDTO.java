

public class UserEditDTO {
    private Long UserId;
    private String UserName;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String Address;
    private Date BirthDate;
    private Float Wallet;
    private String PasswordHash;
    private Date BanTimestamp;
    private Date CreateTimestamp;
    private Date UpdateTimestamp;
    private Date DeleteTimestamp;

    public UserEditDTO() {

    }

    public Long getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public Float getWallet() {
        return Wallet;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public Date getBanTimestamp() {
        return BanTimestamp;
    }

    public Date getCreateTimestamp() {
        return CreateTimestamp;
    }

    public Date getUpdateTimestamp() {
        return UpdateTimestamp;
    }

    public Date getDeleteTimestamp() {
        return DeleteTimestamp;
    }
}
