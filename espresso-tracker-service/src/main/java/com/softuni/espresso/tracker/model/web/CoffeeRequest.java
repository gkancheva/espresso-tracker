package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CoffeeRequest {

    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @NotEmpty
    private Long bakeryId;

    @PastOrPresent
    private LocalDate roastedOnDate;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String origin;

    private String description;

}
