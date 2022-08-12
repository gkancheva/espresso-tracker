package com.softuni.espresso.tracker.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
public class UserRequest {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    @Email
    @Size(min = MIN_LENGTH, max = MAX_LENGTH)
    private String email;

    @NotBlank
    @Size(min = MIN_LENGTH, max = MAX_LENGTH)
    private String username;

    @NotBlank
    @Size(min = MIN_LENGTH, max = MAX_LENGTH)
    private String password;

}
