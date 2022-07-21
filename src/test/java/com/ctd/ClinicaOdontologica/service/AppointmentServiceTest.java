package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.dto.AppointmentDTO;
import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Dentist;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    PatientService patientService;
    @Mock
    DentistService  dentistService;
    @Mock
    AppointmentRepository appointmentRepository;
    @InjectMocks
    AppointmentService appointmentService;

    @Test
    void addValidAppointment() throws NotFoundException {
        DentistDTO dentist = mock(DentistDTO.class);
        PatientDTO patient = mock(PatientDTO.class);
        when( dentistService.findById(any(Long.class))).thenReturn(dentist);
        when( patientService.findById(any(Long.class))).thenReturn(patient);

        AppointmentDTO appointment = new AppointmentDTO(null, dentist, patient, LocalDateTime.now());
        Patient p = mock(Patient.class);
        Dentist d = mock(Dentist.class);
        Appointment a = new Appointment(1L, LocalDateTime.now(), d, p);
        when( appointmentRepository.save(any(Appointment.class))).thenReturn(a);
        Assertions.assertDoesNotThrow(() ->  appointmentService.add(appointment));
    }

    @Test
    public void addingInvalidAppointment(){
        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO dentist = mock(DentistDTO.class);
            PatientDTO patient = mock(PatientDTO.class);
            AppointmentDTO failAppointment = new AppointmentDTO(null, dentist, patient, null);
            appointmentService.add(failAppointment);
        });

        Assertions.assertThrows(BadRequestException.class, () ->{
            PatientDTO patient = mock(PatientDTO.class);
            AppointmentDTO failAppointment = new AppointmentDTO(null, null, patient, LocalDateTime.now());
            appointmentService.add(failAppointment);
        });

        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO dentist = mock(DentistDTO.class);
            AppointmentDTO failAppointment = new AppointmentDTO(null, dentist, null, LocalDateTime.now());
            appointmentService.add(failAppointment);
        });
    }

    @Test
    void findById() throws NotFoundException {
        Patient p = mock(Patient.class);
        Dentist d = mock(Dentist.class);
        Appointment a = new Appointment(1L, LocalDateTime.now(), d, p);
        when( appointmentRepository.findById(any(Long.class))).thenReturn(Optional.of(a));

        AppointmentDTO respuesta = assertDoesNotThrow( () -> appointmentService.findById(1L)) ;

        Assertions.assertNotNull(respuesta.getDentist());
        Assertions.assertNotNull(respuesta.getPatient());
        Assertions.assertNotNull(respuesta. getDateTime());
    }

    @Test
    void findAAppointmentThatDoesntExists(){
        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThrows( NotFoundException.class, () -> appointmentService.findById(1L));
    }
    @Test
    void update() throws NotFoundException {
        DentistDTO dentist = mock(DentistDTO.class);
        PatientDTO patient = mock(PatientDTO.class);
        when( dentistService.findById(any(Long.class))).thenReturn(dentist);
        when( patientService.findById(any(Long.class))).thenReturn(patient);
        AppointmentDTO appointment = new AppointmentDTO(1L, dentist, patient, LocalDateTime.now());

        Patient p = mock(Patient.class);
        Dentist d = mock(Dentist.class);
        Appointment a = new Appointment(1L, LocalDateTime.now(), d, p);

        when(appointmentRepository.findById(any(Long.class))).thenReturn(Optional.of(a));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(a);
        Assertions.assertDoesNotThrow( () -> appointmentService.update(appointment));
    }


}