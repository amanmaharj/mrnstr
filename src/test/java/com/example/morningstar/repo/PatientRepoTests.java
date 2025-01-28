package com.example.morningstar.repo;

import com.example.morningstar.entity.Patient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatientRepoTests {
    @Autowired
    private PatientRepo patientRepo;

    @Test
    public void PatientRepository_Save_ReturnSavedPatient(){
        Patient patient = Patient.builder().firstName("Johnny").lastName("Sin").memoryCare(false).build();

        Patient savedPatient = patientRepo.save(patient);

        Assertions.assertThat(savedPatient).isNotNull();
        Assertions.assertThat(savedPatient.getId()).isGreaterThan(0);

    }

    @Test
    public void PatientRepository_findAll_ReturnListPatient(){
        Patient patient = Patient.builder().firstName("Johnny").lastName("Sin").memoryCare(false).build();

        Patient patient1 = Patient.builder().firstName("Johnny1").lastName("Sin1").memoryCare(false).build();

        patientRepo.save(patient1);
        patientRepo.save(patient);

        List<Patient> listOfPatient = patientRepo.findAll();

        Assertions.assertThat(listOfPatient).isNotNull();
        Assertions.assertThat(listOfPatient.size()).isEqualTo(2);


    }

    @Test
    public void PatientRepostiory_findById_ReturnPatient(){
        Patient patient = Patient.builder().firstName("Johnny").lastName("Sin").build();
        Patient savedPatient = patientRepo.save(patient);

       Patient patient1= patientRepo.findById(savedPatient.getId()).get() ;

       Assertions.assertThat(patient1).isNotNull();
       Assertions.assertThat(patient1.getId()).isGreaterThan(0);
    }

    @Test
    public void PatientRepository_deletePatient_ReturnIsEmpty(){
        Patient patient = Patient.builder().firstName("Johnny").lastName("Sin").memoryCare(true).build();
        patientRepo.save(patient);

       Patient existingPatient = patientRepo.findById(patient.getId()).get();

       patientRepo.delete(existingPatient);
       Optional<Patient> returnPatient = patientRepo.findById(patient.getId());

       Assertions.assertThat(returnPatient).isEmpty();

    }

}
