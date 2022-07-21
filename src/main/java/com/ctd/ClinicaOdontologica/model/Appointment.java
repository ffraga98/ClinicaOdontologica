package com.ctd.ClinicaOdontologica.model;

import com.ctd.ClinicaOdontologica.dto.AppointmentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime dateTime;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "dentist_id")
    private Dentist dentist;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment( AppointmentDTO a ){
        this.id = a.getId();
        this.dentist = new Dentist( a.getDentist() );
        this.patient = new Patient( a.getPatient() );
        this.dateTime = a.getDateTime();
    }
}
