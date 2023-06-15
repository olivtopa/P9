package com.mediscreen.note.service;

import com.mediscreen.note.model.DrNote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(properties = "de.flapdoodle.mongodb.embedded.version=5.0.5")
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@DirtiesContext
class DrNoteServiceTest {

    @Autowired
    private DrNoteService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findPatientIds() {

        int initialSize = service.findPatientIds().size();

        createAndInsertNote(888L, "888");

        assertThat(service.findPatientIds())
                .hasSize(initialSize + 1)
                .contains(888L);
    }

    @Test
    void create() {

        DrNote drNote = new DrNote();
        drNote.setPatientId(33L);
        drNote.setNote("a note");

        assertThat(service.insert(drNote).getId()).isNotNull();
    }

    @Test
    void update() {

        createAndInsertNote(56L, "1234");

        DrNote drNote = new DrNote();
        drNote.setId("1234");
        drNote.setPatientId(56L);
        drNote.setNote("an updated note");

        assertThat(service.update(drNote)).isNotNull();
    }

    @Test
    void updateUnknown() {

        assertThatThrownBy(() -> service.update(new DrNote()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findById() {

        String id = "100100";
        long patientId = 100L;
        createAndInsertNote(patientId, id);

        Optional<DrNote> drNoteOpt = service.findById(id);

        assertThat(drNoteOpt).isNotEmpty();
        DrNote drNote = drNoteOpt.get();
        assertThat(drNote.getId()).isEqualTo(id);
        assertThat(drNote.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void findByIdUnknown() {
        Optional<DrNote> drNoteOpt = service.findById(anyString());
        assertThat(drNoteOpt).isNotPresent();
    }

    private void createAndInsertNote(long patientId, String number) {
        DrNote drNote = new DrNote();
        drNote.setPatientId(patientId);
        drNote.setId(number);
        drNote.setNote("a note");
        mongoTemplate.save(drNote);
    }

}