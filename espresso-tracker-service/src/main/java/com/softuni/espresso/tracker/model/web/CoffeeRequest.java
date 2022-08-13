package com.softuni.espresso.tracker.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class CoffeeRequest {

    @NotBlank(message = "Coffee name is required")
    @Size(min = 3, max = 100,
            message = "Coffee name must be at between 3 and 100 characters length")
    private String name;

    private long bakeryId;

    @PastOrPresent(message = "Roasted on date cannot be in the future")
    private LocalDate roastedOnDate;

    @NotBlank(message = "Coffee origin is required")
    @Size(min = 3, max = 100,
            message = "Coffee origin must be at between 3 and 100 characters length")
    private String origin;

    private String description;

}
