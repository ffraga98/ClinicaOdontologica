package com.ctd.ClinicaOdontologica.service;


import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.ctd.ClinicaOdontologica.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;

    @Mock
    ResidenceService residenceService;

    @InjectMocks
    PatientService patientService;

    @Test
    void addPatient() throws BadRequestException {
        Residence residence = new Residence();
        PatientDTO patient = new PatientDTO(10L,"fer", "fraga", "102369",  residence, LocalDate.now());
        Assertions.assertDoesNotThrow(() -> patientService.add(patient));

        PatientDTO failPatient = new PatientDTO(10L,"", "fraga", "102369",  residence, LocalDate.now());
        Assertions.assertThrows(BadRequestException.class, () -> patientService.add(failPatient));


    }

    @Test
    void findById() throws NotFoundException {
        Residence residence = new Residence();
        Patient patient = new Patient(10L, "salva", "diaz", residence, "1234", LocalDate.now() );
        when(patientRepository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        PatientDTO respuesta = Assertions.assertDoesNotThrow( () -> patientService.findById(10L));

        Assertions.assertEquals(patient.getFirstName(), respuesta.getFirstName());
        Assertions.assertEquals(patient.getLastName(), respuesta.getLastName());
        Assertions.assertEquals(patient.getDNI(), respuesta.getDNI());
    }
}
