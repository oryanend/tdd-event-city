package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> result = repository.findAll(Sort.by("name"));
        return result.stream().map(CityDTO::new).toList();
    }

    @Transactional()
    public CityDTO insert(CityDTO dto) {
        City city = new City();
        city.setId(dto.getId());
        city.setName(dto.getName());
        city = repository.save(city);
        return new CityDTO(city);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
        try{
            repository.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }
}
