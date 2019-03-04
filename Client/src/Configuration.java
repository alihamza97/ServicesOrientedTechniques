import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Configuration {
    public static void main(String args[]) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        URI baseURI = UriBuilder.fromUri("http://localhost:9090/students").build();
        WebTarget serviceTarget = client.target(baseURI);
        WebTarget resourceTarget=serviceTarget.path("2");
        Invocation.Builder requestBuilder = resourceTarget.request().accept(MediaType.TEXT_PLAIN);
        //Post operation
//        Student student=new Student(2,"JohN");
//        Entity<Student> entity=Entity.entity(student,MediaType.APPLICATION_JSON);
//        Invocation.Builder requestBuilder = serviceTarget.path("2").request().accept(MediaType.APPLICATION_JSON);
        Response response = requestBuilder.delete();
//        Response response = serviceTarget.request().accept(MediaType.TEXT_PLAIN).put(entity);

//        if (response.getStatus() == 200)
//        {
//            String ent = response.readEntity(String.class);
//            System.out.println("service response is " + ent);
//
//        }
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode())
        {

            System.out.println("Student with id :"+" created");

        }

        else
        {
            System.out.println("Error cannot get reponse" + response);
            String entity = response.readEntity(String.class);

            System.out.println(entity);

        }
    }
}
