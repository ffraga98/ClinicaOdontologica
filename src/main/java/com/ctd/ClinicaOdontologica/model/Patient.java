package com.ctd.ClinicaOdontologica.model;

import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String DNI;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_id")
    private Residence home;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    @JsonIgnore
    public boolean isInvalid() {
        boolean result = false;

        if (this == null) {
            result = true;
        } else if (firstName == null || lastName == null || DNI == null) {
            result = true;
        } else if (DNI.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            result = true;
        }

        return result;
    }

    public Patient(Long id, String firstName, String lastName, Residence home, String DNI, LocalDate date) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.home = home;
        this.DNI = DNI;
        this.registrationDate = date;
    }

    @JsonIgnore
    public Long getHomeId() {
        return home.getId();
    }
}
