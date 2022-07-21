package com.ctd.ClinicaOdontologica.model;

import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    private String dni;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_id")
    private Residence home;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    public Patient( PatientDTO p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.home = p.getHome();
        this.dni = p.getDni();
        this.registrationDate = p.getRegistrationDate();
    }
    @JsonIgnore
    public Long getHomeId() {
        return home.getId();
    }
}
