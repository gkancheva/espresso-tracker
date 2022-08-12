package com.softuni.espresso.tracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softuni.espresso.tracker.model.CustomUser;
import com.softuni.espresso.tracker.model.web.UserRequest;
import com.softuni.espresso.tracker.service.UserService;
import com.softuni.espresso.tracker.service.exceptions.UserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    private static final String REGISTER_PATH = "/users/register";
    private static final String LOGIN_PATH = "/users/login";
    private static final String USERNAME = "test";
    private static final String PASS = "test";
    private static final String EMAIL = "test@test.com";
    private static final ObjectMapper JSON_OBJ_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void register_whenAllCorrect_expectStatusOk() throws Exception {
        UserRequest request = new UserRequest();
        request.setPassword(PASS);
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJ_MAPPER.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void register_whenMissingUser_expectStatus400() throws Exception {
        testBadRequest(null);
    }

    @Test
    void register_whenMissingPassword_expectStatus400() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        testBadRequest(request);
    }

    @Test
    void register_whenPasswordLessThan3_expectStatus400() throws Exception {
        UserRequest request = new UserRequest();
        request.setPassword("p");
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        testBadRequest(request);
    }

    @Test
    void register_whenPasswordMoreThan30_expectStatus400() throws Exception {
        UserRequest request = new UserRequest();
        request.setPassword("p".repeat(40));
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        testBadRequest(request);
    }

    @Test
    void register_whenEmptyPass_expectStatus400() throws Exception {
        UserRequest request = new UserRequest();
        request.setPassword("         ");
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        testBadRequest(request);
    }

    @Test
    void register_whenUserServiceThrowsUserException_expectStatusIsConflict() throws Exception {
        doThrow(UserException.class).when(userService).register(any(UserRequest.class));
        mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_whenOk_expectStatusIsOK() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername(USERNAME);
        when(userService.login(request)).thenReturn(
                CustomUser.builder().username(request.getUsername()).build());
        request.setPassword(PASS);
        request.setUsername(USERNAME);
        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJ_MAPPER.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(request.getUsername()));
    }

    @Test
    void login_whenUserServiceThrowsException_expectStatus401() throws Exception {
        UserRequest request = new UserRequest();
        request.setPassword(PASS);
        request.setUsername(USERNAME);
        request.setEmail(EMAIL);
        String expectedErrorMessage = "Test exception";
        doThrow(new UsernameNotFoundException(expectedErrorMessage))
                .when(userService).login(any(UserRequest.class));
        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJ_MAPPER.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    private void testBadRequest(UserRequest request) throws Exception {
        mockMvc.perform(post(REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_OBJ_MAPPER.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}