package su.ecso.hotelstub.repositories;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import su.ecso.hotelstub.dto.Travels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Dmitry on 12.03.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelsDAOImplTest {

    @Autowired
    TravelsDAO travels;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {
        Document travelToSave1 = new Document();
        travelToSave1
                .append("_id", "testID1")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-01")
                .append("priceHotel", 102)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave1, "travels");

        Document travelToSave2 = new Document();
        travelToSave2
                .append("_id", "testID2")
                .append("origin", 1)
                .append("destination", 5)
                .append("date", "2000-01-01")
                .append("priceHotel", 70)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave2, "travels");

        Document travelToSave3 = new Document();
        travelToSave3
                .append("_id", "testID3")
                .append("origin", 1)
                .append("destination", 3)
                .append("date", "2000-01-01")
                .append("priceHotel", 25)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave3, "travels");
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.dropCollection("travels");
    }

    @Test
    public void testGetMaxOrigin() throws Exception {
        int originMax = travels.getMaxOrigin();
        assertEquals(1, originMax);
    }

    @Test
    public void testUpdateTravel() throws Exception {

        // 1. Update existing document
        Travels travel = new Travels(0, 0, "2000-01-01", 111);
        travels.updateTravel(travel);

        Document resultToMatch = new Document();
        resultToMatch
                .append("_id", "testID1")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-01")
                .append("priceHotel", 111)
                .append("otherAttribute", "test value");

        Document result = mongoTemplate.findById("testID1", Document.class, "travels");
        assertEquals(resultToMatch, result);


        // 2. Update non-existing document
        Travels travelNotExists = new Travels(0, 0, "2000-01-02", 111);
        travels.updateTravel(travelNotExists);
        long cnt = mongoTemplate.count(new Query(), "travels" );
        assertEquals(3, cnt);
    }

    @Test
    public void testGetMaxDestination() throws Exception {
        int destinationMax = travels.getMaxDestination();
        assertEquals(5, destinationMax);
    }

    @Test
    public void testExists() throws Exception {

        // 1. Travel exists in DB
        boolean exists1 = travels.exists(1, 5, "2000-01-01");
        assertTrue(exists1);

        // 2. Travel doesn't exist in DB
        boolean exists2 = travels.exists(1, 5, "2000-01-02");
        assertFalse(exists2);
    }
}