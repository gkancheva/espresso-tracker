package com.softuni.espresso.tracker.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class BakeryRequest {

    private static final int MIN_LENGTH = 3;

    @NotBlank(message = "Name must be provided")
    @Size(min = MIN_LENGTH, max = 255)
    private String name;

    @NotBlank
    @Size(min = MIN_LENGTH)
    private String address;

    @NotBlank
    @Size(min = MIN_LENGTH, max = 30)
    private String phone;

    @NotBlank
    @Size(min = MIN_LENGTH)
    private String webSite;

    private String imgSource;
}
