package com.mediscreen.patientinfo.repository;

import com.mediscreen.patientinfo.model.Patient;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PatientJdbcRepository {

    public static final String FIND_ALL_SQL = "select * from Patient order by id asc";
    public static final String FIND_BY_ID_SQL = "select * from Patient where Id=?";

    public static final String FIND_BY_FAMILY_NAME_SQL = "select * from Patient where Family_Name = ?";
    public static final String DELETE_BY_ID_SQL = "delete from Patient where Id=?";
    public static final String INSERT_SQL = "insert into Patient (Given_Name, Family_Name, Birth_Date, Sex, Home_Address, Phone_Number) values(?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_SQL = "update Patient  set Given_Name = ?, Family_Name = ?,  Birth_Date = ?, Sex = ?, Home_Address = ?, Phone_Number = ? where Id = ?";
    private final JdbcTemplate jdbcTemplate;

    public PatientJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient Patient = new Patient();
            Patient.setId(rs.getLong("Id"));
            Patient.setGivenName(rs.getString("Given_Name"));
            Patient.setFamilyName(rs.getString("Family_Name"));
            Patient.setSex(rs.getString("Sex"));
            Patient.setHomeAddress(rs.getString("Home_Address"));
            Patient.setPhoneNumber(rs.getString("Phone_Number"));
            Patient.setBirthDate(rs.getDate("Birth_Date"));

            return Patient;
        }

    }

    public List<Patient> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, new PatientRowMapper());
    }

    public Patient findById(long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, new Object[]{id},
                new BeanPropertyRowMapper<>(Patient.class));
    }

    public Patient findByFamilyName(String familyName) {
        return jdbcTemplate.queryForObject(FIND_BY_FAMILY_NAME_SQL, new Object[]{familyName},
                new BeanPropertyRowMapper<>(Patient.class));
    }

    public int deleteById(long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, new Object[]{id});
    }

    public int insert(Patient patient) {
        return jdbcTemplate.update(INSERT_SQL,
                new Object[]{patient.getGivenName(), patient.getFamilyName(),
                        patient.getBirthDate(), patient.getSex(),
                        patient.getHomeAddress(), patient.getPhoneNumber()
                });
    }

    public int update(Patient patient) {
        return jdbcTemplate.update(UPDATE_SQL,
                new Object[]{patient.getGivenName(), patient.getFamilyName(),
                        patient.getBirthDate(), patient.getSex(),
                        patient.getHomeAddress(), patient.getPhoneNumber(),
                        patient.getId()});
    }
}
