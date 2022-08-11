package com.softuni.espresso.tracker.model.web;

import com.softuni.espresso.tracker.model.CoffeeTool;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoffeeToolsResponse {
    private CoffeeTool coffeeMachine;
    private CoffeeTool grinder;
}
