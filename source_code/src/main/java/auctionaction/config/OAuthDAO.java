package auctionaction.config;

import Layers.BusinessLayer.UserAuthBusiness.UserAuthListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.ResultSet;

@Repository
public class OAuthDAO {
    @Autowired
    private UserAuthListDTO userAuth;

    public UserEntity getUserDetails(String username) {
        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        String SQLQuery = "select * from User where UserName=?";
        //obtain the result from the sql call and return the result
        List<UserEntity> list = jdbcTemplate.query(SQLQuery, new String[] {username}, (ResultSet rs, int rowNum) -> {
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPasswordHash(rs.getString("PasswordHash"));
            return user;
        });
        return null;
    }
}
