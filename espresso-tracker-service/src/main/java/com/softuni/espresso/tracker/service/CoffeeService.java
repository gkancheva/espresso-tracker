package com.softuni.espresso.tracker.service;

import com.softuni.espresso.tracker.model.mapper.CoffeeMapper;
import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.repository.BakeryRepository;
import com.softuni.espresso.tracker.repository.CoffeeRepository;
import com.softuni.espresso.tracker.repository.entities.BakeryEntity;
import com.softuni.espresso.tracker.repository.entities.CoffeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.enterprise.context.ContextException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final BakeryRepository bakeryRepository;
    private final CoffeeRepository coffeeRepository;
    private final CoffeeMapper coffeeMapper;

    public long createCoffee(CoffeeRequest request) {
        Optional<BakeryEntity> bakery = bakeryRepository.findById(request.getBakeryId());
        if (bakery.isEmpty()) {
            throw new ContextException("Bakery with id: " + request.getBakeryId() + " does not exist");
        }
        CoffeeEntity coffeeEntity = coffeeMapper.mapToEntity(request);
        coffeeEntity.setActive(1);
        coffeeEntity.setDateCreated(LocalDate.now());
        coffeeEntity.setBakery(bakery.get());
        try {
            CoffeeEntity result = coffeeRepository.save(coffeeEntity);
            return result.getId();
        } catch (Exception e) {
            throw new ContextException(e.getMessage());
        }
    }

}
