package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.dto.AppointmentDTO;
import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Appointment;
import com.ctd.ClinicaOdontologica.model.Dentist;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.repository.AppointmentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("AppointmentService")
public class AppointmentService implements IService<AppointmentDTO> {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DentistService dentistService;
    public static Logger logger = Logger.getLogger(AppointmentService.class);

    @Override
    public void add(AppointmentDTO appointment) throws NotFoundException {
        PatientDTO p = patientService.findById(appointment.getPatientId());
        DentistDTO d = dentistService.findById(appointment.getDentistId());

        Patient patient = patientService.DTO2entity(p);
        Dentist dentist = dentistService.DTO2entity(d);
        Appointment a = DTO2entity(appointment);

        appointmentRepository.save(a);
        logger.info("New appointment added.\n Dentist: Dr."+ dentist.getLastName() +"\n Patient:" + patient.getLastName() + "\n DateTime: " + a.getDateTime().toString());
    }

    @Override
    public void update(AppointmentDTO appointment) throws BadRequestException, NotFoundException {
        AppointmentDTO dto = this.findById(appointment.getId() );
        Appointment a = DTO2entity(dto);

        //Lo busco
        try{
            PatientDTO p = patientService.findById(appointment.getPatientId());
            DentistDTO d = dentistService.findById(appointment.getDentistId());
            
            a.setDentist(dentistService.DTO2entity(d));
            a.setPatient(patientService.DTO2entity(p));
            a.setDateTime( appointment.getDateTime() );

            logger.info("Appointment updated.\n Dentist: Dr."+ d.getLastName() +"\n Patient:" + p.getLastName() + "\n DateTime: " + a.getDateTime().toString());
            appointmentRepository.save(a);
        }catch(NotFoundException e ){
            throw new BadRequestException("Error AppointmentService : Appoint can't be updated because the dentist or patient entered doesn't exist.");
        }
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        AppointmentDTO dto = this.findById(id);
        Appointment a = DTO2entity(dto);
        logger.info("Appointment deleted.");
        appointmentRepository.delete(a);
    }

    @Override
    public Set<AppointmentDTO> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();

        Set<AppointmentDTO> set = new HashSet<>();
        for (Appointment a : appointments) {
            set.add(entity2DTO(a));
        }
        return set;
    }

    @Override
    public AppointmentDTO findById(Long id) throws NotFoundException {
        Optional<Appointment> a = appointmentRepository.findById(id);
        if (!a.isPresent())
            throw new NotFoundException("Error AppointmentService : Appointment with ID : " + id + " doesn't exist.");

        return entity2DTO(a.get());
    }

    private AppointmentDTO entity2DTO(Appointment a) {
        return new AppointmentDTO(a.getId(), a.getDentist(), a.getPatient(), a.getDateTime());
    }

    public Appointment DTO2entity(AppointmentDTO dto) {
        return new Appointment(dto.getId(), dto.getDateTime(), dto.getDentist(), dto.getPatient());
    }

}
