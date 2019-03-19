package com.jhonatansouza.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhonatansouza.converter.PlanetConverter;
import com.jhonatansouza.dto.PlanetDTO;
import com.jhonatansouza.dto.SwapiPlanetsDTO;
import com.jhonatansouza.error.ErrorHandler;
import com.jhonatansouza.exceptions.IndexOutRangeException;
import com.jhonatansouza.exceptions.PlanetException;
import com.jhonatansouza.exceptions.SwapiException;
import com.jhonatansouza.models.PlanetModel;
import com.jhonatansouza.repositories.PlanetRepository;
import com.jhonatansouza.swapi.SwapiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetRepository repository;

    private final SwapiService swapi;

    @Autowired
    public PlanetController(final PlanetRepository repository, final SwapiService swapi) {
        this.repository = repository;
        this.swapi = swapi;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public PlanetDTO insertPlanet(@Valid @RequestBody PlanetDTO planet, Errors errors) throws PlanetException {

        if (errors.hasFieldErrors()) {
            throw new PlanetException("Verify all obligatory fields.");
        }

        if (planet.getHashId() != null) {
            throw new PlanetException("ID field is not allowed!");
        }

        planet.setMoviesAmount(this.
                swapi.getMoviesAmount(planet.getName()));

        return PlanetConverter
                .convertPlanetDTOFromModel(this.repository.save(PlanetConverter.convertPlanetModelFromDTO(planet)));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<PlanetDTO> listAllPlanets() {

        List<PlanetModel> list = this.repository.findAll();
        return list.stream().map(PlanetConverter::convertPlanetDTOFromModel).collect(Collectors.toList());
    }

    @RequestMapping(value = "/swapi/planets/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SwapiPlanetsDTO> listSwapiPlanets(@NotNull @Min(1) @PathParam("page") Integer page) throws PlanetException, SwapiException, IndexOutRangeException {

        if (page == null) {
            throw new PlanetException("Invalid page attribute.");
        }

        if (page > 0) {
            return ResponseEntity.ok(this.swapi.listPlanets(page));
        }

        throw new PlanetException("Invalid page, the page must be great than 0");

    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public List<PlanetModel> findByName(@Valid @NotNull @PathVariable String name) {
        return this.repository.findAllByName(name);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public PlanetDTO updatePlanet(@Valid @RequestBody PlanetDTO planet) throws PlanetException {

        if (StringUtils.isNotBlank(planet.getHashId())) {

            if(this.repository.exists(planet.getHashId())){
                return PlanetConverter.convertPlanetDTOFromModel(
                        this.repository.save(PlanetConverter.convertPlanetModelFromDTO(planet)));
            }

            throw new PlanetException( String.format( "This planet %s doesn't exist.", planet.getName()) );

       }

        throw new PlanetException("id attribute is missing.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePlanet(@NotNull @PathVariable("id") String id) throws PlanetException {

        if (this.repository.exists(id)) {
            this.repository.delete(id);
            return ResponseEntity.ok(String.format("Planet with id %s was Removed.",id) );
        }

        throw new PlanetException(String.format("Planet with id %s doesn't exist.", id));

    }

    @ExceptionHandler({PlanetException.class, SwapiException.class, IndexOutRangeException.class})
    public ResponseEntity<String> attributeMissing(Exception ex) {
        ObjectMapper obj = new ObjectMapper();
        String message = "";

        try {
            message = obj.writeValueAsString(new ErrorHandler(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> serverError(Exception ex) {
        ObjectMapper obj = new ObjectMapper();
        String message = "";

        try {
            message = obj.writeValueAsString(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }


}
