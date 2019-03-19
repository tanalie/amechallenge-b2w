import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.jhonatansouza.Application;
import com.jhonatansouza.models.PlanetModel;
import com.jhonatansouza.repositories.PlanetRepository;

import java.util.List;

import com.jhonatansouza.swapi.SwapiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class PlanetApiTest {

    private DynamoDBMapper dynamoDBMapper;
    private final static String PLANET_NAME = "Alderaan";
    private final static Integer EXPECTED_MOVIES = 2;
    private final static String EXPECTED_NAME = "Mars XZY";

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private PlanetRepository repository;

    @Mock
    private SwapiService swapi;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTestCase() {

        when(this.swapi.getMoviesAmount(PLANET_NAME))
                .thenReturn(2);

        this.repository.save(this.pattern());

        List<PlanetModel> planets = repository.findAllByName(PLANET_NAME);

        assertTrue("Not empty", !planets.isEmpty());
        assertFalse(planets.isEmpty());
        assertTrue(planets.get(0).getMoviesAmount().equals(EXPECTED_MOVIES));

    }

    @Test
    public void deleteTestCase() {

        PlanetModel planet = this.repository.save(this.pattern());
        assertTrue(this.repository.exists(planet.getHashId()));
        this.repository.delete(planet.getHashId());
        assertFalse(this.repository.exists(planet.getHashId()));

    }

    @Test
    public void updateTestCase() {

        PlanetModel planet = this.repository.save(this.pattern());
        planet.setName(EXPECTED_NAME);

        PlanetModel p2 = this.repository.save(planet);

        assertTrue(planet.equals(p2));

    }

    @Test
    public void findTestCase() {

        PlanetModel pp = this.repository.save(this.pattern());
        PlanetModel planet = this.repository.findOne(pp.getHashId());

        assertTrue(planet != null);
        this.repository.delete(pp.getHashId());

    }


    private PlanetModel pattern() {

        PlanetModel planet = new PlanetModel();
        planet.setName(PLANET_NAME);
        planet.setTerrain("grasslands, mountains");
        planet.setClimate("temperate");
        planet.setMoviesAmount(this.swapi.getMoviesAmount(PLANET_NAME));

        return planet;

    }


}
