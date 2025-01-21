package com.example.morningstar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;
    public List<Patient> showAll(){
        return patientRepo.findAll();
    }

    public Patient savePatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient updatePatient(Patient patient, Long id) {
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
