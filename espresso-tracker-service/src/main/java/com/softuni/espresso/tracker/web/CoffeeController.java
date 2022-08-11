package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.Coffee;
import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @PostMapping(value = "/coffees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCoffee(@RequestBody CoffeeRequest request) {
        long coffeeId = coffeeService.createCoffee(request);
        return ResponseEntity.ok(coffeeId);
    }

    @GetMapping(value = "/coffees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coffee>> getCoffees() {
        List<Coffee> coffees = coffeeService.getCoffees();
        return ResponseEntity.ok(coffees);
    }

}
