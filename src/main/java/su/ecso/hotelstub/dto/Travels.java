package su.ecso.hotelstub.dto;

import org.springframework.data.annotation.Id;

/**
 * Created by Dmitry on 07.10.2018.
 */
public class Travels {

    @Id
    private String id;

    private int origin;
    private int destination;
    private String date;
    private int priceHotel;

    public Travels() {
    }

    public Travels(String id, int origin, int destination, String date, int priceHotel) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.priceHotel = priceHotel;
    }

    public Travels(int origin, int destination, String date, int priceHotel) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.priceHotel = priceHotel;
    }

    public int getPriceHotel() {
        return priceHotel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriceHotel(int priceHotel) {
        this.priceHotel = priceHotel;
    }

    @Override
    public String toString() {
        return "Travels{" +
                "id: '" + id + '\'' +
                ", origin: " + origin +
                ", destination: " + destination +
                ", date: '" + date + '\'' +
                ", priceHotel: " + priceHotel +
                '}';
    }
}
