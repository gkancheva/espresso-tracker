package com.softuni.espresso.tracker.model.web;

import com.softuni.espresso.tracker.model.CoffeeToolType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoffeeToolRequest {
    private CoffeeToolType coffeeToolType;
    private String name;
}
