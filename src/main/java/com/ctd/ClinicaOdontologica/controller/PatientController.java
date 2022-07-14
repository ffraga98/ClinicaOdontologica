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
    public ResponseEntity<Void> add(@RequestBody PatientDTO patientDTO) {
        try {
            patientService.add(patientDTO);
            logger.info("Patient added.");
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            patientService.delete(id);
            logger.info("Patient deleted.");

            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PatientDTO patientDTO) {
        try {
            patientService.update(patientDTO);
            logger.info("Patient updated.");
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}