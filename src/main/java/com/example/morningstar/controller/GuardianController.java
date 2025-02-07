package com.example.morningstar.controller;

import com.example.morningstar.entity.Guardian;
import com.example.morningstar.service.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/morningstar/guardian")
public class GuardianController {
    @Autowired
    private GuardianService guardianService;
    @GetMapping
    public List<Guardian> getAll(){
        return guardianService.getAll();
    }

    @PostMapping
    public ResponseEntity<Guardian> insertIntoDB(@RequestBody Guardian guardian){
        return new ResponseEntity<>(guardianService.saveInDB(guardian), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFromDbByID(@PathVariable Long id) {
        return new ResponseEntity<>(guardianService.deleteByID(id), HttpStatus.OK);
    }
    @GetMapping("/unique-guardian/{g_id}")
    public ResponseEntity<?> getById(@PathVariable long g_id){
        return new ResponseEntity<>(guardianService.getById(g_id), HttpStatus.OK);
    }

    @PutMapping("/{g_id}/update-parent")
    public ResponseEntity<Object> updateGuardian(@RequestBody(required = false) Guardian guardian,@PathVariable Long g_id, @RequestParam(name = "id", required = false) Long id){
        return new ResponseEntity<>(guardianService.updateGuardian(guardian, g_id, id) , HttpStatus.OK);
    }

}
