package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.EspressoSetting;
import com.softuni.espresso.tracker.model.web.EspressoSettingRequest;
import com.softuni.espresso.tracker.service.EspressoSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class EspressoSettingController {

    private final EspressoSettingService espressoSettingService;

    @GetMapping(value = "/espresso-settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EspressoSetting>> getEspressoSettings(HttpServletRequest request) {
        List<EspressoSetting> coffees = espressoSettingService.getCoffees(request.getRemoteUser());
        return ResponseEntity.ok(coffees);
    }

    @PostMapping(value = "/espresso-settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createEspressoSetting(
            @Valid @RequestBody @NotNull EspressoSettingRequest request, HttpServletRequest httpRequest) {
        Long result = espressoSettingService.createNewSetting(request, httpRequest.getRemoteUser());
        return ResponseEntity.ok(result);
    }

}
