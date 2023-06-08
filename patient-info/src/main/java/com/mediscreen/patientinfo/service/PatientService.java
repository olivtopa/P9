package com.mediscreen.patientinfo.service;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.repository.PatientJdbcRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientJdbcRepository patientJdbcRepository;

    public PatientService(PatientJdbcRepository patientJdbcRepository) {
        this.patientJdbcRepository = patientJdbcRepository;
    }

    public List<Patient> findAll() {
        try {
            return patientJdbcRepository.findAll();
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Nullable
    public Patient findById(long id) {
        try {
            return patientJdbcRepository.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Nullable
    public Patient findByFamilyName(String familyName) {
        try {
            return patientJdbcRepository.findByFamilyName(familyName);
        } catch (EmptyResultDataAccessException exeException) {
            return null;
        }
    }

    public boolean deleteById(long id) {
        return patientJdbcRepository.deleteById(id) != 0;
    }

    public int insert(Patient patient) {
        return patientJdbcRepository.insert(patient);
    }

    public boolean update(Patient patient) {
        return patientJdbcRepository.update(patient) != 0;

    }
}
