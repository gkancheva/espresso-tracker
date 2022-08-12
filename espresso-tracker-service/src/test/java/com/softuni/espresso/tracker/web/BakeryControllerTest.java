package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.BakeryWithCoffees;
import com.softuni.espresso.tracker.model.web.BakeryRequest;
import com.softuni.espresso.tracker.service.BakeryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.enterprise.context.ContextException;
import java.util.ArrayList;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BakeryController.class)
@AutoConfigureMockMvc(addFilters = false)
class BakeryControllerTest {

    private static final String BAKERIES_PATH = "/bakeries";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BakeryService bakeryService;

    @Test
    void getBakeries() throws Exception {
        when(bakeryService.getAllBakeries()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(BAKERIES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createBakery_whenInvalidData_expectBadRequest() throws Exception {
        BakeryRequest request = new BakeryRequest();
        mockMvc.perform(post(BAKERIES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBakery_whenValidData_expectCreatedAndResponse() throws Exception {
        BakeryRequest request = new BakeryRequest();
        request.setName("test name");
        request.setAddress("address");
        request.setWebSite("www.bakery.com");
        request.setPhone("+359555666333");
        when(bakeryService.createBakery(request)).thenReturn(1L);
        mockMvc.perform(post(BAKERIES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void createBakery_whenBakeryServiceThrowsException_expectException() throws Exception {
        BakeryRequest request = new BakeryRequest();
        request.setName("test name");
        request.setAddress("address");
        request.setWebSite("www.bakery.com");
        request.setPhone("+359555666333");
        String expectedException = "test exception";
        doThrow(new ContextException(expectedException)).when(bakeryService).createBakery(request);
        mockMvc.perform(post(BAKERIES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedException));
    }

    @Test
    void getBakery_whenPathVarExists_shouldReturnCorrect() throws Exception {
        when(bakeryService.getBakery(1L)).thenReturn(new BakeryWithCoffees());
        mockMvc.perform(get(BAKERIES_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void getBakery_whenPathVarIsMissingExists_expectBadRequest() throws Exception {
        when(bakeryService.getBakery(1L)).thenReturn(new BakeryWithCoffees());
        mockMvc.perform(get(BAKERIES_PATH + "/someStringId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}