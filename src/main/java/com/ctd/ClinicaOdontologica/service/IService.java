package com.ctd.ClinicaOdontologica.service;

import com.ctd.ClinicaOdontologica.exceptions.BadRequestException;
import com.ctd.ClinicaOdontologica.exceptions.NotFoundException;

import java.util.Set;

public interface IService<T> {
    T add(T element) throws BadRequestException, NotFoundException;

    T update(T element) throws BadRequestException, NotFoundException;

    void delete(Long id) throws NotFoundException;

    Set<T> findAll();

    T findById(Long id) throws NotFoundException;
}
