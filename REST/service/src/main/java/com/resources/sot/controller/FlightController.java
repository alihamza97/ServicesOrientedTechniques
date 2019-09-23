package com.resources.sot.controller;
import com.resources.sot.dao.TicketDao;
import com.resources.sot.dao.PassengersDao;
import com.resources.sot.models.Tickets;
import com.resources.sot.models.Passengers;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("flights")
public class FlightController {
    public TicketDao ticketDao = new TicketDao();
    public PassengersDao passengersDao = new PassengersDao();

    @GET
    @Path("passengers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Passengers> getPassenger() {
        return passengersDao.getPassengersList();
    }

    @GET
    @Path("tickets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tickets> getTickets() {
        return ticketDao.getTicketsList();
    }

    @GET
    @Path("tickets/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tickets> getTicketByAirline(@QueryParam("airline") String airline){
        return ticketDao.getTicketsList().stream()
                .filter(ticket -> ticket.getAirlines().equals(airline))
                .collect(Collectors.toList());
    }

    @POST
   @Path("create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createTicket(@FormParam("id") int id, @FormParam("price") int price,@FormParam("airline") String airline) {
        boolean created=false;

        Tickets r=new Tickets(id,price,airline);
        ticketDao.getTicketsList().add(r);
        System.out.println("ticket created ");

    }

    @PUT
    @Path("reserve/{ticketId}/tickets/{passengerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTicket(@PathParam("ticketId") int ticketId,@PathParam("passengerId") int passengerId){
        Tickets tickets = ticketDao.getTicketById(ticketId);
        Passengers passengers = passengersDao.getPassengerById(passengerId);
        if (tickets==null){
            System.out.println("cant find ticket");
        }
        if (passengers ==null){
            System.out.println("cant find passengers");
        }else {
            tickets.setAvailable(false);
            passengers.setAssignedTicket(tickets.getId());
            System.out.println("ticket reserved");
        }
    }

    @DELETE
    @Path("passengers/{id}")
    public void deletePassenger(@PathParam("id")int id){
        Passengers passengers = passengersDao.getPassengerById(id);
        passengersDao.getPassengersList().remove(passengers);
    }

    @DELETE
    @Path("ticket/{id}")
    public void deleteTicket(@PathParam("id")int id){
        Tickets r = ticketDao.getTicketById(id);
        ticketDao.getTicketsList().remove(r);
        System.out.println("ticket deleted");
    }
}
