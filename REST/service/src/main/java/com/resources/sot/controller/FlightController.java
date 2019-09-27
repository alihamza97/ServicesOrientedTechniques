package com.resources.sot.controller;
import com.resources.sot.dao.TicketDao;
import com.resources.sot.dao.PassengersDao;
import com.resources.sot.models.Tickets;
import com.resources.sot.models.Passengers;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("flights")
@Singleton
public class FlightController {
    public TicketDao ticketDao = new TicketDao();
    public PassengersDao passengersDao = new PassengersDao();

    //shows a list of passengers
    @GET
    @Path("passengers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Passengers> getPassenger() {
        return passengersDao.getPassengersList();
    }

    //shows a list of tickets
    @GET
    @Path("tickets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tickets> getTickets() {
        return ticketDao.getTicketsList();
    }

    //shows all the available tickets, when provided  a queryParam on the client
    @GET
    @Path("tickets/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tickets> getTicketByAirline(@QueryParam("airline") String airline){
        return ticketDao.getTicketsList().stream()
                .filter(ticket -> ticket.getAirlines().equals(airline))
                .collect(Collectors.toList());
    }

    // add a new object of ticket to the existing list of tickets
    @POST
   @Path("create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createTicket(@FormParam("id") int id, @FormParam("price") int price,@FormParam("airline") String airline) {
        boolean created=false;

        Tickets r=new Tickets(id,price,airline);
        ticketDao.getTicketsList().add(r);
        System.out.println("ticket created ");

    }
    @POST
    @Path("createPassenger")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createPassenger(@FormParam("id") int id, @FormParam("name") String name,
                                @FormParam("phone") String phone,
                                @FormParam("assignedticket") int ticketReserved) {
        boolean created=false;

        Passengers passengers=new Passengers(id,name,phone,ticketReserved);
        passengersDao.getPassengersList().add(passengers);
        System.out.println("passsenger created ");

    }
    //reserve an available ticket by giving a passenger id and ticket id
    @PUT
    @Path("reserve/{ticketId}/tickets/{passengerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTicket(@PathParam("ticketId") int ticketId,@PathParam("passengerId") int passengerId){
        Tickets tickets = ticketDao.getTicketById(ticketId);
        Passengers passengers = passengersDao.getPassengerById(passengerId);
        if (tickets==null){
            System.out.println("cant find ticket");
        }
        if (passengers ==null){
            System.out.println("cant find passengers");
            return Response.serverError().entity("passenger with "+passengers.getId()+" cannot be found").build();
        }else {
            tickets.setAvailable(false);
            passengers.setAssignedTicket(tickets.getId());
            System.out.println("ticket reserved");
            return Response.noContent().build();
        }
    }
  //romves a passenger by Id from the list
    @DELETE
    @Path("passengers/{id}")
    public Response deletePassenger(@PathParam("id")int id){
        Passengers p = passengersDao.getPassengerById(id);
        passengersDao.getPassengersList().remove(p);
        if(p==null){
            return Response.serverError().entity("passenger with "+p.getId()+" cannot be found").build();
        }else{
            System.out.println("passenger deleted");
            return Response.noContent().build();
        }

    }
    // removes a passenger by id from the list
    @DELETE
    @Path("ticket/{id}")
    public Response deleteTicket(@PathParam("id")int id){
        Tickets t = ticketDao.getTicketById(id);
        ticketDao.getTicketsList().remove(t);
        if(t==null){
            return Response.serverError().entity("ticket with "+t.getId()+" cannot be found").build();
        }
        else{
            System.out.println("ticket deleted");
            return Response.noContent().build();
        }

    }
}
