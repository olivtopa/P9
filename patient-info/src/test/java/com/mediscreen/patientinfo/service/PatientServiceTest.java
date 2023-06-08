package com.mediscreen.patientinfo.service;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.repository.PatientJdbcRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientJdbcRepository repository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void findByIdError() {

        when(repository.findById(anyLong())).thenThrow(new EmptyResultDataAccessException(1));

        Patient patient = patientService.findById(1L);

        assertThat(patient).isNull();
    }

    @Test
    public void findByFamilyName() {

        Patient originalPatient = new Patient();
        when(repository.findByFamilyName(anyString())).thenReturn(originalPatient);

        Patient patient = patientService.findByFamilyName("a family name");

        assertThat(patient).isEqualTo(originalPatient);
    }

    @Test
    public void findByFamilyNameError() {


        when(repository.findByFamilyName(anyString())).thenThrow(new EmptyResultDataAccessException(1));

        Patient patient = patientService.findByFamilyName("a family name");

        assertThat(patient).isNull();
    }

    @Test
    public void update() {

        when(repository.update(any())).thenReturn(1);

        boolean wasUpdated = patientService.update(new Patient());

        assertThat(wasUpdated).isTrue();
    }

    @Test
    public void updateNoMatchingPatient() {

        when(repository.update(any())).thenReturn(0);

        boolean wasUpdated = patientService.update(new Patient());

        assertThat(wasUpdated).isFalse();
    }

    @Test
    public void delete() {

        when(repository.deleteById(anyLong())).thenReturn(150);

        boolean wasDeleted = patientService.deleteById(anyLong());

        assertThat(wasDeleted).isTrue();
    }

    @Test
    public void deleteNoMatchingPatient() {

        when(repository.deleteById(anyLong())).thenReturn(0);

        boolean wasDeleted = patientService.deleteById(0);

        assertThat(wasDeleted).isFalse();

    }
}