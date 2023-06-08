package com.mediscreen.patientinfo.controller;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PatientUIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientUIController.class);
    private static final String REDIRECT_PATIENTS_LIST = "redirect:/ui/patients/list";

    private final PatientService service;

    @Autowired
    public PatientUIController(PatientService service) {
        this.service = service;
    }

    @RequestMapping("/ui/patients/list")
    public String displayPatientLists(Model model) {
        LOGGER.info("Display patient List page");
        model.addAttribute("patients", service.findAll());
        return "patients/list";
    }

    @RequestMapping("/ui/patients/add")
    public String displayPatientAdd(Patient patient) {
        LOGGER.info("Display add patient form");
        return "patients/add";
    }

    @PostMapping("/ui/patients/add-validate")
    public String create(@Valid Patient patient, BindingResult result, Model model) {
        LOGGER.info("Try creating new patient {}: ", patient);

        if (!result.hasErrors()) {
            service.insert(patient);
            return REDIRECT_PATIENTS_LIST;
        }
        LOGGER.info("Adding patients error ! {} ", result);
        return "patients/add";
    }

    @RequestMapping("/ui/patients/update/{id}")
    public String displayPatientUpdate(@PathVariable Long id, Patient patient, Model model) {
        LOGGER.info("Display update patient form");

        model.addAttribute("patient", service.findById(id));
        return "patients/update";
    }

    @PostMapping("/ui/patients/update-validate")
    public String update(@Valid Patient patient, BindingResult result, Model model) {
        LOGGER.info("Try updating Patient {}: ", patient);

        if (!result.hasErrors()) {
            service.update(patient);
            return REDIRECT_PATIENTS_LIST;
        }
        LOGGER.info("Updating patients error ! {} ", result);
        return "patients/update";
    }


}
