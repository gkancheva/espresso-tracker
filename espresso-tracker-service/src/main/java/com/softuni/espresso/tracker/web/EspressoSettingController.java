package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.EspressoSetting;
import com.softuni.espresso.tracker.service.EspressoSettingService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EspressoSettingController {

    private final EspressoSettingService espressoSettingService;

    @GetMapping(value = "{username}/espresso-settings", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EspressoSetting>> getEspressoSettings(@NotNull @PathVariable("username") final String username) {
        List<EspressoSetting> coffees = espressoSettingService.getCoffees(username);
        return ResponseEntity.ok(coffees);
    }

}
