package com.mediscreen.analysis.controller;

import com.mediscreen.analysis.service.DiabetesAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiabetesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesController.class);

    private final DiabetesAssessmentService diabetesAssessmentService;

    @Autowired
    public DiabetesController(DiabetesAssessmentService diabetesAssessmentService) {
        this.diabetesAssessmentService = diabetesAssessmentService;
    }

    @PostMapping("/assess/id")
    public ResponseEntity<String> assessPatient(@RequestParam(value = "patId") Long patientId) {
        try {
            LOGGER.info("Generating assessment for " + patientId);
            return ResponseEntity.ok(diabetesAssessmentService.generateFor(patientId));
        } catch (RuntimeException exception) {
            LOGGER.error("Error generating assessment for " + patientId, exception);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/assess/familyName")
    public ResponseEntity<String> assessPatient(@RequestParam(value = "familyName") String familyName) {
        try {
            LOGGER.info("Generating assessment for " + familyName);
            return ResponseEntity.ok(diabetesAssessmentService.generateFor(familyName));
        } catch (RuntimeException exception) {
            LOGGER.info("Error generating assessment for " + familyName, exception);
            return ResponseEntity.notFound().build();
        }
    }


}
