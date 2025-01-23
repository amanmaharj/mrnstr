package com.example.morningstar.controller;


import com.example.morningstar.entity.Patient;
import com.example.morningstar.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/morningstar")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @GetMapping
    public ResponseEntity<List<Patient>> getAll(){
        List<Patient> pat =  patientService.showAll();
        return  ResponseEntity.status(HttpStatus.OK).body(pat);
    }

    @PostMapping
    public ResponseEntity<Patient> insertIntoDB(@RequestBody Patient patient){
        return new ResponseEntity<>(patientService.savePatient(patient),HttpStatus.OK);

    }

    @PutMapping("/update-patient/{id}/update-guardian")
    public ResponseEntity<Patient> updateIntoDB(@RequestBody(required = false) Patient patient,@PathVariable Long id,@RequestParam(required = false) Long g_id) throws Exception {
        return new ResponseEntity<>(patientService.updatePatient(patient, id, g_id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable long id){

        return new ResponseEntity<>(patientService.deletePatientById(id), HttpStatus.OK);
    }
}
