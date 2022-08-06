package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CustomUser;
import com.softuni.espresso.tracker.model.mapper.UserMapper;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::mapUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
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

    private List<GrantedAuthority> getAuthorities(UserEntity entity) {
        return List.of(new SimpleGrantedAuthority(
                "ROLE_" + entity.getUserRole().name()));
    }

}
