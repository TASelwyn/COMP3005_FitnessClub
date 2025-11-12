package tech.selwyn.carleton.comp3005.fitnessclub.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.selwyn.carleton.comp3005.fitnessclub.model.ClubAccount;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final ClubAccount clubAccount;

    public UserDetailsImpl(ClubAccount clubAccount) {
        this.clubAccount = clubAccount;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = clubAccount.getRole();
        String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(prefixedRole));
    }

    @Override
    public String getPassword() { return clubAccount.getPasswordHash(); }

    @Override
    public String getUsername() { return clubAccount.getEmail(); }

    @Override
    public boolean isAccountNonExpired() { return false; }

    @Override
    public boolean isAccountNonLocked() { return false; }

    @Override
    public boolean isCredentialsNonExpired() { return false; }

    @Override
    public boolean isEnabled() { return false; }

}