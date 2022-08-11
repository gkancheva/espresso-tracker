package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.CoffeeTool;
import com.softuni.espresso.tracker.model.mapper.CoffeeToolMapper;
import com.softuni.espresso.tracker.model.web.CoffeeToolRequest;
import com.softuni.espresso.tracker.model.web.CoffeeTools;
import com.softuni.espresso.tracker.repository.CoffeeToolRepository;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.repository.entities.CoffeeToolEntity;
import com.softuni.espresso.tracker.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.enterprise.context.ContextException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoffeeToolsService {

    private final UserRepository userRepository;
    private final CoffeeToolRepository coffeeToolRepository;
    private final CoffeeToolMapper coffeeToolMapper;

    public CoffeeTools getCoffeeTools(String username) {
        UserEntity user = findUser(username);
        return convertToResponse(user);
    }

    public CoffeeTools createUpdateCoffeeTool(String username, CoffeeToolRequest request) {
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

        UserEntity user = findUser(username);
        switch (request.getCoffeeToolType()) {
            case GRINDER -> user.setGrinder(coffeeToolEntity);
            case COFFEE_MACHINE -> user.setCoffeeMachine(coffeeToolEntity);
        }
        UserEntity result = userRepository.save(user);
        return convertToResponse(result);
    }

    private CoffeeTools convertToResponse (UserEntity user) {
        CoffeeTool coffeeMachine = coffeeToolMapper.map(user.getCoffeeMachine());
        CoffeeTool grinder = coffeeToolMapper.map(user.getGrinder());

        return CoffeeTools.builder()
                .coffeeMachine(coffeeMachine)
                .grinder(grinder)
                .build();
    }

    private UserEntity findUser (String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ContextException("Missing information about user");
        }
        return user.get();
    }

}
