package auctionaction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {
    @Autowired
    OAuthDAO oAuthDAO;

    public CustomUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = null;
        try {
            user = oAuthDAO.getUserDetails(username);
            CustomUserEntity customUser = new CustomUserEntity(user);
            return customUser;
        } catch (Exception exception) {
            System.out.println("User "  + username + " was not found in the database.");
            return null;
        }
    }

}
