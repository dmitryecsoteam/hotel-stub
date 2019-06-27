package su.ecso.hotelstub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import su.ecso.hotelstub.dto.Travels;
import su.ecso.hotelstub.repositories.TravelsDAO;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dmitry on 09.03.2019.
 */
@Service
public class PriceHotelService {

    private static final Logger logger = LoggerFactory.getLogger(PriceHotelService.class);

    @Autowired
    private TravelsDAO travels;

    @Value("${numberOfMonths}")
    private int numberOfMonths;

    public void processTravels(LocalDate today) {

        logger.info("numberOfMonths: " + numberOfMonths);
        logger.info("Starting processing travels");

        try {

            int originMax = travels.getMaxOrigin();
            int destMax = travels.getMaxDestination();

            for (int i = 1; i <= originMax; i++) {
                for (int j = 1; j <= destMax; j++) {

                    // If travels for these orig and dest exist in DB
                    if (travels.exists(originMax, destMax, today.toString())) {

                        LocalDate end = today.plusMonths(numberOfMonths);
                        for (LocalDate date = today; date.isBefore(end); date = date.plusDays(1)) {
                            Travels travel = new Travels(i, j, date.toString(), ThreadLocalRandom.current().nextInt(15, 150));
                            travels.updateTravel(travel);

                            // Update 75% of travels
//                        if (ThreadLocalRandom.current().nextInt(4) > 0) {
//                            travels.updateTravel(travel);
//                        }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in processTravels", e);
        }

        logger.info("Finished processing travels");
    }
}
