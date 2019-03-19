package com.jhonatansouza.repositories;

import com.jhonatansouza.models.PlanetModel;
import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PlanetRepository extends CrudRepository<PlanetModel, String> {

    List<PlanetModel> findAllByName(String name);

    @Override
    List<PlanetModel> findAll();

}
