package com.mediscreen.note.service;

import com.mediscreen.note.model.DrNote;
import com.mediscreen.note.repository.DrNoteJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DrNoteService {

    private final DrNoteJdbcRepository repository;

    public DrNoteService(DrNoteJdbcRepository repository) {
        this.repository = repository;
    }

    public DrNote insert(DrNote drNote) {
        return repository.insert(drNote);
    }
    public DrNote update(DrNote drNote) {

        if(repository.findById(drNote.getId()).isEmpty()) {
            throw new IllegalArgumentException();
        } else repository.save(drNote);
        return drNote;
    }

    public List<DrNote> findByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public Optional<DrNote> findById(String id) {
        return repository.findById(id);
    }

    public List<Long> findPatientIds() {

        return repository.findAll().stream().map(DrNote::getPatientId).collect(Collectors.toList());
    }
}
