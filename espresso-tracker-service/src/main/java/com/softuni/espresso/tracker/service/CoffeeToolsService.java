package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CoffeeTool;
import com.softuni.espresso.tracker.model.mapper.CoffeeToolMapper;
import com.softuni.espresso.tracker.model.web.CoffeeToolRequest;
import com.softuni.espresso.tracker.model.web.CoffeeToolsResponse;
import com.softuni.espresso.tracker.repository.CoffeeToolRepository;
import com.softuni.espresso.tracker.model.entities.CoffeeToolEntity;
import com.softuni.espresso.tracker.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoffeeToolsService {

    private final UserService userService;
    private final CoffeeToolRepository coffeeToolRepository;
    private final CoffeeToolMapper coffeeToolMapper;

    public CoffeeToolsResponse getCoffeeTools(String username) {
        UserEntity user = userService.findUser(username);
        return convertToResponse(user);
    }

    public CoffeeToolsResponse createUpdateCoffeeTool(String username, CoffeeToolRequest request) {
        Optional<CoffeeToolEntity> optCoffeeTool = coffeeToolRepository
                .findByCoffeeToolTypeAndAndName(request.getCoffeeToolType(), request.getName());
        CoffeeToolEntity coffeeToolEntity;
        if (optCoffeeTool.isEmpty()) {
            CoffeeToolEntity entity = new CoffeeToolEntity();
            entity.setCoffeeToolType(request.getCoffeeToolType());
            entity.setName(request.getName());
            coffeeToolEntity = coffeeToolRepository.save(entity);
        } else {
            coffeeToolEntity = optCoffeeTool.get();
        }

        UserEntity user = userService.findUser(username);
        switch (request.getCoffeeToolType()) {
            case GRINDER -> user.setGrinder(coffeeToolEntity);
            case COFFEE_MACHINE -> user.setCoffeeMachine(coffeeToolEntity);
        }
        UserEntity result = userService.saveUser(user);
        return convertToResponse(result);
    }

    private CoffeeToolsResponse convertToResponse (UserEntity user) {
        CoffeeTool coffeeMachine = coffeeToolMapper.map(user.getCoffeeMachine());
        CoffeeTool grinder = coffeeToolMapper.map(user.getGrinder());

        return CoffeeToolsResponse.builder()
                .coffeeMachine(coffeeMachine)
                .grinder(grinder)
                .build();
    }

}
