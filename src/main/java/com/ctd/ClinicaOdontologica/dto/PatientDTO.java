package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String DNI;
    private Residence home;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    public PatientDTO( Patient p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.home = p.getHome();
        this.DNI = p.getDNI();
        this.registrationDate = p.getRegistrationDate();
    }

    @JsonIgnore
    public boolean isInvalid() {
        boolean result = (firstName == null || lastName == null || DNI == null || registrationDate == null);
        if ( !result)
            result = (DNI.isEmpty() || firstName.isEmpty() || lastName.isEmpty());
        return result;
    }

}
