package com.resources.sot.models;

import javax.inject.Singleton;


public class Passengers {
    private int id;
    private String name;
    private String phoneNumber;
    private int assignedTicket;
    public Passengers(int id, String name, String phoneNumber, int assignedTicket) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.assignedTicket = assignedTicket;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAssignedTicket() {
        return assignedTicket;
    }

    public void setAssignedTicket(int assignedTicket) {
        this.assignedTicket = assignedTicket;
    }
}
