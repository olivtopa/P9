package com.mediscreen.analysis.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

class PatientTest {

    @Test
    void getAge() {
        Patient patient = new Patient();

        patient.setBirthDate(new Date(100, 1, 1));

        Assertions.assertThat(patient.getAge()).isEqualTo(23);
    }
}