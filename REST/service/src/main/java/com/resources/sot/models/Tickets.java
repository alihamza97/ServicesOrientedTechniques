package com.resources.sot.models;

import javax.inject.Singleton;

@Singleton
public class Tickets {
    private int id;
    private String airlines;
    private int price;
    private boolean available;

    public Tickets() {}

    public Tickets(int id, int price, String airlines) {
        this.id = id;
        this.price = price;
        this.airlines = airlines;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getAirlines() {
        return airlines;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
