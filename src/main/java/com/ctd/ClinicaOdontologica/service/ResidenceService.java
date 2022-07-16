package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;
import com.ctd.ClinicaOdontologica.model.Residence;
import com.ctd.ClinicaOdontologica.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

@Service("ResidenceService")
public class ResidenceService implements IService<Residence> {
    @Autowired
    private ResidenceRepository residenceRepository;
    public static Logger logger = Logger.getLogger(ResidenceService.class);

    @Override
    public Residence add(Residence residence) throws BadRequestException {
        if( residence.isInvalid() ) throw new BadRequestException("Error ResidenceService: Wrong input values");
        logger.info("New residence added.");
        return residenceRepository.save(residence);
    }

    @Override
    public Residence update(Residence r) throws NotFoundException {
        Residence residence = this.findById(r.getId());

        residence.setStreet(r.getStreet());
        residence.setNumber(r.getNumber());
        residence.setLocation(r.getLocation());
        residence.setProvince(r.getProvince());

        logger.info("Residence id: " + r.getId() + " updated.");
        return residenceRepository.save(residence);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        try {
            this.findById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Residence doesn't exist.");
        }
        residenceRepository.deleteById(id);
    }

    @Override
    public Set<Residence> findAll() {
        List<Residence> residences = residenceRepository.findAll();

        Set<Residence> set = new HashSet<>();
        for (Residence residence : residences) {
            set.add(residence);
        }
        return set;
    }

    @Override
    public Residence findById(Long id) throws NotFoundException {
        Optional<Residence> r = residenceRepository.findById(id);
        if (!r.isPresent())
            throw new NotFoundException("Residence it wasn't found.");

        return r.get();
    }

}
