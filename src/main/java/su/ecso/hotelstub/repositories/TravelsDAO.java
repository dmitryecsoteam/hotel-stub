package su.ecso.hotelstub.repositories;


import su.ecso.hotelstub.dto.Travels;

/**
 * Created by Dmitry on 07.10.2018.
 */
public interface TravelsDAO {
    void updateTravel(Travels travel);
    int getMaxOrigin();
    int getMaxDestination();
    boolean exists(int origin, int destination, String date);
}
