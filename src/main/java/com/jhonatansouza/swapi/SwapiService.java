package com.jhonatansouza.swapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhonatansouza.dto.SwapiPlanetsDTO;
import com.jhonatansouza.exceptions.IndexOutRangeException;
import com.jhonatansouza.exceptions.SwapiException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SwapiService {

    private static final String SWAPI_REST = "https://swapi.co/api/planets/";

    public Integer getMoviesAmount(String planetName) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.add("userAgent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0");
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<HashMap> result = restTemplate.exchange(SWAPI_REST.concat("?search=").concat(planetName), HttpMethod.GET, entity, HashMap.class);
            List rBody = (List) result.getBody().get("results");

            if (rBody.isEmpty()) {
                return 0;
            }

            List movies = ((List) ((Map) rBody.get(0)).get("films"));

            return movies.size();
        } catch (Exception e) {
            return 0;
        }

    }

    public SwapiPlanetsDTO listPlanets(Integer page) throws SwapiException, IndexOutRangeException {


        HttpHeaders headers = new HttpHeaders();
        headers.add("userAgent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<HashMap> result;

        try {
            result = restTemplate.exchange(SWAPI_REST.concat("?page=" + page), HttpMethod.GET, entity, HashMap.class);
        } catch (Exception ex) {
            throw new IndexOutRangeException(" No result to index " + page);
        }

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            List content = (List) result.getBody().get("results");
            ObjectMapper obj = new ObjectMapper();
            SwapiPlanetsDTO swapiData = new SwapiPlanetsDTO();
            swapiData.setResults(content);
            swapiData.setLength(content.size());
            swapiData.setPage(page);
            return swapiData;
        }

        throw new SwapiException("Failed to retrieve data of swapi.");
    }
}
