package com.mediscreen.analysis.service;

import com.mediscreen.analysis.model.Assessment;
import com.mediscreen.analysis.model.Patient;
import com.mediscreen.analysis.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiabetesAssessmentServiceTest {

    @InjectMocks
    private DiabetesAssessmentService diabetesAssessmentService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DiabetesAnalyzer diabetesAnalyzer;

    @Mock
    private TriggerTermCounter counter;

    @Test
    void generateForId() {
        Patient patient = createPatient();
        when(patientRepository.getPatientData(anyLong())).thenReturn(patient);
        when(diabetesAnalyzer.getAssessmentValue(anyInt(), anyInt(), anyString())).thenReturn(Assessment.NONE);

        String value = diabetesAssessmentService.generateFor(1L);

        Assertions.assertThat(value).isEqualTo("Patient: Marc Java (age 53) diabetes assessment is: None");
    }

    @Test
    void generateForIdUnknown() {

        when(patientRepository.getPatientData(anyLong())).thenReturn(null);

        Assertions.assertThatThrownBy(() -> diabetesAssessmentService.generateFor(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void generateForFamilyName() {
        when(patientRepository.getPatientData(anyString())).thenReturn(createPatient());
        when(diabetesAnalyzer.getAssessmentValue(anyInt(), anyInt(), anyString())).thenReturn(Assessment.BORDERLINE);

        String value = diabetesAssessmentService.generateFor("family name");

        Assertions.assertThat(value).isEqualTo("Patient: Marc Java (age 53) diabetes assessment is: Borderline");
    }

    @Test
    void generateForFamilyNameUnknown() {

        when(patientRepository.getPatientData(anyString())).thenReturn(null);

        Assertions.assertThatThrownBy(() -> diabetesAssessmentService.generateFor("family name"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Patient createPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setBirthDate(new Date(0));
        patient.setSex("F");
        patient.setFamilyName("Java");
        patient.setGivenName("Marc");
        return patient;
    }


}