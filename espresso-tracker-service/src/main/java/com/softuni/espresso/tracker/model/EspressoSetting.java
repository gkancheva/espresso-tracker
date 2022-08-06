package com.softuni.espresso.tracker.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EspressoSetting {
    private final long id;
    private final Coffee coffee;
    private final double dose;
    private final String grindingFineness;
    private final double brewingTemperature;
    private final double brewingPressure;
    private final double volume;
}
