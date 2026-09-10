package com.rideflow.user.userdetails;

import com.rideflow.user.entity.User;
import com.rideflow.user.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UUID id;
        try{
            id = UUID.fromString(userId);
        }catch(Exception e){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found : "+ userId));

        return new UserPrincipal(user);
    }
}
