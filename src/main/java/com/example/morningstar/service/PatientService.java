package com.example.morningstar.service;

import com.example.morningstar.entity.Guardian;
import com.example.morningstar.entity.Patient;
import com.example.morningstar.exception.PatientNotFound;
import com.example.morningstar.exceptionEntity.ErrorMSg;
import com.example.morningstar.repo.GuardianRepo;
import com.example.morningstar.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public Patient savePatient(Patient patient) {
        if(patient.getGuardians()!=null){
            patient.getGuardians().forEach(guardian -> guardian.getPatients().add(patient));
        }
        return patientRepo.save(patient);
    }

    public Object updatePatient(Patient patient, Long id, Long g_id) throws Exception {



        try{
         Patient existingpPatient = patientRepo.findById(id).orElseThrow(()-> new PatientNotFound("Patient not found"));
            if(patient != null){
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
            }
            if((patient == null || patient.getGuardians()==null) && g_id != null){

            /*first finding the set of value of guardians in the existingPatient
            adding the existingGuardian on that set of value of guardians
            then calling set method to set that added value of set of guardians into the exising patient.*/

                try{
                    Guardian existingGuardian = guardianRepo.findById(g_id).orElseThrow(()-> new PatientNotFound("Guardian not found"));

                    Set<Guardian> existingSetOfGuardians = existingpPatient.getGuardians();
                    existingSetOfGuardians.add(existingGuardian);

                    existingpPatient.setGuardians(existingSetOfGuardians);
                }catch(PatientNotFound e){
                    ErrorMSg errorMSg = new ErrorMSg("Finding The Guardian was not possible", LocalDateTime.now(), e.getMessage());
                    return errorMSg;
                }
            }
            return patientRepo.save(existingpPatient);
        }catch(PatientNotFound e){
            ErrorMSg error = new ErrorMSg("The Patient could Not be Found", LocalDateTime.now(), e.getMessage());
            return error;
        }

    }
    @Transactional

    public Object deletePatientById(long id) {
        try {
            Patient existingPatient = patientRepo.findById(id).orElseThrow(() -> new PatientNotFound("Could'nt find the patient"));

            for(Guardian guardian: existingPatient.getGuardians()){
                guardian.getPatients().remove(existingPatient);
            }

            existingPatient.getGuardians().clear();
            patientRepo.delete(existingPatient);

            String successful = "Successfully deleted of " + id;
            return successful;
        }catch(PatientNotFound exception){
            String message = "Cannot delete because existing id " + id + " is not in the DB.";
            ErrorMSg errorMSg = new ErrorMSg(message,LocalDateTime.now(), exception.getMessage());
            return errorMSg;
        }

    }

    public Object getPatientById(Long id) {
        try{
            Patient patient = patientRepo.findById(id).orElseThrow(()-> new PatientNotFound("couldn't find the patient"));
            return patient;
        }catch(PatientNotFound exception){
            String message = "Cannot find the patient of id: " + id ;
            ErrorMSg errorMSg = new ErrorMSg(message, LocalDateTime.now(), exception.getMessage());
            return errorMSg;
        }
    }
}
