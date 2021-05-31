package Helpers.config;

import java.util.Date;

public class UserSession {
    private Integer userId;
    private Long roleId;
    private String userName;
    private int expirationTime;
    private Date createDate;

    public UserSession(Integer userId, Long roleId, String userName, int expirationTime,  Date createDate) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.expirationTime = expirationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTimestamp) {
        this.expirationTime = expirationTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
