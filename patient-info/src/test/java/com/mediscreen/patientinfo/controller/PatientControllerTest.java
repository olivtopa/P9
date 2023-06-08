package com.mediscreen.patientinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientinfo.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreate() throws Exception {

        Patient patient = buildPatient();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patients")
                        .content(mapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    public void testCreateError() throws Exception {

        Patient patientWithoutAnyValue = new Patient();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patients")
                        .content(mapper.writeValueAsString(patientWithoutAnyValue))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Sql("/get_data.sql")
    @Test
    public void testGet() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/patients/150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("givenName").value("First"))
                .andExpect(jsonPath("familyName").value("Patient"))
                .andExpect(jsonPath("birthDate").exists())
                .andExpect(jsonPath("sex").value("M"))
                .andExpect(jsonPath("homeAddress").value("12 Boulevard des Belges Paris"))
                .andExpect(jsonPath("phoneNumber").value("0102030405"));
    }

    @Test
    public void testGetUnknown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/100"))
                .andExpect(status().isNotFound());
    }

    @Sql("/get_data.sql")
    @Test
    public void testGetByFamilyName() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/patients/getByFamilyName").queryParam("familyName", "Patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(150))
                .andExpect(jsonPath("givenName").value("First"))
                .andExpect(jsonPath("birthDate").exists())
                .andExpect(jsonPath("sex").value("M"))
                .andExpect(jsonPath("homeAddress").value("12 Boulevard des Belges Paris"))
                .andExpect(jsonPath("phoneNumber").value("0102030405"));
        ;
    }

    @Test
    public void testGetByFamilyNameUnknown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/getByFamilyName").queryParam("familyName", "unknown"))
                .andExpect(status().isNotFound());


    }

    @Sql("/delete_data.sql")
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/150"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUnknown() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/50"))
                .andExpect(status().isNotFound());
    }

    @Sql("/update_data.sql")
    @Test
    public void testUpdate() throws Exception {
        Patient patient = buildPatient();

        patient.setId(55L);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/patients")
                        .content(mapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUnknown() throws Exception {
        Patient patient = buildPatient();

        patient.setId(66L);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/patients")
                        .content(mapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Patient patient = buildPatient();
        mockMvc.perform(

                MockMvcRequestBuilders.put("/patients")
                        .content(mapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Sql("/get_all_data.sql")
    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("[0].givenName").value("First"))
                .andExpect(jsonPath("[0].familyName").value("Patient"))
                .andExpect(jsonPath("[0].sex").value("M"))
                .andExpect(jsonPath("[0].homeAddress").value("12 Boulevard des Belges Paris"))
                .andExpect(jsonPath("[0].phoneNumber").value("0102030405"));
    }

    private static Patient buildPatient() {
        Patient patient = new Patient();
        patient.setGivenName("TheName");
        patient.setFamilyName("TheFamilyName");
        patient.setSex("M");
        patient.setBirthDate(new Date());
        patient.setHomeAddress("4 Boulevard des Capucines Paris");
        patient.setPhoneNumber("0102030405");
        return patient;
    }

}