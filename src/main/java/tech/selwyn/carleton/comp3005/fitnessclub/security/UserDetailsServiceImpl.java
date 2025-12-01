package tech.selwyn.carleton.comp3005.fitnessclub.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accRepo;

    public UserDetailsServiceImpl(AccountRepository accRepo) {
        this.accRepo = accRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account acc = accRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(acc.getRole()));

        //return new UserDetailsImpl(acc);
        return User.builder()
                .username(acc.getEmail())
                .password(acc.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}
