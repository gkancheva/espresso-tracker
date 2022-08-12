package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CoffeeRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    private Long bakeryId;

    @PastOrPresent
    private LocalDate roastedOnDate;

    @NotBlank
    @Size(min = 3, max = 100)
    private String origin;

    private String description;

}
