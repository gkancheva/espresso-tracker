package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.Coffee;
import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CoffeeController {

    private final CoffeeService coffeeService;

    @PostMapping(value = "/coffees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCoffee(@Valid @RequestBody CoffeeRequest request) {
        long coffeeId = coffeeService.createCoffee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(coffeeId);
    }

    @GetMapping(value = "/coffees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coffee>> getCoffees() {
        List<Coffee> coffees = coffeeService.getCoffees();
        return ResponseEntity.ok(coffees);
    }

}
