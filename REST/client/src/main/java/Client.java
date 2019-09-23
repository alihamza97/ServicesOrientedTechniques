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


    public static void main(String[] args) throws IOException {
        Input = new Scanner(System.in);
       client = new HttpClient();
        System.out.println("Please enter a number between 1-6 for POST, PUT ,POST ,DEL operations");

        while(true) {
            String selectedOption = Input.nextLine();

            // Show available options

            switch (selectedOption) {
                case "1":
                    getTicket();
                    break;
                case "2":
                    System.out.println("Please enter an airline name : ");
                    String selectedAirline = Input.nextLine();
                    getTicketByaAirline(selectedAirline);
                    break;
                case "3":
                    getPassengers();
                    break;
                case "4":
                    System.out.println("Please enter the ticket Number : ");
                    String selectedRoom= Input.nextLine();
                    System.out.println("Please enter the passenger Number : ");
                    String selectedStudent = Input.nextLine();

                    bookTicket(Integer.parseInt(selectedRoom), Integer.parseInt(selectedStudent));
                case "5":
                    System.out.println("Please enter the ID and the airline for the new ticket");
                    System.out.println("ID: ");
                    String id = Input.nextLine();
                    System.out.println("Airline: ");
                    String city = Input.nextLine();
                    System.out.println("Price: ");
                    String price = Input.nextLine();
                    createTicket(Integer.parseInt(id), city, price);
                case "6":
                    System.out.println("Please enter the ID of the ticket you want  to delete");
                    String selectedRoomId = Input.nextLine();
                    deleteRoom(selectedRoomId);
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
        NameValuePair queryData = new NameValuePair("ticket", airline);

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
                        "{\"id\": " + id + ", \"airline\": " +  "\""+ airline + "\", " + "\"price\": \"" + price + "\"}"
                )
        );

        postMethod.setRequestEntity(body);
        postMethod.setRequestHeader("Content-type",
                "application/x-www-form-urlencoded");

        getResponse(postMethod);

        postMethod = null;
    }


    static void bookTicket(int ticketId, int passengerId) throws IOException {
        PutMethod putMethod = new PutMethod(URL + String.format("reserve/%s/tickets/%s", ticketId, passengerId));

        getResponse(putMethod);

        putMethod = null;

    }

    static void deleteRoom(String ticketId) throws IOException {
        DeleteMethod deleteMethod = new DeleteMethod(URL + String.format("tickets/%s", ticketId));

        getResponse(deleteMethod);

        deleteMethod = null;
    }

}
