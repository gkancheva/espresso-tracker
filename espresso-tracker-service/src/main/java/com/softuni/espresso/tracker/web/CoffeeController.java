package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @PostMapping("/coffees")
    public ResponseEntity<Long> createCoffee(@RequestBody CoffeeRequest request) {
        long coffeeId = coffeeService.createCoffee(request);
        return ResponseEntity.ok(coffeeId);
    }

}
