package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.Bakery;
import com.softuni.espresso.tracker.model.BakeryWithCoffees;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.service.BakeryService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class BakeryController {

    private final BakeryService bakeryService;

    @GetMapping(value = "/bakeries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Bakery>> getBakeries() {
        List<Bakery> bakeries = bakeryService.getAllBakeries();
        return ResponseEntity.ok(bakeries);
    }

    @PostMapping(value = "/bakeries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createBakery(@Valid @RequestBody @NotNull BakeryRequest request) {
        long bakeryId = bakeryService.createBakery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bakeryId);
    }

    @GetMapping(value = "/bakeries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BakeryWithCoffees> getBakery(@PathVariable @NotNull Long id) {
        BakeryWithCoffees bakery = bakeryService.getBakery(id);
        return ResponseEntity.ok().body(bakery);
    }

}
