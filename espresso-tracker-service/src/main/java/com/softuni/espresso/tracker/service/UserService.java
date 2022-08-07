package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CustomUser;
import com.softuni.espresso.tracker.model.web.UserRequest;
import com.softuni.espresso.tracker.model.UserRole;
import com.softuni.espresso.tracker.model.mapper.UserMapper;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import com.softuni.espresso.tracker.service.exceptions.UserException;
import com.softuni.espresso.tracker.service.exceptions.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final String EXCEPTION = "User already exists";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void register(UserRequest user) {
        Optional<UserEntity> optUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (optUser.isPresent()) {
            throw new UserException(EXCEPTION);
        }

        UserEntity newUser = userMapper.mapToEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserRole(UserRole.USER);
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Exception while persisting new user: {}", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }
    }

    public UserDetails login (UserRequest user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());

        boolean matches = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());

        if(!matches) {
            throw new UserUnauthorizedException("User is unauthorized");
        }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::mapUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
    }

    private CustomUser mapUser(UserEntity entity) {
        return CustomUser.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + entity.getUserRole().name())))
                .build();
    }

}
