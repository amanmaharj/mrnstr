package com.example.morningstar.repo;

import com.example.morningstar.entity.Guardian;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GuardianRepoTests {
    private GuardianRepo guardianRepo;
    @Autowired
    GuardianRepoTests(GuardianRepo guardianRepo){
        this.guardianRepo = guardianRepo;
    }
    @Test
    public void GuardianRepo_Save_ReturnSavedGuardian(){
        Guardian guardian = Guardian.builder().firstName("Johnny").lastName("Sin").address("Teku").phone("4535353535").build();
        Guardian guardianSave = guardianRepo.save(guardian);

        Assertions.assertThat(guardianSave).isNotNull();
        Assertions.assertThat(guardianSave.getG_id()).isGreaterThan(0);
    }
    @Test
    public void GuardianRepo_FindAll_ReturnListGuardian(){
        Guardian guardian = Guardian.builder().firstName("Johnny").lastName("Sin").address("teku").phone("3424242424").build();
        Guardian guardian1 = Guardian.builder().firstName("Ronnie").lastName("Coleman").address("Delaware").phone("22435353535").build();

        guardianRepo.save(guardian);
        guardianRepo.save(guardian1);

        List<Guardian> guardianList = guardianRepo.findAll();

        Assertions.assertThat(guardianList).isNotNull();
        Assertions.assertThat(guardianList.size()).isEqualTo(2);
    }
    @Test
    public void GuardianRepo_FindById_ReturnListGuardian(){
        Guardian guardian = Guardian.builder().firstName("Johnny").lastName("Bravo").address("Delaware").phone("23232323").build();
        guardianRepo.save(guardian);

        Guardian guardian1 = guardianRepo.findById(guardian.getG_id()).get();

        Assertions.assertThat(guardian1).isNotNull();
        Assertions.assertThat(guardian1.getG_id()).isGreaterThan(0);

    }
    @Test
    public void GuardianRepo_DeleteById_ReturnIsEmpty(){
        Guardian guardian = Guardian.builder().firstName("Johnny").lastName("Bravo").address("Delaware").phone("23232323").build();
        guardianRepo.save(guardian);

        Guardian guardian1 = guardianRepo.findById(guardian.getG_id()).get();
        guardianRepo.delete(guardian1);

        List<Guardian> guardianList= guardianRepo.findAll();

        Assertions.assertThat(guardianList).isEmpty();

    }


}
