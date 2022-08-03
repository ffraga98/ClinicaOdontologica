package com.ctd.ClinicaOdontologica.controller;

import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.service.PatientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/patient")
public class PatientController {
    public static Logger logger = Logger.getLogger(PatientController.class);
    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> add(@RequestBody PatientDTO patientDTO) {
        try {
            return ResponseEntity.ok( patientService.add(patientDTO) );
        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Set<PatientDTO>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(patientService.findById(id));
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            patientService.delete(id);
            logger.info("Patient deleted.");
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<PatientDTO> update(@RequestBody PatientDTO patientDTO) {
        try {
            return ResponseEntity.ok( patientService.update(patientDTO) );
        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}