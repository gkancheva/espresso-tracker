package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EspressoSettingRequest {
    private long coffeeId;

    @Size(min = 5, max = 25)
    private double dose;

    @NotEmpty
    private String grindingFineness;

    @Size(min = 80, max = 100)
    private double brewingTemperature;

    @Size(min = 2, max = 16)
    private double brewingPressure;

    @Size(min = 5, max = 150)
    private double volume;

    @Size(min = 10, max = 50)
    private int extractDurationSec;

}
