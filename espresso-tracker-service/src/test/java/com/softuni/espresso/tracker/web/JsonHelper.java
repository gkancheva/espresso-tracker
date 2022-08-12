package com.softuni.espresso.tracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    private static final ObjectMapper JSON_OBJ_MAPPER = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return JSON_OBJ_MAPPER;
    }

}