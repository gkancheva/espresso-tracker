package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CustomUser;
import com.softuni.espresso.tracker.model.UserRole;
import com.softuni.espresso.tracker.model.entities.UserEntity;
import com.softuni.espresso.tracker.model.mapper.UserMapper;
import com.softuni.espresso.tracker.model.web.UserRequest;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.service.exceptions.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.ContextException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USERNAME = "test";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Captor
    private ArgumentCaptor<UserEntity> userArgCaptor;

    @Test
    void register_whenUserWithTheSameEmailOrUsernameExists_shouldThrow() {
        UserEntity mockedEntity = mockEntity();
        UserRequest userRequest = mockRequest(mockedEntity);
        when(userRepository.findByUsernameOrEmail(userRequest.getUsername(),userRequest.getEmail()))
                .thenReturn(Optional.of(mockedEntity));
        Assertions.assertThrows(UserException.class,
                () -> userService.register(userRequest));
    }

    @Test
    void register_whenUserDoesNotExist_shouldSetPasswordAndUserRoleCorrect() {
        UserEntity mockedEntity = mockEntity();
        UserRequest request = mockRequest(mockedEntity);
        when(userRepository.findByUsernameOrEmail(request.getUsername(),request.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword() + "h");
        when(userMapper.mapToEntity(request)).thenReturn(mockedEntity);

        userService.register(request);

        verify(userRepository, times(1))
                .findByUsernameOrEmail(request.getUsername(), request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userMapper, times(1)).mapToEntity(request);
        verify(userRepository, times(1)).save(userArgCaptor.capture());

        UserEntity result = userArgCaptor.getValue();
        assertNotNull(result);
        assertEquals(request.getPassword() + "h", result.getPassword());
        assertEquals(UserRole.USER, result.getUserRole());
    }

    @Test
    void loadUserByUsername_whenNotExisting_shouldThrow() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(USERNAME));
    }

    @Test
    void loadUserByUsername_whenExisting_shouldReturnCorrect() {
        UserEntity entity = mockEntity();
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(entity));
        CustomUser userDetails = (CustomUser) userService.loadUserByUsername(entity.getUsername());
        assertNotNull(userDetails);
        assertEquals(entity.getUsername(), userDetails.getUsername());
        assertEquals(entity.getEmail(), userDetails.getEmail());
        assertEquals(entity.getPassword(), userDetails.getPassword());
        assertEquals(getAuthorities(entity), userDetails.getAuthorities());
    }

    @Test
    void findUser_whenNoSuchUser_shouldThrow() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(ContextException.class,
                () -> userService.findUser(USERNAME));
    }

    @Test
    void findUser_whenUserExists_shouldReturnResult() {
        UserEntity entity = mockEntity();
        when(userRepository.findByUsername(entity.getUsername()))
                .thenReturn(Optional.of(entity));
        UserEntity user = userService.findUser(entity.getUsername());
        assertEquals(entity, user);
    }

    @Test
    void saveUser_shouldCallCorrectlyRepo() {
        UserEntity entity = mockEntity();
        userService.saveUser(entity);
        verify(userRepository, times(1)).save(entity);
    }

    private List<SimpleGrantedAuthority> getAuthorities (UserEntity entity) {
        return List.of(new SimpleGrantedAuthority(
                "ROLE_" + entity.getUserRole().name()));
    }

    private UserRequest mockRequest(UserEntity entity) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(entity.getUsername());
        userRequest.setEmail(entity.getEmail());
        userRequest.setPassword(entity.getPassword());
        return userRequest;
    }

    private UserEntity mockEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(USERNAME);
        entity.setPassword(USERNAME);
        entity.setEmail("test@test.com");
        entity.setUserRole(UserRole.USER);
        return entity;
    }

}