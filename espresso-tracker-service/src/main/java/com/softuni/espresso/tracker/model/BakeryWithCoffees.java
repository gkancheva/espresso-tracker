package com.softuni.espresso.tracker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BakeryWithCoffees {
    private Bakery bakery;
    private List<Coffee> coffees;
}
