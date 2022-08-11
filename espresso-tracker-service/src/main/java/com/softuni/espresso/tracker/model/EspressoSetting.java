package com.softuni.espresso.tracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EspressoSetting {
    private long id;
    private Coffee coffee;
    private CoffeeTool coffeeMachine;
    private CoffeeTool grinder;
    private double dose;
    private String grindingFineness;
    private double brewingTemperature;
    private double brewingPressure;
    private double volume;
    private int extractDurationSec;
}
