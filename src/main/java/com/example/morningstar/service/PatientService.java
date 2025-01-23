package com.example.morningstar.service;

import com.example.morningstar.entity.Guardian;
import com.example.morningstar.entity.Patient;
import com.example.morningstar.repo.GuardianRepo;
import com.example.morningstar.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private GuardianRepo guardianRepo;
    public List<Patient> showAll(){
        return patientRepo.findAll();
    }

    public Patient savePatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient updatePatient(Patient patient, Long id, Long g_id) throws Exception {
        Patient existingpPatient = patientRepo.findById(id).orElseThrow(()-> new RuntimeException("Users not found"));

        if(patient.getFirstName() != null){
            existingpPatient.setFirstName(patient.getFirstName());
        }

        if(patient.getLastName() != null){
            existingpPatient.setLastName(patient.getLastName());
        }

        if(patient.isMemoryCare() != existingpPatient.isMemoryCare()){
            existingpPatient.setMemoryCare(patient.isMemoryCare());
        }

        if(patient.getGuardians() != null){
            existingpPatient.setGuardians(patient.getGuardians());
        }
        if(patient.getGuardians()==null && g_id != null){

          Guardian existingGuardian = guardianRepo.findById(g_id).orElseThrow(()-> new Exception("no value found"));
            
           /*first finding the set of value of guardians in the existingPatient
            adding the existingGuardian on that set of value of guardians
            then calling set method to set that added value of set of guardians into the exising patient.*/
            
           Set<Guardian> existingSetOfGuardians = existingpPatient.getGuardians();
           existingSetOfGuardians.add(existingGuardian);
           //It won't work only setGuardians as it will take set of guardians so above method is needed.
           existingpPatient.setGuardians(existingSetOfGuardians);
        }
        // TODO: 1/23/25 making sure if the patient is null we handle those too. 

        return patientRepo.save(existingpPatient);

    }

    public String deletePatientById(long id) {
        Optional<Patient> existingPatient = patientRepo.findById(id);

        if(existingPatient.isPresent()){
            patientRepo.delete(existingPatient.get());
            return "Delete Successfully";
        }

        return "Cannot delete, couldn't find the patient in DB";

    }
}
