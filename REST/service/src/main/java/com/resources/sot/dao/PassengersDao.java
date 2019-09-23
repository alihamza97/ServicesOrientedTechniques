package com.resources.sot.dao;

import com.resources.sot.models.Passengers;

import java.util.ArrayList;
import java.util.List;
public class PassengersDao {
    private List<Passengers> passengersList;

    public PassengersDao() {
        passengersList = new ArrayList<Passengers>() {{
            add(new Passengers(1, "Ali", "3056325", 0));
            add(new Passengers(2, "Ali2", "3223242", 0));
            add(new Passengers(3, "Ali3", "23523523", 0));
            add(new Passengers(4, "Ali4", "23223523", 0));
            add(new Passengers(5, "Ali4", "2322323", 0));
        }};
    }

    public List<Passengers> getPassengersList() {
        return passengersList;
    }

    public void createPassenger(Passengers passenger) {
        passengersList.add(passenger);
    }

    public Passengers getPassengerById(int id) {
        for (Passengers passenger : passengersList) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        return null;
    }

    public boolean deletePassengertById(int id) {
        for (Passengers passenger : passengersList) {
            if (passenger.getId() == id) {
                passengersList.remove(id);
                return true;
            }
        }
        return false;
    }
}
