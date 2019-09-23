package com.resources.sot.dao;

import java.util.ArrayList;
import java.util.List;
import com.resources.sot.models.Tickets;


public class TicketDao {
    private List<Tickets> ticketsList;

    public TicketDao() {
        ticketsList = new ArrayList<Tickets>() {{
            add(new Tickets(1, 150, "Transavia"));
            add(new Tickets(2, 200, "Ryan"));
            add(new Tickets(3, 100, "BritishAirways"));
            add(new Tickets(4, 120, "PIA"));
            add(new Tickets(5, 400, "AirCanada"));
            add(new Tickets(6, 136, "AirFrance"));
        }};
    }

    public List<Tickets> getTicketsList() {
        return ticketsList;
    }

    public void createTicket(Tickets tickets) {
        ticketsList.add(tickets);
    }

    public Tickets getTicketById(int id) {
        for (Tickets tickets : ticketsList) {
            if (tickets.getId() == id) {
                return tickets;
            }
        }
        return null;
    }

    public void updateTicketAvailability(Tickets tickets, boolean isAvailable) {
        tickets.setAvailable(isAvailable);
    }
}
