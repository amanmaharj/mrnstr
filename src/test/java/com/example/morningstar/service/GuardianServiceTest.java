package com.example.morningstar.service;


import com.example.morningstar.entity.Guardian;
import com.example.morningstar.repo.GuardianRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GuardianServiceTest {
    @Mock
    private GuardianRepo guardianrepo;

    @InjectMocks
    private GuardianService guardianService;

    @Test
    public void GuardianService_SaveInDB_ReturnsavedGuardian(){
        Guardian guardian = Guardian.builder().firstName("JOhn").g_id(1L) .lastName("Doe").build();

        when(guardianrepo.save(Mockito.any(Guardian.class))).thenReturn(guardian);
        Guardian guardianSaved = guardianService.saveInDB(guardian);

        Assertions.assertThat(guardianSaved).isNotNull();
        Assertions.assertThat(guardianSaved.getG_id()).isGreaterThan(0);
    }

    @Test
    public void GuardianService_GetAll_ReturnListGuardian(){
        Guardian guardian1 = Guardian.builder().firstName("john").g_id(1L).lastName("Doe").build();
        Guardian guardian2 = Guardian.builder().firstName("john").g_id(2L).lastName("Doe").build();

        List<Guardian> guardianList = Arrays.asList(guardian1, guardian2);

        when(guardianrepo.findAll()).thenReturn(guardianList);
        List<Guardian>SavedGuardian = guardianService.getAll();

        Assertions.assertThat(SavedGuardian).isNotNull();
        Assertions.assertThat(SavedGuardian.size()).isEqualTo(2);

    }

    @Test
    public void GuardianService_deleteById_ReturnObject(){
        Long g_id = 1L;
        Guardian guardian1 = Guardian.builder().firstName("john").g_id(g_id).build();
        guardian1.setPatients(new HashSet<>());

        when(guardianrepo.findById(g_id)).thenReturn(Optional.of(guardian1) );
        Object result = guardianService.deleteByID(g_id);

        Assertions.assertThat(result).isEqualTo("Successfully deleted");
    }
    // TODO: 1/29/25 : need to build other edge cases for delete part. Will be doing that in next development phase

    @Test
    public void GuardianService_updateGuardian_ReturnObject(){
        Long g_id = 1L;
        Guardian guardian = Guardian.builder().firstName("Simi").lastName("mas").g_id(g_id).build();
        when(guardianrepo.findById(g_id)).thenReturn(Optional.of(guardian));
        Guardian existingGuardian = guardianrepo.findById(g_id).get();
        guardian.setFirstName("Rimi");
        guardian.setLastName("Shr");

        Guardian response = (Guardian)guardianService.updateGuardian(existingGuardian, g_id, null);

        Assertions.assertThat(response.getFirstName()).isEqualTo("Rimi");
        Assertions.assertThat(response.getLastName()).isEqualTo("Shr");
        
        //// TODO: 1/29/25 : need to build more case where guardian is null and g_id and patient is is there in next development 

    }
}
