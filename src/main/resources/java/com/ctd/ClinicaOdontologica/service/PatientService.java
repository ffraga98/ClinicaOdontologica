package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.dto.PatientDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Patient;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.ctd.ClinicaOdontologica.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service("PatientService")
public class PatientService implements IService<PatientDTO> {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ResidenceService residenceService;

    public static Logger logger = Logger.getLogger(PatientService.class);

    @Override
    public PatientDTO add(PatientDTO patient) throws BadRequestException {
        if (patient.isInvalid()) {
            throw new BadRequestException("Error PatientService: Wrong input values");
        }

        Patient p = new Patient(patient);
        if( p.getHomeId() == null ){
            residenceService.add(p.getHome());
        }else{
            Residence r = null;
            try {
                Long id = p.getHomeId();
                r = residenceService.findById(p.getHomeId());
                p.setHome(r);
            } catch (NotFoundException e) {
                throw new BadRequestException("Error PatientService: Residence entered doesn't exist.");
            }
        }
        logger.info("Patient with dni : " + p.getDni() + "added.");
        Patient newPatient = patientRepository.save(p);
        return new PatientDTO( newPatient );

    }

    @Override
    public PatientDTO update(PatientDTO patient) throws BadRequestException, NotFoundException {
        if (patient.isInvalid()) {
            throw new BadRequestException("Error PatientService: Wrong input values");
        }

        Optional<Patient> p = patientRepository.findById(patient.getId());
        if (!p.isPresent())
            throw new NotFoundException("Error PatientService : Patient with ID : " + patient.getId() + " doesn't exist.");

        Residence r = null;
        if( patient.getHome().getId() == null ){
            r = residenceService.add(patient.getHome());
        }else{
            try {
                r = residenceService.findById(patient.getHome().getId());
            } catch (NotFoundException e) {
                throw new BadRequestException("Error PatientService: Residence entered doesn't exist.");
            }
        }

        p.get().setFirstName(patient.getFirstName());
        p.get().setLastName(patient.getLastName());
        p.get().setDni(patient.getDni());
        p.get().setHome(r);

        logger.info("Patient with ID : " + p.get().getId() + "updated.");
        return new PatientDTO( patientRepository.save(p.get()) );
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        PatientDTO p = this.findById(id);
        logger.info("Patient with ID:" + id + "deleted.");
        patientRepository.deleteById(id);
    }

    @Override
    public Set<PatientDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();

        Set<PatientDTO> set = new HashSet<>();

        for (Patient patient : patients) {
            set.add(new PatientDTO(patient));
        }
        return set;
    }

    @Override
    public PatientDTO findById(Long id) throws NotFoundException {
        Optional<Patient> p = patientRepository.findById(id);
        if (!p.isPresent())
            throw new NotFoundException("Error PatientService : Patient with ID : " + id + " doesn't exist.");

        return new PatientDTO(p.get());
    }
}
