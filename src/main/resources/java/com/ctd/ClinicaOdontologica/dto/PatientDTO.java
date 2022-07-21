package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;
    private Residence home;

    public PatientDTO(Patient p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.dni = p.getDni();
        this.registrationDate = p.getRegistrationDate();
        this.home = p.getHome();
    }

    @JsonIgnore
    public boolean isInvalid() {
        boolean result = (firstName == null || lastName == null || dni == null || registrationDate == null);
        if ( !result)
            result = (dni.isEmpty() || firstName.isEmpty() || lastName.isEmpty());
        return result;
    }

}
