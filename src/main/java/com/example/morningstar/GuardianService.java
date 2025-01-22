package com.example.morningstar;

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
            if(guardian.firstName != null){
                existingGuardian.get().setFirstName(guardian.getFirstName());
            }

            if(guardian.lastName != null){
                existingGuardian.get().setLastName(guardian.getLastName());
            }

            if(guardian.address != null) {
                existingGuardian.get().setAddress(guardian.getAddress());
            }

            if(guardian.phone != null){
                existingGuardian.get().setPhone(guardian.getPhone());
            }
            return existingGuardian.get();
        }

        return "Cannot be updated";
    }
}
