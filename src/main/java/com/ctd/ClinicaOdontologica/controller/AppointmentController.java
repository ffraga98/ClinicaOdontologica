package com.ctd.ClinicaOdontologica.controller;

import com.ctd.ClinicaOdontologica.dto.AppointmentDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.service.AppointmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    public static Logger logger = Logger.getLogger(AppointmentController.class);
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            appointmentService.add(appointmentDTO);
            logger.info("Adding new appointment");
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Set<AppointmentDTO>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(appointmentService.findById(id));
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            appointmentService.delete(id);

            logger.info("Appointment deleted.");
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            appointmentService.update(appointmentDTO);
            logger.info("Appointment updated.");
            return ResponseEntity.ok().build();
        } catch (NotFoundException | BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}