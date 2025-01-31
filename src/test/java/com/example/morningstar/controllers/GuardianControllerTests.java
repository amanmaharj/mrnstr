package com.example.morningstar.controllers;

import com.example.morningstar.controller.GuardianController;
import com.example.morningstar.entity.Guardian;
import com.example.morningstar.entity.Patient;
import com.example.morningstar.service.GuardianService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = GuardianController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)


public class GuardianControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GuardianService guardianService ;
    @Test
    public void GuardianController_InsertIntoDB_ReturnGuardian() throws Exception{
        Guardian guardian  = Guardian.builder().firstName("John").lastName("Doe").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(guardian);

        when(guardianService.saveInDB(any(Guardian.class))).thenReturn(guardian);
        mockMvc.perform(post("/v1/morningstar/guardian").contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("John")));


    }

    @Test
    public void GuardianController_GetAll_ReturnListGuardianx() throws Exception{
        Guardian guardian  = Guardian.builder().firstName("John").lastName("Doe").build();
        List guardianList = new ArrayList<>(Arrays.asList(guardian));

        when(guardianService.getAll()).thenReturn(guardianList);

        mockMvc.perform(get("/v1/morningstar/guardian")).andExpect(status().isOk()).andExpect(jsonPath("$.[0].firstName",is("John")));
    }

    @Test
    public void testUpdateGuardian_ReturnsUpdatedGuardianDetails() throws Exception {
        Long g_id = 1L;
        Long id = null; // Optional path variable

        Guardian guardian = Guardian.builder().g_id(g_id).build();
        guardian.setFirstName("John");
        guardian.setLastName("Doe");

        Patient patient = new Patient();
        patient.setFirstName("harry");
        patient.setLastName("Style");

        Set<Patient> setOfPatients = new HashSet<>(Arrays.asList(patient));
        guardian.setPatients(setOfPatients);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(guardian);

        when(guardianService.updateGuardian(Mockito.any(Guardian.class), Mockito.eq(g_id), Mockito.eq(id))).thenReturn(guardian);
        mockMvc.perform(put("/v1/morningstar/guardian/{g_id}/update-parent", g_id)
                        .contentType(MediaType.APPLICATION_JSON).param("g_id",(g_id !=null ? g_id.toString() : null))
                        .content(jsonData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
public void PatientController_delete_ReturnString() throws Exception{
    Guardian guardian = Guardian.builder().g_id(1L).build();
    guardian.setFirstName("John");
    guardian.setLastName("Doe");

    when(guardianService.deleteByID(1L)).thenReturn("Successfully deleted");
    mockMvc.perform(delete("/v1/morningstar/guardian/delete/{id}", 1L)).andExpect(status().isOk());

}

}
