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
public class GuardianService {
    @Autowired
    private GuardianRepo guardianRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Transactional
    public Guardian saveInDB(Guardian guardian) {
        /*if(guardian.getPatients()!=null){
            guardian.getPatients().forEach((patient)->{
                if(patient.getId() == null){
                    patient.getGuardians().add(guardian);
                    patientRepo.save(patient);//Save new patient if not already persist
                }
            });
        }*/
       return guardianRepo.save(guardian);
    }


    @Transactional

    public Object deleteByID(Long id) {
        try{
            Guardian existingGuardian = guardianRepo.findById(id).orElseThrow(()->new PatientNotFound("Guardian could not be found in DB"));
            //Explicitly remove the association before deleting through the repository

                for(Patient patient : existingGuardian.getPatients()){
                    patient.getGuardians().remove(existingGuardian); //remove the guardian from each patient's guardian list
                }

                existingGuardian.getPatients().clear();
                guardianRepo.delete(existingGuardian);

            return "Successfully deleted";
        }catch(PatientNotFound exception){
            String msg = "The guardian with " + id + " could not be found in our DB";
            ErrorMSg errorMSg = new ErrorMSg(msg, LocalDateTime.now(), exception.getMessage());
            return errorMSg;
        }


    }

    public List<Guardian> getAll() {
        return guardianRepo.findAll();
    }

    @Transactional
    public Object updateGuardian(Guardian guardian, Long g_id, Long id) {

        try{
            Guardian existingGuardian = guardianRepo.findById(g_id).orElseThrow(()->new PatientNotFound("Guardian cannot be found"));
            if(guardian!=null && id==null){
                if(guardian.getFirstName() != null){
                    existingGuardian.setFirstName(guardian.getFirstName());
                }

                if(guardian.getLastName() != null){
                    existingGuardian.setLastName(guardian.getLastName());
                }

                if(guardian.getAddress() != null) {
                    existingGuardian.setAddress(guardian.getAddress());
                }

                if(guardian.getPhone() != null){
                    existingGuardian.setPhone(guardian.getPhone());
                }
                if(guardian.getPatients() != null){
                    existingGuardian.setPatients(guardian.getPatients());
                }

            }else if((guardian==null || guardian.getPatients()==null)&& id!=null){

                try{
                    Patient existingPatient = patientRepo.findById(id).orElseThrow(()->new PatientNotFound("patient cannot be found"));
                    Set<Patient> existingPatintSet = existingGuardian.getPatients();
                    existingPatintSet.add(existingPatient);

                    existingGuardian.setPatients(existingPatintSet);
                }catch(PatientNotFound exception){
                    String msg = "The Patient with id : " + id +" cannot be found";
                    ErrorMSg errorMSg = new ErrorMSg(msg, LocalDateTime.now(), exception.getMessage());
                    return errorMSg;
                }
            }
            return guardianRepo.save(existingGuardian);
        }catch(PatientNotFound exception){
            String mesg = "The guardian with id " +  g_id + " Cannot be found";
            ErrorMSg errorMSg = new ErrorMSg(mesg , LocalDateTime.now(), exception.getMessage());
            return errorMSg;
        }
    }
}
