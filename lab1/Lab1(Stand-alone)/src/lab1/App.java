package lab1;
import WebService.*;
import connectionUtils.ConnectionUtil;
import javax.xml.ws.Endpoint;
public class App {

   
    public static void main(String[] args) {
        String url = "http://localhost:8080/TeamsService";
        Endpoint.publish(url, new TeamWebService());     
    }
    
}
