package com.ctd.ClinicaOdontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "residences")
public class Residence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String street;
    @Column
    private Integer number;
    @Column
    private String location;
    @Column
    private String province;

    @JsonIgnore
    public boolean isInvalid() {
        return ( street.isEmpty() || number == null || location.isEmpty() || province.isEmpty() );
    }
}
