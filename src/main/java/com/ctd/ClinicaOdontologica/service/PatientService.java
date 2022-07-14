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
    public void add(PatientDTO patient) throws BadRequestException {
        if (patient.isInvalid()) {
            throw new BadRequestException("Error PatientService: Wrong input values");
        }

        Patient p = DTO2entity(patient);

        try {
            residenceService.findById(p.getHomeId());
        }catch( Exception | NotFoundException e){
            residenceService.add(p.getHome());
        }

        patientRepository.save(p);
        logger.info("Patient with DNI : " + p.getDNI() + "added.");

    }

    @Override
    public void update(PatientDTO patient) throws BadRequestException, NotFoundException {
        if (patient.isInvalid()) {
            throw new BadRequestException("Error PatientService: Wrong input values");
        }

        Optional<Patient> p = patientRepository.findById(patient.getId());
        if (!p.isPresent())
            throw new NotFoundException("Error PatientService : Patient with ID : " + patient.getId() + " doesn't exist.");

        p.get().setFirstName(patient.getFirstName());
        p.get().setLastName(patient.getLastName());
        p.get().setHome(patient.getHome());
        p.get().setDNI(patient.getDNI());

        patientRepository.save(p.get());
        logger.info("Patient with ID : " + p.get().getId() + "updated.");
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
            set.add(entity2DTO(patient));
        }
        return set;
    }

    @Override
    public PatientDTO findById(Long id) throws NotFoundException {
        Optional<Patient> p = patientRepository.findById(id);
        if (!p.isPresent())
            throw new NotFoundException("Error PatientService : Patient with ID : " + id + " doesn't exist.");

        return entity2DTO(p.get());
    }

    public PatientDTO entity2DTO(Patient patient) {
        return new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDNI(), patient.getHome(), patient.getRegistrationDate());
    }

    public Patient DTO2entity(PatientDTO dto) {
        return new Patient(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getHome(), dto.getDNI(), dto.getRegistrationDate());
    }
}
