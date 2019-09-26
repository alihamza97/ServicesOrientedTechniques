import com.cedarsoftware.util.io.JsonWriter;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.httpclient.HttpClient;


public class Client {
    static final String URL = "http://localhost:8080/travelling/rest/flights/";

    static Scanner Input;
    static HttpClient client;
    static HttpMethodBase httpMethod;

//Please note that when you delete a passenger/ticket or perform other PUT/POST operations, you will still be getting
    // list of passengers/tickets because this REST service is stateless
    public static void main(String[] args) throws IOException {
        Input = new Scanner(System.in);
       client = new HttpClient();
        System.out.println("Please make a choice between different options" +
                "\n Press 1 To get list of all available tickets" +
                "\n Press 2 to get available tickets by airline" +
                "\n Press 3 to get an overview of all passengers" +
                "\n Press 4 to reserve ticket" +
                "\n Press 5 to create a new ticket" +
                "\n Press 6 to delete a previously made booking request " +
                "\n Press 7 to create a new passenger" +
                "\n Press 8 to delete a passenger");

        while(true) {
            String selectedOption = Input.nextLine();

            // Show available options

            switch (selectedOption) {
                case "1":
                    getTicket();
                    break;
                case "2":
                    System.out.println("***Please enter the airline name exactly as shown below to get details of all available ticket***" +
                            "\n Available airlines are: " +
                            "\n Transavia" +
                            "\n BritishAirways" +
                            "\n Ryan" +
                            "\nPIA" +
                            "\nAirCanada" +
                            "\nAirFrance");
                    String selectedAirline = Input.nextLine();
//                    if(selectedAirline=="Transavia"||selectedAirline=="BritishAirways"||selectedAirline=="Ryan"||
//                    selectedAirline=="PIA"||selectedAirline=="AirCanada"||selectedAirline=="AirFrance"){
                        getTicketByaAirline(selectedAirline);
                        break;
//                    }else{
//                        System.out.println("Please make a selection again");
//                    }

                case "3":
                    getPassengers();
                    break;
                case "4":
                    System.out.println("Please enter the ticket Number : ");
                    String selectedTicket= Input.nextLine();
                    System.out.println("Please enter a passenger Number : ");
                    String selectedPassenger = Input.nextLine();

                    bookTicket(Integer.parseInt(selectedTicket), Integer.parseInt(selectedPassenger));
                case "5":
                    System.out.println("Please enter the ID and the airline for the new ticket");
                    System.out.println("ID: ");
                    String id = Input.nextLine();
                    System.out.println("Airline: ");
                    String airline = Input.nextLine();
                    System.out.println("Price: ");
                    String price = Input.nextLine();
                    createTicket(Integer.parseInt(id), airline, price);

                    break;

                case "6":
                    System.out.println("Please enter the ID of the ticket you want  to delete");
                    String selectedTicketId = Input.nextLine();
                    deleteTicket(selectedTicketId);
                    break;
                case "7":
                    System.out.println("Please provide the data below to create a new passenger");

                    System.out.println("ID: ");
                    String ID = Input.nextLine();
                    System.out.println("Name: ");
                    String Name = Input.nextLine();
                    System.out.println("Phone: ");
                    String phone = Input.nextLine();
                    int assignedTicket=1;
                    createPassenger(Integer.parseInt(ID),Name,phone,assignedTicket);
                    break;
                case "8":
                    System.out.println("Please enter the ID of the passenger you want  to delete");
                    String selectedPassengerID = Input.nextLine();
                    deletePassenger(selectedPassengerID);
                    break;
                default:
                    break;
            }

            selectedOption = null;
        }
    }

    static HttpMethodBase getHttpMethod(String methodType, String url) {
        HttpMethodBase httpMethod;
        switch (methodType.toUpperCase()) {
            case "GET":
                httpMethod = new GetMethod(url);
                break;
            case "POST":
                httpMethod = new PostMethod(url);
                break;
            case "PUT":
                httpMethod = new PutMethod(url);
            case "DELETE":
                httpMethod = new DeleteMethod(url);
            default:
                httpMethod = new GetMethod(url);
        }

        httpMethod.setRequestHeader("Content-type",
                "application/json");
        return httpMethod;
    }

    static HttpMethodBase setData(HttpMethodBase httpMethod, String dataType, Object data) {
        switch (dataType.toUpperCase()) {
            case "QUERY":
                httpMethod.setQueryString((NameValuePair[]) data);
                break;
            case "PARAMS":
                httpMethod.setParams((HttpMethodParams) data);
                break;
            default:
                httpMethod.setParams((HttpMethodParams) data);
                break;
        }
        return httpMethod;
    }

    static void getResponse(HttpMethodBase method) throws IOException {
        int statusCode = client.executeMethod(method);
        if (statusCode >= 200 && statusCode <= 400) {
            System.out.print(method.getResponseBodyAsString());
        } else {
            System.out.println(method.getResponseBodyAsString());
            System.out.println("There is an error please try again.");
        }
    }

    static void getResponse(HttpMethodBase method, boolean JsonResponse) throws IOException {
        int statusCode = client.executeMethod(method);
        if (statusCode >= 200 && statusCode <= 400) {
            if(JsonResponse) {
                System.out.print(JsonWriter.formatJson(method.getResponseBodyAsString()));
            } else {
                System.out.println(method.getResponseBodyAsString());
                getResponse(method);
            }
        } else {
            System.out.println(method.getResponseBodyAsString());
            System.out.println("There is an error please try again.");
        }
    }

    static void getTicketByaAirline(String airline) throws IOException {
        httpMethod = getHttpMethod("GET", URL + "tickets/search");
        NameValuePair queryData = new NameValuePair("airline", airline);

        httpMethod = setData(httpMethod, "QUERY", new NameValuePair[]{queryData});

        getResponse(httpMethod, true);

        httpMethod = null;
    }

    static void getTicket() throws IOException {
        httpMethod = getHttpMethod("GET", URL + "tickets");

        getResponse(httpMethod, true);

        httpMethod = null;
    }


    static void getPassengers() throws IOException {
        httpMethod = getHttpMethod("GET", URL + "passengers");

//        System.out.println("-- All Students --");
        getResponse(httpMethod, true);

        httpMethod = null;
    }

    static void createTicket(int id, String airline, String price) throws IOException {
        PostMethod postMethod = new PostMethod(URL+"create");

        StringRequestEntity body = new StringRequestEntity(
                JsonWriter.formatJson(
                        "{\"Id\": " + id + ", \"airline\": " +  "\""+ airline + "\", " + "\"price\": \"" + price + "\"}"
                )
        );

        postMethod.setRequestEntity(body);
        postMethod.setRequestHeader("Content-type",
                "application/x-www-form-urlencoded");

        getResponse(postMethod);

       // postMethod = null;
    }
    static void createPassenger(int id, String name, String phone,int assignedTicket ) throws IOException {
        PostMethod postMethod = new PostMethod(URL+"createPassenger");

        StringRequestEntity body = new StringRequestEntity(
                JsonWriter.formatJson(
                        "{\"Id\": " + id + ", \"name\": " +  "\""+ name + "\", " + "\"phone\": \"" + phone + "\", "+ "\"assignedticket\": \""+assignedTicket+"\"}"
                )
        );

        postMethod.setRequestEntity(body);
        postMethod.setRequestHeader("Content-type",
                "application/x-www-form-urlencoded");

        getResponse(postMethod);

        // postMethod = null;
    }


    static void bookTicket(int ticketId, int passengerId) throws IOException {
        PutMethod putMethod = new PutMethod(URL + String.format("reserve/%s/tickets/%s", ticketId, passengerId));

        getResponse(putMethod);

        putMethod = null;

    }

    static void deleteTicket(String ticketId) throws IOException {
        DeleteMethod deleteMethod = new DeleteMethod(URL + String.format("ticket/%s", ticketId));

        getResponse(deleteMethod);

        deleteMethod = null;
    }
    static void deletePassenger(String PassengerId) throws IOException {
        DeleteMethod deleteMethod = new DeleteMethod(URL + String.format("passengers/%s", PassengerId));

        getResponse(deleteMethod);

        deleteMethod = null;
    }

}
