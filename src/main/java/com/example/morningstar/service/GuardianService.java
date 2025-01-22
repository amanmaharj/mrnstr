package com.example.morningstar.service;

import com.example.morningstar.entity.Guardian;
import com.example.morningstar.repo.GuardianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepo guardianRepo;
    public Guardian saveInDB(Guardian guardian) {
       return guardianRepo.save(guardian);
    }

    public String deleteByID(Long id) {
        Optional<Guardian> existingGuardian = guardianRepo.findById(id);
        if(existingGuardian.isPresent()){
            guardianRepo.deleteById(id);
            return "Successfully deleted";
        }
        return "Cannot find the guardian";

    }

    public List<Guardian> getAll() {
        return guardianRepo.findAll();
    }

    public Object updateGuardian(Guardian guardian, Long id) {
        Optional<Guardian> existingGuardian = guardianRepo.findById(id);

        if(existingGuardian.isPresent()){
            if(guardian.getFirstName() != null){
                existingGuardian.get().setFirstName(guardian.getFirstName());
            }

            if(guardian.getLastName() != null){
                existingGuardian.get().setLastName(guardian.getLastName());
            }

            if(guardian.getAddress() != null) {
                existingGuardian.get().setAddress(guardian.getAddress());
            }

            if(guardian.getPhone() != null){
                existingGuardian.get().setPhone(guardian.getPhone());
            }
            if(guardian.getPatients() != null){
                existingGuardian.get().setPatients(guardian.getPatients());
            }
            return existingGuardian.get();
        }

        return "Cannot be updated";
    }
}
