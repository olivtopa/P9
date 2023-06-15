package com.mediscreen.note.controller;

import com.mediscreen.note.model.DrNote;
import com.mediscreen.note.service.DrNoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private final DrNoteService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    public NoteController(DrNoteService service) {
        this.service = service;
    }

    @PostMapping("/patHistory/add")
    public DrNote createNote(@RequestBody @Valid DrNote drNote) {

        LOGGER.info("Creating note " + drNote);

        return service.insert(drNote);
    }

    @PutMapping("/patHistory/{id}")
    public ResponseEntity<DrNote> updateNote(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid DrNote drNote) {

        LOGGER.info("Updating note " + drNote);

        try {
            return ResponseEntity.ok(service.update(drNote));
        } catch (IllegalArgumentException exception) {

            LOGGER.info("Updating note " + drNote + " failed!");

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patHistory/findByPatientId")
    public List<DrNote> getNotes(@RequestParam("patientId") Long patientId) {

        LOGGER.info("Fetching notes for patient " + patientId);

        return service.findByPatientId(patientId);
    }
}
