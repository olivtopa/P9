package com.mediscreen.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.model.DrNote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "de.flapdoodle.mongodb.embedded.version=5.0.5")
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@DirtiesContext
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoTemplate mongoTemplate;
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testCreate() throws Exception {

        DrNote drNote = new DrNote();
        drNote.setPatientId(123L);
        drNote.setNote("A note");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patHistory/add")
                        .content(mapper.writeValueAsString(drNote))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    public void testCreateError() throws Exception {

        DrNote drNote = new DrNote();
        drNote.setPatientId(43L);
        drNote.setNote("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/patHistory/add")
                        .content(mapper.writeValueAsString(drNote))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdate() throws Exception {
        DrNote drNote = new DrNote();
        drNote.setId("8790");
        drNote.setNote("An other Note");
        drNote.setPatientId(747L);
        insertIntoDB("8790",747L);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/patHistory/8790")
                        .content(mapper.writeValueAsString(drNote))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect((status().isOk()));


    }

    @Test
    public void testUpdateUnknown() throws Exception {

        DrNote drNote = new DrNote();
        drNote.setId("unknownId");
        drNote.setPatientId(123L);
        drNote.setNote("A note");


        mockMvc.perform(
                MockMvcRequestBuilders.put("/patHistory/unknownId")
                        .content(mapper.writeValueAsString(drNote))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }

    @Test
    public void testUpdateError() throws Exception {

        DrNote drNote = new DrNote();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/patHistory/648ad098a4ccc87a42098131")
                        .content(mapper.writeValueAsString(drNote))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void testGetNotes() throws Exception {

        insertIntoDB("155", 96L);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/findByPatientId")
                        .queryParam("patientId", "96")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].note").isNotEmpty());
    }

    @Test
    public void testGetNotesForUnknownPatient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/findByPatientId")
                        .queryParam("patientId", "456")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

    private void insertIntoDB(String id, Long patientId) {
        DrNote drNote = new DrNote();
        drNote.setId(id);
        drNote.setPatientId(patientId);
        drNote.setNote("A note");
        mongoTemplate.save(drNote);
        
    }
}