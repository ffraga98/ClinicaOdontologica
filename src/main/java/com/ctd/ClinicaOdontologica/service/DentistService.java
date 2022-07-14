package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.dto.DentistDTO;
import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Dentist;
import com.ctd.ClinicaOdontologica.repository.DentistRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("DentistService")
public class DentistService implements IService<DentistDTO> {
    @Autowired
    private DentistRepository dentistRepository;

    public static Logger logger = Logger.getLogger(DentistService.class);

    @Override
    public void add(DentistDTO dentist) throws BadRequestException {
        if (dentist.isInvalid()) {
            throw new BadRequestException("Error DentistService: Wrong input values");
        }

        Dentist d = DTO2entity(dentist);
        dentistRepository.save(d);

        logger.info("Dentist with Registration : " + d.getRegistration() + " added.");

    }

    @Override
    public void update(DentistDTO dentist) throws BadRequestException, NotFoundException {
        if (dentist.isInvalid()) {
            throw new BadRequestException("Error DentistService: Wrong input values");
        }

        Optional<Dentist> d = dentistRepository.findById(dentist.getId());
        if (!d.isPresent())
            throw new NotFoundException("Error DentistService : Dentist with ID : " + dentist.getId() + " doesn't exist.");

        d.get().setFirstName(dentist.getFirstName());
        d.get().setLastName(dentist.getLastName());
        d.get().setRegistration(dentist.getRegistration());

        dentistRepository.save(d.get());
        logger.info("Dentist with Registration : " + d.get().getRegistration() + "updated.");

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        //TODO: Agregar logger.
        DentistDTO d = this.findById(id);
        dentistRepository.deleteById(id);
        logger.info("Dentist with ID:" + id + "deleted.");
    }

    @Override
    public Set<DentistDTO> findAll() {
        List<Dentist> dentists = dentistRepository.findAll();
        Set<DentistDTO> set = new HashSet<>();
        for (Dentist dentist : dentists) {
            set.add(entity2DTO(dentist));
        }

        logger.info("All dentists were listed.");
        return set;
    }

    @Override
    public DentistDTO findById(Long id) throws NotFoundException {
        Optional<Dentist> d = dentistRepository.findById(id);
        if (!d.isPresent())
            throw new NotFoundException("Error DentistService : Dentist with ID : " + id + " doesn't exist.");

        return entity2DTO(d.get());
    }

    public DentistDTO entity2DTO(Dentist d) {
        return new DentistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRegistration());
    }

    public Dentist DTO2entity(DentistDTO dto) {
        return new Dentist(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getRegistration());
    }
}
