package com.example.morningstar.service;

import com.example.morningstar.entity.Guardian;
import com.example.morningstar.entity.Patient;
import com.example.morningstar.repo.GuardianRepo;
import com.example.morningstar.repo.PatientRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepo patientRepo;

    @Mock
    private GuardianRepo guardianRepo;
    @InjectMocks
    private PatientService patientService;

    @Test
    public void PatientService_Save_ReturnSavedPatientWithNoGuardian(){
        Patient patient = Patient.builder().firstName("Johnny").lastName("Bravo").memoryCare(false).build();
        when(patientRepo.save(any(Patient.class))).thenReturn(patient);

        Patient savedPatient = patientService.savePatient(patient);

        Assertions.assertThat(savedPatient).isNotNull();

    }

    @Test
    public void PatientService_Save_ReturnSavedPatientWithGuardian(){
        Guardian guardian = Guardian.builder().g_id(1L).build();
        Guardian guardian1 = Guardian.builder().g_id(2L).build();

        Set<Guardian> setOfGuardian = new HashSet<>();

        setOfGuardian.add(guardian);
        setOfGuardian.add(guardian1);

        Patient patient = Patient.builder().firstName("Johnny").lastName("bravo").memoryCare(true).guardians(setOfGuardian).build();
        setOfGuardian.forEach(g -> g.setPatients(new HashSet<>(Arrays.asList(patient))));

        when(patientRepo.save(any(Patient.class))).thenReturn(patient);
        Patient savedPatient = patientService.savePatient(patient);


        Assertions.assertThat(savedPatient).isNotNull();
        Assertions.assertThat(savedPatient.getGuardians()).isNotNull();
        Assertions.assertThat(savedPatient.getGuardians().size()).isEqualTo(2);


        savedPatient.getGuardians().forEach(guardian2-> Assertions.assertThat(guardian2.getPatients()).isNotNull());

    }

    @Test
    public void PatientService_UpdatePatient_ReturnPatientWithoutGuardian() throws Exception {
        Patient patient = Patient.builder().firstName("Johnny").lastName("bravo").memoryCare(true).id(1L).build();

        Long id = 1L;
        Long g_id = null;

        when(patientRepo.findById(id)).thenReturn(Optional.ofNullable(patient));
        Optional<Patient> updateInfo= patientRepo.findById(id);
        updateInfo.get().setFirstName("Haku");
        updateInfo.get().setLastName("Black");

        when(patientRepo.save(Mockito.any(Patient.class))).thenReturn(updateInfo.get());

        Patient patient2 = (Patient) patientService.updatePatient(updateInfo.get(), id, g_id);

        Assertions.assertThat(patient2.getFirstName()).isEqualTo("Haku");
        Assertions.assertThat(patient2.getLastName()).isEqualTo("Black");

    }
    @Test
    public void PatientService_UpdatePatient_ReturnPatientWithGuardian() throws Exception {
        Long g_id = 1L;
        Long p_id = 1L;
        Patient patientArg = null;


        Guardian guardian = Guardian.builder().g_id(g_id).build();

        when(guardianRepo.findById(g_id)).thenReturn(Optional.of(guardian));
        Guardian guardianNeedUpdate = guardianRepo.findById(g_id).get();

        Patient patient = Patient.builder().id(1L).build();
        when(patientRepo.findById(p_id)).thenReturn(Optional.of(patient));
        Patient existingPatient = patientRepo.findById(p_id).get();


        Set<Guardian> setOfGuardian = new HashSet<>(Arrays.asList(guardianNeedUpdate)) ;
        setOfGuardian.add(guardianNeedUpdate);

        existingPatient.setGuardians(setOfGuardian);

        when(patientRepo.save(any(Patient.class))).thenReturn(existingPatient);
        Patient patient1 = (Patient) patientService.updatePatient(patientArg, p_id, g_id);

       Assertions.assertThat(patient1.getGuardians()).isNotNull();
       Assertions.assertThat(patient1.getGuardians().size()).isEqualTo(1);
    }

    @Test
    public void PatientService_ShowAll_ReturnListPatient(){
        Patient patient = Patient.builder().firstName("John").lastName("Doe").memoryCare(true).build();
        Patient patient1 = Patient.builder().firstName("Mayer").lastName("Singh").build();

        List<Patient> patientSet = new ArrayList<>(Arrays.asList(patient,patient1));


        when(patientRepo.findAll()).thenReturn(patientSet);
        List<Patient> patientlist = patientService.showAll();

        Assertions.assertThat(patientlist).isNotNull();
        Assertions.assertThat(patientlist.size()).isEqualTo(2);

    }

    @Test
    public void PatientService_DeletePatientById_ReturnObject(){
        Long p_id =1L;
        Patient patient = Patient.builder().firstName("John").lastName("Doe").id(p_id).memoryCare(true).guardians(new HashSet<>()).build();
        when(patientRepo.findById(1L)).thenReturn(Optional.of(patient));
        Optional<Patient> patient1 = patientRepo.findById(p_id);

        Object result = patientService.deletePatientById(p_id);

        Assertions.assertThat(result).isEqualTo("Successfully deleted of " + p_id);
    }

}
