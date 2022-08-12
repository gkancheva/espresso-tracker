package com.softuni.espresso.tracker.web;

import com.softuni.espresso.tracker.model.web.EspressoSettingRequest;
import com.softuni.espresso.tracker.service.EspressoSettingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EspressoSettingController.class)
@AutoConfigureMockMvc(addFilters = false)
class EspressoSettingControllerTest {

    private static final String PATH = "/espresso-settings";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EspressoSettingService service;

    @Test
    void createEspressoSetting_maxDoseExceeded() throws Exception {
        EspressoSettingRequest request = mockRequest();
        request.setDose(5);
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEspressoSetting_minTemperatureExceeded() throws Exception {
        EspressoSettingRequest request = mockRequest();
        request.setBrewingTemperature(50);
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEspressoSetting_finessIsEmpty() throws Exception {
        EspressoSettingRequest request = mockRequest();
        request.setGrindingFineness("    ");
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.getMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private EspressoSettingRequest mockRequest() {
        EspressoSettingRequest request = new EspressoSettingRequest();
        request.setDose(18);
        request.setGrindingFineness("finesse");
        request.setBrewingTemperature(92.0);
        request.setBrewingPressure(9);
        request.setVolume(20);
        request.setExtractDurationSec(20);
        return request;
    }

}