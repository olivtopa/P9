package com.mediscreen.note.controller;

import com.mediscreen.note.model.DrNote;
import com.mediscreen.note.service.DrNoteService;
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
public class UIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UIController.class);
    private static final String REDIRECT_LIST = "redirect:/ui/patients/list";

    private final DrNoteService service;

    @Autowired
    public UIController(DrNoteService service) {
        this.service = service;
    }


    @RequestMapping("/ui/patients/list")
    public String displayPatientLists(Model model) {
        LOGGER.info("Patients List page");
        model.addAttribute("patientIds", service.findPatientIds());
        return "patients/list";
    }

    @RequestMapping("/ui/patients/{id}/{notes}")
    public String displayNoteLists(@PathVariable Long id, Model model) {
        LOGGER.info("Patient Note List page");
        model.addAttribute("notes", service.findByPatientId(id));
        return "notes/list";
    }

    @RequestMapping("/ui/notes/add")
    public String displayNoteAdd(DrNote drNote) {
        LOGGER.info("display add note form");
        return "notes/add";
    }

    @PostMapping("/ui/notes/add-validate")
    public String create(@Valid DrNote drNote, BindingResult result, Model model) {
        LOGGER.info("Adding Note {}: ", drNote);
        if (!result.hasErrors()) {
            service.insert(drNote);
            return REDIRECT_LIST;
        }
        LOGGER.info("Adding note error ! {} ",result);
        return "notes/add";
    }

    @RequestMapping("/ui/notes/update/{id}")
    public String displayNoteUpdate(@PathVariable String id, DrNote drNote, Model model) {
        LOGGER.info("display update note form");
        model.addAttribute("drNote",service.findById(id));
        return "notes/update";
    }

    @PostMapping("/ui/notes/update-validate")
    public String update(@Valid DrNote drNote, BindingResult result, Model model) {
        LOGGER.info("Updating Note {}: ", drNote);
        if (!result.hasErrors()) {
        service.update(drNote);
        return REDIRECT_LIST;
        }
        LOGGER.info("Updating note error ! {} ",result);
        return "notes/update";
    }


}
