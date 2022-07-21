package com.ctd.ClinicaOdontologica.dto;

import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Dentist;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Dentist dentist;
    private Patient patient;

    @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime dateTime;
    @JsonIgnore
    public Long getDentistId() {
        return dentist.getId();
    }
    @JsonIgnore
    public Long getPatientId() {
        return patient.getId();
    }

    public AppointmentDTO( Appointment a ){
        this.id = a.getId();
        this.dentist = a.getDentist();
        this.patient = a.getPatient();
        this.dateTime = a.getDateTime();
    }

}
