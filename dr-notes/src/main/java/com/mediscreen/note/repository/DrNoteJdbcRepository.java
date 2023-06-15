package com.mediscreen.note.repository;

import com.mediscreen.note.model.DrNote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DrNoteJdbcRepository extends MongoRepository<DrNote, String> {
    List<DrNote> findByPatientId(Long patientId);
}


