package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Dentist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DentistDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer registration;

    @JsonIgnore
    public boolean isInvalid() {
        boolean result = (firstName == null || lastName == null || registration == null);
        if( !result )
            result = (firstName.isEmpty() || lastName.isEmpty());
        return result;
    }

    public DentistDTO( Dentist d) {
        this.id = d.getId();
        this.firstName = d.getFirstName();
        this.lastName = d.getLastName();
        this.registration = d.getRegistration();
    }
}
