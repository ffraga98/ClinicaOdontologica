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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void addValidPatient(){
        Residence rMock = mock(Residence.class) ;
        PatientDTO patient = new PatientDTO(null, "test", "test", "test", LocalDate.now(),  rMock);
        Patient p = new Patient();
        when(patientRepository.save(any(Patient.class))).thenReturn(p);
        Assertions.assertDoesNotThrow(() -> patientService.add(patient));
    }

    @Test
    public void addingInvalidPatient(){
        Residence rMock = mock(Residence.class) ;

        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"", "test", "test", LocalDate.now(),  rMock);
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"test", "", "test", LocalDate.now(),  rMock);
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"test", "test", "", LocalDate.now(),  rMock);
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"test", null, "test", LocalDate.now(),  rMock);
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"test", "test", null, LocalDate.now(),  rMock);
            patientService.add(failPatient);
        });

        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(null,"test", "test", "test", null,  rMock);
            patientService.add(failPatient);
        });
    }
    @Test
    void findById(){
        Residence rMock = mock(Residence.class);
        PatientDTO p = new PatientDTO(null, "test",  "test",  "test", LocalDate.now(), rMock );
        Patient patient = new Patient( p );
        when(patientRepository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        PatientDTO respuesta = Assertions.assertDoesNotThrow( () -> patientService.findById(10L));

        Assertions.assertEquals(patient.getFirstName(), respuesta.getFirstName());
        Assertions.assertEquals(patient.getLastName(), respuesta.getLastName());
        Assertions.assertEquals(patient.getDni(), respuesta.getDni());
        Assertions.assertEquals(patient.getHome(), respuesta.getHome());
        Assertions.assertEquals(patient.getRegistrationDate(), respuesta.getRegistrationDate());
    }


    @Test
    void findAPacientThatDoesntExists(){
        Residence rMock = mock(Residence.class);
        PatientDTO p = new PatientDTO(10L, null, null,  null, null, null);
        when(patientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

       Assertions.assertThrows( NotFoundException.class, () -> patientService.findById(10L));
    }


    @Test
    void update() {
            Residence rMock = mock(Residence.class);
            PatientDTO dto = new PatientDTO(10L, "test", "test",  "test", LocalDate.now(),rMock);
            Patient p = new Patient();
            when(patientRepository.findById(any(Long.class))).thenReturn(Optional.of(p));
            when(patientRepository.save(any(Patient.class))).thenReturn(p);
            Assertions.assertDoesNotThrow( () -> patientService.update(dto));
    }
}
