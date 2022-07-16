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
    public DentistDTO add(DentistDTO dentist) throws BadRequestException {
        if (dentist.isInvalid()) {
            throw new BadRequestException("Error DentistService: Wrong input values");
        }

        Dentist d = new Dentist(dentist);

        logger.info("Dentist with Registration : " + d.getRegistration() + " added.");

        return new DentistDTO( dentistRepository.save(d) );
    }

    @Override
    public DentistDTO update(DentistDTO d) throws BadRequestException, NotFoundException {
        if (d.isInvalid()) {
            throw new BadRequestException("Error DentistService: Wrong input values");
        }
        DentistDTO dentist = this.findById(d.getId());

        dentist.setFirstName( d.getFirstName() );
        dentist.setLastName( d.getLastName() );
        dentist.setRegistration( d.getRegistration() );

        dentistRepository.save(new Dentist( dentist ));
        logger.info("Dentist with Registration : " + dentist.getRegistration() + " updated.");

        return dentist;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        DentistDTO d = this.findById(id);
        dentistRepository.deleteById(id);
        logger.info("Dentist with ID:" + id + "deleted.");
    }

    @Override
    public Set<DentistDTO> findAll() {
        List<Dentist> dentists = dentistRepository.findAll();
        Set<DentistDTO> set = new HashSet<>();
        for (Dentist dentist : dentists) {
            set.add(new DentistDTO(dentist));
        }

        logger.info("All dentists were listed.");
        return set;
    }

    @Override
    public DentistDTO findById(Long id) throws NotFoundException {
        Optional<Dentist> d = dentistRepository.findById(id);
        if (!d.isPresent())
            throw new NotFoundException("Error DentistService : Dentist with ID : " + id + " doesn't exist.");

        return new DentistDTO(d.get());
    }
}
