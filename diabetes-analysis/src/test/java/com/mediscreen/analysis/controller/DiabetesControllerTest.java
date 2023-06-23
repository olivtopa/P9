package com.mediscreen.analysis.controller;

import com.mediscreen.analysis.service.DiabetesAssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiabetesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiabetesAssessmentService service;

    @Test
    public void testAssessForId() throws Exception {


        when(service.generateFor(anyLong())).thenReturn("result from service");

        mockMvc.perform(MockMvcRequestBuilders.post("/assess/id")
                .queryParam("patId","2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("result from service"));
    }

    @Test
    public void testAssessForIdUnknown() throws Exception {

        when(service.generateFor(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/assess/id")
                .queryParam("patId","2")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
    }

    @Test
    public void testAssessForFamilyName() throws Exception {

        when(service.generateFor(anyString())).thenReturn("result from service");

        mockMvc.perform(MockMvcRequestBuilders.post("/assess/familyName")
                        .queryParam("familyName", "family")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("result from service"));
    }

    @Test
    public void testAssessForFamilyNameUnknown() throws Exception {

        when(service.generateFor(anyString())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/assess/familyName")
                .queryParam("familyName", "family")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }


}