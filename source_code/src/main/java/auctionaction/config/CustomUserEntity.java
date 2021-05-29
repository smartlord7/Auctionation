package auctionaction.config;

import org.springframework.security.core.userdetails.User;

//constructs a User object required by the DAOAuthenticatorProvider
public class CustomUserEntity extends User {

    public CustomUserEntity(UserEntity user){
        super(user.getUsername(), user.getPasswordHash(), user.getGrantedAuthoritiesList());
    }

}