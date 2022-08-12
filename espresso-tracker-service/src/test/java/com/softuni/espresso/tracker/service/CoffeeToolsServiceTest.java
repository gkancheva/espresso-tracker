package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CoffeeToolType;
import com.softuni.espresso.tracker.model.entities.CoffeeToolEntity;
import com.softuni.espresso.tracker.model.entities.UserEntity;
import com.softuni.espresso.tracker.model.mapper.CoffeeToolMapper;
import com.softuni.espresso.tracker.model.mapper.CoffeeToolMapperImpl;
import com.softuni.espresso.tracker.model.web.CoffeeToolRequest;
import com.softuni.espresso.tracker.model.web.CoffeeToolsResponse;
import com.softuni.espresso.tracker.repository.CoffeeToolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeToolsServiceTest {

    @InjectMocks
    private CoffeeToolsService service;

    @Mock
    private UserService userService;

    @Mock
    private CoffeeToolRepository coffeeToolRepository;

    @Spy
    private CoffeeToolMapper coffeeToolMapper = new CoffeeToolMapperImpl();

    @Captor
    private ArgumentCaptor<CoffeeToolEntity> coffeeToolArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserEntity> userArgumentCaptor;

    @Test
    void getCoffeeTools_whenUserExists_shouldReturn() {
        UserEntity userEntity = mockUserEntity();
        when(userService.findUser(userEntity.getUsername())).thenReturn(userEntity);
        CoffeeToolsResponse coffeeTools = service.getCoffeeTools(userEntity.getUsername());
        assertNotNull(coffeeTools);
        assertNotNull(coffeeTools.getCoffeeMachine());
        assertNotNull(coffeeTools.getGrinder());
    }

    @Test
    void createUpdateCoffeeTool_whenSuchToolExists_shouldUpdateUser() {
        UserEntity userEntity = mockUserEntity();
        CoffeeToolRequest request = mockRequest();
        CoffeeToolEntity entity = mockToolEntity(request);
        when(coffeeToolRepository
                .findByCoffeeToolTypeAndAndName(request.getCoffeeToolType(), request.getName()))
                .thenReturn(Optional.of(entity));
        when(userService.findUser(userEntity.getUsername())).thenReturn(userEntity);
        when(userService.saveUser(userEntity)).thenReturn(userEntity);

        service.createUpdateCoffeeTool(userEntity.getUsername(), request);

        verify(coffeeToolRepository, never()).save(any());
        verify(userService, times(1)).findUser(userEntity.getUsername());
        verify(userService, times(1)).saveUser(userArgumentCaptor.capture());
        UserEntity userResult = userArgumentCaptor.getValue();
        assertNotNull(userResult);
        assertEquals(request.getCoffeeToolType(), userResult.getGrinder().getCoffeeToolType());
        assertEquals(request.getName(), userResult.getGrinder().getName());
    }

    @Test
    void createUpdateCoffeeTool_whenSuchToolDoesNotExist_shouldInsertToolAndUpdateUser() {
        UserEntity userEntity = mockUserEntity();
        CoffeeToolRequest request = mockRequest();
        when(coffeeToolRepository
                .findByCoffeeToolTypeAndAndName(request.getCoffeeToolType(), request.getName()))
                .thenReturn(Optional.empty());
        when(userService.findUser(userEntity.getUsername())).thenReturn(userEntity);
        when(userService.saveUser(userEntity)).thenReturn(userEntity);
        when(coffeeToolRepository.save(any())).thenReturn(mockToolEntity(request));

        service.createUpdateCoffeeTool(userEntity.getUsername(), request);

        verify(coffeeToolRepository, times(1)).save(coffeeToolArgumentCaptor.capture());
        CoffeeToolEntity coffeeToolResult = coffeeToolArgumentCaptor.getValue();
        assertNotNull(coffeeToolResult);
        assertEquals(request.getCoffeeToolType(), coffeeToolResult.getCoffeeToolType());
        assertEquals(request.getName(), coffeeToolResult.getName());
        verify(userService, times(1)).saveUser(userArgumentCaptor.capture());
        UserEntity userResult = userArgumentCaptor.getValue();
        assertNotNull(userResult);
        assertEquals(request.getCoffeeToolType(), userResult.getGrinder().getCoffeeToolType());
        assertEquals(request.getName(), userResult.getGrinder().getName());
    }

    private CoffeeToolRequest mockRequest() {
        CoffeeToolRequest request = new CoffeeToolRequest();
        request.setCoffeeToolType(CoffeeToolType.GRINDER);
        request.setName("test");
        return request;
    }

    private UserEntity mockUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername("test-user");
        entity.setCoffeeMachine(new CoffeeToolEntity());
        entity.setGrinder(new CoffeeToolEntity());
        return entity;
    }

    private CoffeeToolEntity mockToolEntity(CoffeeToolRequest request) {
        CoffeeToolEntity tool = new CoffeeToolEntity();
        tool.setCoffeeToolType(request.getCoffeeToolType());
        tool.setName(request.getName());
        return tool;
    }

}