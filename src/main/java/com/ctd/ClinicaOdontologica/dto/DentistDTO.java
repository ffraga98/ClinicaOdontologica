package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DentistDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer registration;

    @JsonIgnore
    public boolean isInvalid() {
        return (this == null || firstName.isEmpty() || lastName.isEmpty() || registration == null);
    }

    public DentistDTO(Long id, String firstName, String lastName, Integer registration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
    }
}
