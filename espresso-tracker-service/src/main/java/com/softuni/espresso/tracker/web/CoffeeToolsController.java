package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.web.CoffeeToolRequest;
import com.softuni.espresso.tracker.model.web.CoffeeToolsResponse;
import com.softuni.espresso.tracker.service.CoffeeToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class CoffeeToolsController {

    private final CoffeeToolsService service;

    @GetMapping("/coffee-tools")
    public ResponseEntity<CoffeeToolsResponse> getCoffeeTools(HttpServletRequest httpRequest) {
        CoffeeToolsResponse coffeeToolsResponse = service.getCoffeeTools(httpRequest.getRemoteUser());
        return ResponseEntity.ok(coffeeToolsResponse);
    }

    @PutMapping("/coffee-tools")
    public ResponseEntity<CoffeeToolsResponse> createUpdateCoffeeTool(@RequestBody @NotNull CoffeeToolRequest request, HttpServletRequest httpRequest) {
        CoffeeToolsResponse coffeeToolsResponse = service.createUpdateCoffeeTool(httpRequest.getRemoteUser(), request);
        return ResponseEntity.ok(coffeeToolsResponse);
    }

}