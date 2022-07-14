package com.ctd.ClinicaOdontologica.repository;

import com.ctd.ClinicaOdontologica.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, Long> {
}
