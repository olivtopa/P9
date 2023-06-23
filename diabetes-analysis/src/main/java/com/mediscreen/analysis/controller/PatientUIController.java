package com.mediscreen.analysis.controller;

import com.mediscreen.analysis.model.Patient;
import com.mediscreen.analysis.repository.PatientRepository;
import com.mediscreen.analysis.service.DiabetesAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PatientUIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientUIController.class);

    private final PatientRepository repository;

    private final DiabetesAssessmentService service;

    @Autowired
    public PatientUIController(PatientRepository repository, DiabetesAssessmentService service) {
        this.repository = repository;
        this.service = service;
    }

    @RequestMapping("/ui/patients/list")
    public String displayPatientLists(Model model) {
        LOGGER.info("Display patient List page");
        model.addAttribute("patients", repository.findAll());
        return "patients/list";
    }

    @RequestMapping("/ui/patients/{id}")
    public String displayPatientLists(@PathVariable Long id, Patient patient, Model model) {
        LOGGER.info("display patient");
        model.addAttribute("patient", repository.getPatientData(id));
        model.addAttribute("assessment", service.generateFor(id));

        return "patients/patient";
    }


}
