package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.UserRequest;
import com.softuni.espresso.tracker.model.UserRole;
import com.softuni.espresso.tracker.model.mapper.UserMapper;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;

    public void registerAndLogin(UserRequest user) {

        UserEntity newUser = userMapper.mapToEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserRole(UserRole.USER);

        userRepository.save(newUser);
        login(newUser.getEmail());
    }

    public UserDetails login (String email) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
        return userDetails;
    }

}
