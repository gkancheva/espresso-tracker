package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BakeryRequest {

    private static final int MIN_LENGTH = 3;

    @NotEmpty(message = "Name must be provided")
    @Size(min = MIN_LENGTH)
    private String name;

    @NotEmpty
    @Size(min = MIN_LENGTH)
    private String address;

    @NotEmpty
    @Size(min = MIN_LENGTH, max = 30)
    private String phone;

    @NotEmpty
    @Size(min = MIN_LENGTH)
    private String webSite;

    private String imgSource;
}
