package com.mediscreen.patientinfo.repository;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.repository.PatientJdbcRepository.PatientRowMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientJdbcRepositoryTest {

    private PatientRowMapper patientRowMapper = new PatientRowMapper();

    @Mock
    private ResultSet resultSet;

    @Test
    void mapRow() throws SQLException {

        Date date = new Date(1);

        when(resultSet.getLong("Id")).thenReturn(1L);
        when(resultSet.getString("Given_Name")).thenReturn("A given name");
        when(resultSet.getString("Family_Name")).thenReturn("A family name");
        when(resultSet.getString("Sex")).thenReturn("F");
        when(resultSet.getString("Home_Address")).thenReturn("3615 Paris");
        when(resultSet.getString("Phone_Number")).thenReturn("0102030405");
        when(resultSet.getDate("Birth_Date")).thenReturn(date);


        Patient patient = patientRowMapper.mapRow(resultSet, 0);

        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getGivenName()).isEqualTo("A given name");
        assertThat(patient.getFamilyName()).isEqualTo("A family name");
        assertThat(patient.getBirthDate()).isEqualTo(date);
        assertThat(patient.getSex()).isEqualTo("F");
        assertThat(patient.getHomeAddress()).isEqualTo("3615 Paris");
        assertThat(patient.getPhoneNumber()).isEqualTo("0102030405");
    }

}