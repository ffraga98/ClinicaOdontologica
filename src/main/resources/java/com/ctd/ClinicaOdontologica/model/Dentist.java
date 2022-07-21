package com.ctd.ClinicaOdontologica.model;

import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dentists")
@NoArgsConstructor
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Integer registration;
    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    public Dentist(Long id, String firstName, String lastName, Integer registration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
        this.appointments = new HashSet<>();
    }
    public Dentist( DentistDTO d) {
        this.id = d.getId();
        this.firstName = d.getFirstName();
        this.lastName = d.getLastName();
        this.registration = d.getRegistration();
    }
}
