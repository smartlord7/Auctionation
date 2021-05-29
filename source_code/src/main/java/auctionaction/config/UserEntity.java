package auctionaction.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserEntity {
    private String username, passwordHash;
    private int userID;
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }
}
