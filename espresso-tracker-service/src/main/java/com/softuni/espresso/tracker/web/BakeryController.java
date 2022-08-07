package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.Bakery;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.service.BakeryService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BakeryController {

    private final BakeryService bakeryService;

    @GetMapping("/bakeries")
    public ResponseEntity<List<Bakery>> getBakeries() {
        List<Bakery> bakeries = bakeryService.getAllBakeries();
        return ResponseEntity.ok(bakeries);
    }

    @PostMapping("/bakeries")
    public ResponseEntity<Long> createBakery(@RequestBody @NotNull BakeryRequest request) {
        long bakeryId = bakeryService.createBakery(request);
        return ResponseEntity.ok(bakeryId);
    }

}
