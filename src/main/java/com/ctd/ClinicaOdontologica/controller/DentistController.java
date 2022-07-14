package com.ctd.ClinicaOdontologica.controller;

import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.service.DentistService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/dentist")
public class DentistController {
    public static Logger logger = Logger.getLogger(DentistController.class);
    @Autowired
    private DentistService dentistService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody DentistDTO dentistDTO) {
        try {
            dentistService.add(dentistDTO);
            logger.info("Dentist added.");
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) { 
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Set<DentistDTO>> findAll() {
        return ResponseEntity.ok(dentistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(dentistService.findById(id));
        } catch (NotFoundException e) { 
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            dentistService.delete(id);
            logger.info("Dentist deleted.");
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody DentistDTO dentistDTO) {
        try {
            dentistService.update(dentistDTO);
            logger.info("Dentist updated.");
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
