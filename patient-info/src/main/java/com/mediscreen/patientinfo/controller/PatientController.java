package com.mediscreen.patientinfo.controller;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/patients")
    public Patient create(@RequestBody @Valid Patient patient) {

        LOGGER.info("Creating patient " + patient);

        patientService.insert(patient);
        return patient;
    }

    @RequestMapping("/patients/{id}")
    public ResponseEntity<Patient> get(@PathVariable("id") Long id) {
        Patient patient = patientService.findById(id);

        LOGGER.info("Get patient for id " + id + " : " + patient);

        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(patient);
    }

    @RequestMapping("/patients/getByFamilyName")
    public ResponseEntity<Patient> getByFamilyName(@RequestParam(value = "familyName") String familyName) {
        Patient patient = patientService.findByFamilyName(familyName);

        LOGGER.info("Get by family name" + familyName + " : patient " + patient);

        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean wasDeleted = patientService.deleteById(id);

        LOGGER.info("Deleted patient " + id + " : " + wasDeleted);

        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/patients")
    public ResponseEntity<Patient> update(@RequestBody @Valid Patient patient) {
        boolean wasUpdated = patientService.update(patient);

        LOGGER.info("Updating patient " + patient + " : " + wasUpdated);

        if (wasUpdated) {
            return ResponseEntity.ok(patient);
        }

        return ResponseEntity.badRequest().build();
    }

    @RequestMapping("/patients")
    public List<Patient> getAll() {

        List<Patient> patients = patientService.findAll();

        LOGGER.info("Get All patients " + patients);

        return patients;
    }
}
