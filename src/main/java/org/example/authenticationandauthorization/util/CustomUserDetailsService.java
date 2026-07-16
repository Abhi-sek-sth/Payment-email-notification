package org.example.authenticationandauthorization.util;


import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.entity.Users;
import org.example.authenticationandauthorization.exception.ResourceNotFound;
import org.example.authenticationandauthorization.repository.UsersRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRoles().toString()))
        );
    }
}

