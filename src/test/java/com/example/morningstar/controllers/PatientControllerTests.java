package com.example.morningstar.controllers;

import com.example.morningstar.controller.PatientController;
import com.example.morningstar.entity.Guardian;
import com.example.morningstar.entity.Patient;
import com.example.morningstar.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatientControllerTests {

@Autowired
private MockMvc mockMvc;

@MockitoBean
    private PatientService patientService;

@Test
    public void PatientControllerTest_InsertIntoDB_ReturnPatient() throws Exception {

    Patient patient = new Patient();
    patient.setFirstName("John");
    patient.setLastName("Doe");
    patient.setMemoryCare(false);

    ObjectMapper objectMapper = new ObjectMapper();
    String patientJson = objectMapper.writeValueAsString(patient);

    when(patientService.savePatient(Mockito.any(Patient.class))).thenReturn(patient);

    mockMvc.perform(post("/v1/morningstar").contentType(MediaType.APPLICATION_JSON).content(patientJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void PatientControllerTest_GetAll_ReturnList() throws JsonProcessingException, Exception  {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setMemoryCare(false);

        List<Patient> savedPatient = new ArrayList<>(Arrays.asList(patient));


        when(patientService.showAll()).thenReturn(savedPatient);
        mockMvc.perform(get("/v1/morningstar")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName" , is("John")));


    }

    @Test
    public void PatientControllerTest_UpdateIntoDB_ReturnUpdatePatient() throws Exception {

        Long id = 1L;
        Long g_id = null;
        Patient patient = Patient.builder().id(id).build();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setMemoryCare(false);

        Guardian guardian = new Guardian();
        guardian.setFirstName("Rome");
        guardian.setLastName("Doe");

        Set<Guardian> SetOfGuardians = new HashSet<>(Arrays.asList(guardian));
        patient.setGuardians(SetOfGuardians);

        ObjectMapper objectMapper = new ObjectMapper();
        String patientAsStringJSon = objectMapper.writeValueAsString(patient);

        when(patientService.updatePatient(Mockito.any(Patient.class), Mockito.eq(id), Mockito.eq(g_id))).thenReturn(patient);

        mockMvc.perform(put("/v1/morningstar/update-patient/{id}/update-guardian", id).contentType(MediaType.APPLICATION_JSON).param("g_id",(g_id !=null ? g_id.toString() : null)).content(patientAsStringJSon))
                .andExpect(status().isOk()).andExpect(jsonPath("$.firstName",is("John")));

    }

    @Test
    public void PatientControllerTest_DeletePatientByID_ReturnString() throws Exception {

        Long id = 1L;
        Patient patient = Patient.builder().id(id).build();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setMemoryCare(false);

        when(patientService.deletePatientById(id)).thenReturn("Successfully deleted of " + id);

        mockMvc.perform(delete("/v1/morningstar/delete/{id}", id)).andExpect(status().isOk());

    }


}
