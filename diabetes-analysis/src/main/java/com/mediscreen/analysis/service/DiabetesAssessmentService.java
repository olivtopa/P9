package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.Assessment;
import com.mediscreen.analysis.model.Patient;
import com.mediscreen.analysis.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * determines the level of risk and the conclusion requested for a patient
 */
@Service
public class DiabetesAssessmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesAssessmentService.class);
    private final PatientRepository patientRepository;
    private final DiabetesAnalyzer diabetesAnalyzer;
    private final TriggerTermCounter counter;


    @Autowired
    public DiabetesAssessmentService(PatientRepository patientRepository, DiabetesAnalyzer diabetesAnalyzer, TriggerTermCounter counter) {
        this.patientRepository = patientRepository;
        this.diabetesAnalyzer = diabetesAnalyzer;
        this.counter = counter;
    }

    public String generateFor(Long patientId) {

        return access(patientRepository.getPatientData(patientId));
    }

    public String generateFor(String familyName) {

        return access(patientRepository.getPatientData(familyName));
    }

    private String access(Patient patient) {

        if (patient == null) {
            throw new IllegalArgumentException("Unknown patient");
        }
        int numTriggerTerms;
        int age = patient.getAge();

        numTriggerTerms = counter.getNumberOfTriggerTerms(patientRepository.getDrNotesForPatient(patient.getId()));

        Assessment assessment = diabetesAnalyzer.getAssessmentValue(age, numTriggerTerms, patient.getSex());

        // Info: example :70 ans , 2 terms, sex F: assessment.getValue() "Borderline"

        LOGGER.info("Patient is {} years old, with {} trigger words, and assessment of: {}",
                age, numTriggerTerms, assessment);

        return generateTextResult(patient, assessment.getValue());
    }

    private String generateTextResult(Patient patient, String assessment) {

        return "Patient: " + patient.getGivenName() + " " + patient.getFamilyName() + " (age " + patient.getAge() + ") diabetes assessment is: " + assessment;
    // Example: Patient: Pippa Rees (age 70) diabetes assessment is: Borderline
    }
}
