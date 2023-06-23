package com.mediscreen.analysis.repository;

import com.mediscreen.analysis.model.DrNote;
import com.mediscreen.analysis.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;

@Repository
public class PatientRepository {

    private final PatientApi patientApi;

    private final DoctorApi doctorApi;

    public PatientRepository(@Value("${host.patient-ms}") String patientMsHost,
                             @Value("${host.dr-note-ms}") String drNoteMsHost) {

        patientApi = new Retrofit.Builder()
                .baseUrl(patientMsHost)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(PatientApi.class);

        // DONE
        doctorApi = new Retrofit.Builder()
                .baseUrl(drNoteMsHost)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(DoctorApi.class);
    }

    public List<Patient> findAll() {
        try {
            return patientApi.getAll().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Patient getPatientData(Long patientId) {
        //call micro-service patient for a search by patientId
        try {
            return patientApi.get(patientId).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Patient getPatientData(String familyName) {
        //call micro-service patient for a search by Family Name
        try {
            return patientApi.getByFamilyName(familyName).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<DrNote> getDrNotesForPatient(Long patientId) {
        try {
            return doctorApi.getNotes(patientId).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface PatientApi {

        @GET("patients")
        Call<List<Patient>> getAll();

        @GET("/patients/{id}")
        Call<Patient> get(@Path("id") Long patientId);

        @GET("/patient/getByFamilyName")
        Call<Patient> getByFamilyName(@Query("familyName") String familyName);

    }

    public interface DoctorApi {

        @GET("/patHistory/findByPatientId")
        Call<List<DrNote>> getNotes(@Query("patientId") Long patientId);
    }

}
