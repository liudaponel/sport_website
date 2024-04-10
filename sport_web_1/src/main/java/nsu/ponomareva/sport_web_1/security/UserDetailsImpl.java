package nsu.ponomareva.sport_web_1.security;

import lombok.Builder;
import lombok.Data;
import nsu.ponomareva.sport_web_1.models.Role;
import nsu.ponomareva.sport_web_1.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String fio;
    private String email;
    private String phone_number;
    private String password;
    private Role role;

    public UserDetailsImpl(User user){
        id = user.getId();
        fio = user.getFio();
        email = user.getEmail();
        phone_number = user.getPhone_number();
        password = user.getPassword();
        role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role != null){
            return List.of(new SimpleGrantedAuthority(role.getName()));
        }
        return null;
//        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}