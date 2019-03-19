package com.jhonatansouza.converter;

import com.jhonatansouza.dto.PlanetDTO;
import com.jhonatansouza.models.PlanetModel;

public class PlanetConverter {

    public static PlanetModel convertPlanetModelFromDTO(PlanetDTO dto){
        PlanetModel p = new PlanetModel();
        p.setHashId(dto.getHashId());
        p.setClimate(dto.getClimate());
        p.setMoviesAmount(dto.getMoviesAmount());
        p.setName(dto.getName());
        p.setTerrain(dto.getTerrain());

        return p;
    }

    public static PlanetDTO convertPlanetDTOFromModel(PlanetModel model){

        PlanetDTO dto = new PlanetDTO();
        dto.setHashId(model.getHashId());
        dto.setClimate(model.getClimate());
        dto.setMoviesAmount(model.getMoviesAmount());
        dto.setTerrain(model.getTerrain());
        dto.setName(model.getName());

        return dto;
    }


}
