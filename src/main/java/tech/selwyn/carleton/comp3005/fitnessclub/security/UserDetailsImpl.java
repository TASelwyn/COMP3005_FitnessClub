package tech.selwyn.carleton.comp3005.fitnessclub.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private final Account account;

    public UserDetailsImpl(Account account) {
        this.account = account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = account.getRole().toString();
        String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(prefixedRole));
    }

    public String getEmail() { return account.getEmail(); }
    @Override
    public String getPassword() { return account.getPasswordHash(); }

    @Override
    public String getUsername() { return account.getEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}