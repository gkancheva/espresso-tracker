package com.softuni.espresso.tracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class Coffee {
    private final long id;
    private final String name;
    private final String bakery;
    private final String origin;
    private final String description;
    private final LocalDate roastedOnDate;
    private final int active;
}
