package com.example.morningstar;


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

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updateIntoDB(@RequestBody Patient patient,@PathVariable Long id) {
        return new ResponseEntity<>(patientService.updatePatient(patient, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable long id){

        return new ResponseEntity<>(patientService.deletePatientById(id), HttpStatus.OK);
    }
}
