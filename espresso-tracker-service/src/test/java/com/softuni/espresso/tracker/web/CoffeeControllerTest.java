package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.Coffee;
import com.softuni.espresso.tracker.model.web.CoffeeRequest;
import com.softuni.espresso.tracker.service.CoffeeService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoffeeControllerTest {

    private static final String PATH = "/coffees";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeService coffeeService;

    @Test
    void createCoffee_shouldCallService_expectStatusIsOk() throws Exception {
        CoffeeRequest request = mockRequest();
        when(coffeeService.createCoffee(request)).thenReturn(2L);
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("2"));
        verify(coffeeService, times(1)).createCoffee(request);
    }

    @Test
    void createCoffee_whenDateIsFuture_expectBadRequest() throws Exception {
        CoffeeRequest request = mockRequest();
        request.setRoastedOnDate(LocalDate.now().plusDays(1));
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Roasted on date cannot be in the future"));
    }

    @Test
    void getCoffees_expectStatusIsOk() throws Exception {
        List<Coffee> coffees = mockCoffees();
        when(coffeeService.getCoffees()).thenReturn(coffees);
        Coffee expected = coffees.get(0);
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(mockRequest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(expected.getId() + ""))
                .andExpect(jsonPath("$[0].bakery").value(expected.getBakery()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].origin").value(expected.getOrigin()))
                .andExpect(jsonPath("$[0].description").value(IsNull.nullValue()))
                .andExpect(jsonPath("$[0].roastedOnDate").value("2022-08-13"))
                .andExpect(jsonPath("$[0].active").value(expected.getActive() + ""));

    }
    private CoffeeRequest mockRequest() {
        CoffeeRequest request = new CoffeeRequest();
        request.setName("Bakery name");
        request.setBakeryId(1L);
        request.setOrigin("Kenya");
        request.setDescription("Some description");
        request.setRoastedOnDate(LocalDate.now().minusDays(1));
        return request;
    }

    private List<Coffee> mockCoffees() {
        return List.of(
                new Coffee(
                        1, "coffee name",
                        "Bakery",
                        "Origin",
                        null,
                        LocalDate.now(), 1)
        );
    }

}