import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.jhonatansouza.Application;
import com.jhonatansouza.models.PlanetModel;
import com.jhonatansouza.repositories.PlanetRepository;
import java.util.List;

import com.jhonatansouza.swapi.SwapiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.aws.accesskey=AKIAIQ3QP3EWITZCN3KA",
        "amazon.aws.secretkey=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" })
public class PlanetIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;
    private final static Integer EXPECTED_MOVIES = 2;
    private final static String EXPECTED_NAME = "Mars XZY";

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private PlanetRepository repository;

    @Autowired
    private SwapiService swapi;

    @Test
    public void saveTestCase() {


        this.repository.save(this.pattern());

        List<PlanetModel> planets = repository.findAllByName("Alderaan");

        assertTrue("Not empty", !planets.isEmpty());
        assertTrue(planets.get(0).getMoviesAmount().equals(EXPECTED_MOVIES));

    }

    @Test
    public void deleteTestCase(){

        PlanetModel planet = this.repository.save(this.pattern());
        assertTrue(this.repository.exists(planet.getHashId()));
        this.repository.delete(planet.getHashId());
        assertFalse(this.repository.exists(planet.getHashId()));

    }

    @Test
    public void updateTestCase(){

        PlanetModel planet = this.repository.save(this.pattern());
        planet.setName(EXPECTED_NAME);

        PlanetModel p2 = this.repository.save(planet);

        assertTrue(planet.equals(p2));

    }


    private PlanetModel pattern(){

        PlanetModel planet = new PlanetModel();
        planet.setName("Alderaan");
        planet.setTerrain("grasslands, mountains");
        planet.setClimate("temperate");
        planet.setMoviesAmount(this.swapi.getMoviesAmount("Alderaan"));

        return planet;
    }


}
