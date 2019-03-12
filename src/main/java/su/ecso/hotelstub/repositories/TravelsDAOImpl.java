package su.ecso.hotelstub.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import su.ecso.hotelstub.dto.Travels;


/**
 * Created by Dmitry on 07.10.2018.
 */
@Repository
public class TravelsDAOImpl implements TravelsDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TravelsDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateTravel(Travels travel) {
        Query query = new Query();
        query.addCriteria(Criteria.where("origin").is(travel.getOrigin()))
                .addCriteria(Criteria.where("destination").is(travel.getDestination()))
                .addCriteria(Criteria.where("date").is(travel.getDate()));
        Update update = new Update();
        update.set("priceHotel", travel.getPriceHotel());

        mongoTemplate.updateFirst(query, update, Travels.class);
    }

    @Override
    public int getMaxOrigin() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "origin"));

        return mongoTemplate.findOne(query, Travels.class).getOrigin();
    }

    @Override
    public int getMaxDestination() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "destination"));

        return mongoTemplate.findOne(query, Travels.class).getDestination();
    }

    @Override
    public boolean exists(int origin, int destination, String date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("origin").is(origin))
                .addCriteria(Criteria.where("destination").is(destination))
                .addCriteria(Criteria.where("date").is(date));

        return mongoTemplate.count(query, Travels.class) > 0;
    }

}
