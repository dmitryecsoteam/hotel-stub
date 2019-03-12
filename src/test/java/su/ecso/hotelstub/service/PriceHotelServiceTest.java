package su.ecso.hotelstub.service;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import su.ecso.hotelstub.dto.Travels;
import su.ecso.hotelstub.repositories.TravelsDAO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dmitry on 12.03.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceHotelServiceTest {

    @Value("${numberOfMonths}")
    private int numberOfMonths;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TravelsDAO travels;

    @Autowired
    private PriceHotelService service;

    @Before
    public void setUp() throws Exception {
        Document travelToSave1 = new Document();
        travelToSave1
                .append("_id", "testID1")
                .append("origin", 1)
                .append("destination", 3)
                .append("date", "2000-01-01")
                .append("priceHotel", 1)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave1, "travels");

        Document travelToSave2 = new Document();
        travelToSave2
                .append("_id", "testID2")
                .append("origin", 1)
                .append("destination", 3)
                .append("date", "2000-01-02")
                .append("priceHotel", 7)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave2, "travels");

        Document travelToSave3 = new Document();
        travelToSave3
                .append("_id", "testID3")
                .append("origin", 1)
                .append("destination", 3)
                .append("date", "2000-01-03")
                .append("priceHotel", 2)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave3, "travels");

        Document travelToSave4 = new Document();
        travelToSave4
                .append("_id", "testID4")
                .append("origin", 1)
                .append("destination", 5)
                .append("date", "2000-01-01")
                .append("priceHotel", 10)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave4, "travels");

        Document travelToSave5 = new Document();
        travelToSave5
                .append("_id", "testID5")
                .append("origin", 1)
                .append("destination", 5)
                .append("date", "2000-01-02")
                .append("priceHotel", 7)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave5, "travels");

        Document travelToSave6 = new Document();
        travelToSave6
                .append("_id", "testID6")
                .append("origin", 1)
                .append("destination", 5)
                .append("date", "2000-01-03")
                .append("priceHotel", 2)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave6, "travels");
    }

    @Test
    public void testProcessTravels() throws Exception {

        // Run service
        service.processTravels(LocalDate.of(2000, 1, 1));

        // Get documents from DB
        List<Travels> result = mongoTemplate.findAll(Travels.class, "travels");

        // Each price must be greater 15
        result.forEach(travel -> {
            assertTrue(travel.getPriceHotel() >= 15);
        });
    }
}