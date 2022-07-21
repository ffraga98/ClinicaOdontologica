package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Dentist;
import com.ctd.ClinicaOdontologica.repository.DentistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DentistServiceTest {
    @Mock
    DentistRepository dentistRepository;
    @InjectMocks
    DentistService dentistService;

    @Test
    void addValidDentist(){
        DentistDTO dentist = new DentistDTO(null, "test", "test", 123);
        Dentist p = new Dentist();
        when(dentistRepository.save(any(Dentist.class))).thenReturn(p);
        Assertions.assertDoesNotThrow(() -> dentistService.add(dentist));
    }

    @Test
    public void addingInvalidDentist(){
        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO failDentist = new DentistDTO(null,"", "test", 123 );
            dentistService.add(failDentist);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO failDentist = new DentistDTO(null,"test", "", 123 );
            dentistService.add(failDentist);
        });

        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO failDentist = new DentistDTO(null,"test", null, 100);
            dentistService.add(failDentist);
        });
        Assertions.assertThrows(BadRequestException.class, () ->{
            DentistDTO failDentist = new DentistDTO(null,"test", "test", null );
            dentistService.add(failDentist);
        });
    }
    @Test
    void findById(){
        DentistDTO p = new DentistDTO(null, "test",  "test",  123 );
        Dentist dentist = new Dentist( p );
        when(dentistRepository.findById(any(Long.class))).thenReturn(Optional.of(dentist));

        DentistDTO respuesta = Assertions.assertDoesNotThrow( () -> dentistService.findById(10L));

        Assertions.assertEquals(dentist.getFirstName(), respuesta.getFirstName());
        Assertions.assertEquals(dentist.getLastName(), respuesta.getLastName());
        Assertions.assertEquals(dentist. getRegistration(), respuesta. getRegistration());
    }


    @Test
    void findADentistThatDoesntExists(){
        when(dentistRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThrows( NotFoundException.class, () -> dentistService.findById(10L));
    }
    @Test
    void update() {
        DentistDTO dto = new DentistDTO(10L, "test", "test",  123);
        Dentist p = new Dentist();
        when(dentistRepository.findById(any(Long.class))).thenReturn(Optional.of(p));
        when(dentistRepository.save(any(Dentist.class))).thenReturn(p);
        Assertions.assertDoesNotThrow( () -> dentistService.update(dto));
    }
}