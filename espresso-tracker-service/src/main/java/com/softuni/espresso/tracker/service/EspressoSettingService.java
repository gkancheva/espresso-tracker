package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.EspressoSetting;
import com.softuni.espresso.tracker.model.entities.CoffeeEntity;
import com.softuni.espresso.tracker.model.mapper.EspressoSettingMapper;
import com.softuni.espresso.tracker.model.web.EspressoSettingRequest;
import com.softuni.espresso.tracker.repository.CoffeeRepository;
import com.softuni.espresso.tracker.repository.EspressoSettingsRepository;
import com.softuni.espresso.tracker.model.entities.EspressoSettingEntity;
import com.softuni.espresso.tracker.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.enterprise.context.ContextException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspressoSettingService {

    private final UserService userService;
    private final EspressoSettingsRepository espressoSettingsRepository;
    private final CoffeeRepository coffeeRepository;
    private final EspressoSettingMapper mapper;

    public List<EspressoSetting> getCoffees(String username) {
        UserEntity user = userService.findUser(username);

        List<EspressoSettingEntity> espressoSettingsEntities = espressoSettingsRepository
                .findAllByUserId(user.getId());

        return espressoSettingsEntities.stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toList());
    }

    public Long createNewSetting(EspressoSettingRequest request, String username) {
        UserEntity user = userService.findUser(username);
        EspressoSettingEntity entity = mapper.mapToEntity(request);
        Optional<CoffeeEntity> optCoffee = coffeeRepository.findById(request.getCoffeeId());
        if (optCoffee.isEmpty()) {
             throw new ContextException("Missing coffee with id: " + request.getCoffeeId());
        }
        entity.setUser(user);
        entity.setCoffee(optCoffee.get());
        entity.setCoffeeMachine(user.getCoffeeMachine());
        entity.setGrinder(user.getGrinder());
        try {
            EspressoSettingEntity result = espressoSettingsRepository.save(entity);
            return result.getId();
        } catch (Exception e) {
            throw new ContextException("Exception while persisting to database: " + e.getMessage(), e);
        }
    }

}
