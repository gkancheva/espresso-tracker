package com.softuni.espresso.tracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonHelper {

    private static final ObjectMapper JSON_OBJ_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static ObjectMapper getMapper() {
        return JSON_OBJ_MAPPER;
    }

}