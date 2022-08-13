package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EspressoSettingRequest {
    private long coffeeId;

    @Min(value = 5, message = "Dose must be min 5")
    @Max(value = 25, message = "Dose must be less than 25")
    private float dose;

    @NotBlank
    @Size(max = 150, message = "Grind size mut be less than 150 characters")
    private String grindingFineness;

    @Min(value = 80, message = "Brewing temperature should be less than 80")
    @Max(value = 100, message = "Brewing temperature should be max 100")
    private float brewingTemperature;

    @Min(value = 2, message = "Brewing pressure should be min 2")
    @Max(value = 16, message = "Brewing pressure should be max 16")
    private float brewingPressure;

    @Min(value = 5, message = "Volume should be min 5")
    @Max(value = 150, message = "Volume should be max 150")
    private float volume;

    @Min(value = 10, message = "Extract duration must be min 10")
    @Max(value = 50, message = "Extract duration must be max 50")
    private int extractDurationSec;

}
