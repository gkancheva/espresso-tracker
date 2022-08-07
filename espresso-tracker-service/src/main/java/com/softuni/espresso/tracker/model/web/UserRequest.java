package com.softuni.espresso.tracker.model.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequest {

    private static final int MIN_LENGTH = 3;

    @Email
    @Size(min = MIN_LENGTH, max = 30)
    private String email;

    @NotEmpty
    @Size(min = MIN_LENGTH, max = 30)
    private String username;

    @NotEmpty
    @Size(min = MIN_LENGTH, max = 30)
    private String password;

}
