package com.jhonatansouza.swapi;

import com.jhonatansouza.dto.SwapiPlanetsDTO;
import com.jhonatansouza.exceptions.IndexOutRangeException;
import com.jhonatansouza.exceptions.SwapiException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

@Service
public class SwapiService {

    private static final String SWAPI_REST = "https://swapi.co/api/planets/";

    public Integer getMoviesAmount(String planetName) {

        try {

            ResponseEntity<HashMap> result = this.createConnection(SWAPI_REST.concat("?search=").concat(planetName));
            List rBody = (List) result.getBody().get("results");

            if(result.getStatusCode().equals(HttpStatus.OK)){
                if (rBody.isEmpty()) {
                    return 0;
                }

                List movies = ((List) ((Map) rBody.get(0)).get("films"));

                return movies.size();
            }

            throw new SwapiException("Swapi indisponible.");
        } catch (Exception e) {
            return 0;
        }

    }

    public SwapiPlanetsDTO listPlanets(Integer page) throws SwapiException, IndexOutRangeException {

        ResponseEntity<HashMap> result;

        try {
            result = this.createConnection(SWAPI_REST.concat("?page=" + page));
        } catch (Exception ex) {
            throw new IndexOutRangeException(" No result to index " + page);
        }

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            List content = (List) result.getBody().get("results");
            SwapiPlanetsDTO swapiData = new SwapiPlanetsDTO();
            swapiData.setResults(content);
            swapiData.setLength(content.size());
            swapiData.setPage(page);
            return swapiData;
        }

        throw new SwapiException("Failed to retrieve data of swapi.");
    }

    private ResponseEntity<HashMap> createConnection(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("userAgent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(endpoint, HttpMethod.GET, entity, HashMap.class);
    }
}
