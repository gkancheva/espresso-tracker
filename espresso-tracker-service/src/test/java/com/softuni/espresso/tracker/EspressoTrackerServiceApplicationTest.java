package com.softuni.espresso.tracker;

import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.service.CoffeeService;
import com.softuni.espresso.tracker.service.UserService;
import com.softuni.espresso.tracker.web.BakeryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EspressoTrackerServiceApplicationTest {

    @Autowired
    private BakeryController controller;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testInitializationOfBeans() {
        assertNotNull(controller);
        assertNotNull(coffeeService);
        assertNotNull(userRepository);
        assertNotNull(userDetailsService);
        assertTrue(userDetailsService instanceof UserService);
    }

}