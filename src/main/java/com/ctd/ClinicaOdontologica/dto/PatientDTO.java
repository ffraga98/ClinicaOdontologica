package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String DNI;
    private Residence home;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;


    public PatientDTO(Long id, String firstName, String lastName, String DNI, Residence home, LocalDate date) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DNI = DNI;
        this.home = home;
        this.registrationDate = date;
    }

    @JsonIgnore
    public boolean isInvalid() {
        return (this == null || firstName.isEmpty() || lastName.isEmpty() || DNI.isEmpty());
    }

}
