package com.ctd.ClinicaOdontologica.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ResidenceDTO {
    private Long id;
    private String street;
    private Integer number;
    private String location;
    private String province;

    public ResidenceDTO(Long id) {
        this.id = id;
    }
}
