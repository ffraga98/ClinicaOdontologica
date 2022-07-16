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
    void addValidPatient(){
        Residence rMock = mock(Residence.class) ;
        PatientDTO patient = new PatientDTO(10L,"test", "test", "test",  rMock, LocalDate.now());
        Assertions.assertDoesNotThrow(() -> patientService.add(patient));
    }

    @Test
    public void addingInvalidPatient(){
        Residence rMock = mock(Residence.class) ;

        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"", "test", "test",  rMock, LocalDate.now());
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"test", "", "test",  rMock, LocalDate.now());
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"test", "test", "",  rMock, LocalDate.now());
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,null, "test", "test",  rMock, LocalDate.now());
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"test", null, "test",  rMock, LocalDate.now());
            patientService.add(failPatient);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"test", "test", null,  rMock, LocalDate.now());
            patientService.add(failPatient);
        });

        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO failPatient = new PatientDTO(10L,"test", "test", "test",  rMock, null);
            patientService.add(failPatient);
        });
    }
    @Test
    void findById(){
        Residence rMock = mock(Residence.class);
        Patient patient = new Patient(10L, "test", "test", rMock, "test", LocalDate.now() );
        when(patientRepository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        PatientDTO respuesta = Assertions.assertDoesNotThrow( () -> patientService.findById(10L));

        Assertions.assertEquals(patient.getFirstName(), respuesta.getFirstName());
        Assertions.assertEquals(patient.getLastName(), respuesta.getLastName());
        Assertions.assertEquals(patient.getDNI(), respuesta.getDNI());
        Assertions.assertEquals(patient.getHome(), respuesta.getHome());
        Assertions.assertEquals(patient.getRegistrationDate(), respuesta.getRegistrationDate());
    }


    @Test
    void findAll(){

    }


    @Test
    void update() {
        Assertions.assertTrue(false);
    }
}
