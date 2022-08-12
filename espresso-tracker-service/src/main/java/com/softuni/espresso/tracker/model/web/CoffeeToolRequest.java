package com.softuni.espresso.tracker.model.web;

import com.softuni.espresso.tracker.model.CoffeeToolType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CoffeeToolRequest {

    private CoffeeToolType coffeeToolType;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
