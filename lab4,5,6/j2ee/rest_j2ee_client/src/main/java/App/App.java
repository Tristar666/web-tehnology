/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Teams.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;

public class App {
   
    private static final String URL = "http://localhost:8080/rest_j2ee/rest/teams";
    
    private static void showTeam(){ 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        System.out.println("For selection print column name like name=Arsenal to select information about club");
        System.out.println("For multiply selection write column name and <,> or = and value, after thar write 'and' and another columnt name and expression");
        System.out.println("Example, 'city=London and cups>3 and foundation>1900'");
        System.out.println("For exit write exit");
        try {
            String temp = br.readLine(); 
            JSONObject json = new JSONObject();
            String[] parts = temp.split("and");
            for (String str : parts){
                String[] tmp = str.split("<|>|=");    
                tmp[0] = tmp[0].trim();
                tmp[1] = tmp[1].trim();
                for (int i=0;i<str.length();++i){
                    switch(str.charAt(i)){
                        case '=': tmp[0] += '='; break;
                        case '>': tmp[0] += '>'; break;
                        case '<': tmp[0] += '<'; break;
                    }
                }
                json.put(tmp[0], tmp[1]);            
            }
            String jsonString = json.toString();
            Client client = Client.create();
            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("name",URLEncoder.encode(jsonString, "UTF-8"));           
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON + ";charset=utf-8").header("Login","lol").header("Pass","lol").get(ClientResponse.class);            
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {               
                throw new IllegalStateException(response.getEntity(String.class));
            }       
            String str = response.getEntity(String.class);
            List<Team> teams = new Gson().fromJson(str, new TypeToken<List<Team>>(){}.getType());            
            for (Team team : teams) {
                System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
            }
            System.out.println("Total teams: " + teams.size());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void insertTeam() {
        try {
            String param[] = {"name","city","stadium","cups","foundation"};
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String jsonString = "";
            JSONObject json = new JSONObject();
            for (int i=0;i<=4;++i){
                String temp = "";
                while (true){
                    System.out.println("Input "+param[i]);
                    temp = br.readLine();
                    if (!temp.isEmpty())
                        break;
                }
                json.put(param[i], temp);
            }
            jsonString = json.toString();            
            Client client = Client.create();
            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("name",URLEncoder.encode(jsonString, "UTF-8"));                                                         
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON + ";charset=utf-8").header("Login","lol").header("Pass","lol").put(ClientResponse.class);           
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException(response.getEntity(String.class));
            }
            GenericType<String> type = new GenericType<String>() {};
            String id = response.getEntity(type);            
            if (Integer.parseInt(id) <=0)
                System.out.println("There is no row with this id or something happens....");
            else System.out.println("Id of inserted row is " + id);            
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void updateTeam() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            JSONObject json = new JSONObject();        
            System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
            while (true){
                System.out.println("Write which field you want to update");
                String temp = br.readLine();
                System.out.println("Write new value");
                String value = br.readLine();
                json.put(temp,value);
                System.out.println("If you don't want change more data write NO");
                value = br.readLine();
                if (value.toLowerCase().equals("no"))
                    break;
            }
            System.out.println("Write id, please");
            String temp = br.readLine();
            json.put("id",temp);
            String jsonString = json.toString();
            Client client = Client.create();
            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("name",URLEncoder.encode(jsonString, "UTF-8"));
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON + ";charset=utf-8").header("Login","lol").header("Pass","lol").post(ClientResponse.class);
            ClientResponse response1 = webResource.accept(MediaType.APPLICATION_JSON + ";charset=utf-8").header("Login","lol").header("Pass","lol").post(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException(response.getEntity(String.class));
            }
             if (response1.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                System.out.println("LOLKASAS");
                throw new IllegalStateException(response.getEntity(String.class));
            }
            GenericType<String> type = new GenericType<String>() {};
            String check = response.getEntity(type);
            GenericType<String> type1 = new GenericType<String>() {};
            String check1 = response1.getEntity(type1);
            if (Integer.parseInt(check) == 0){
                System.out.println("There is no row with this id or something happens....");
            } else {
                    System.out.println("Count of updated rows: "+check);
                    System.out.println("Count of updated rows: "+check1);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void deleteTeam(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Write id to delete row");
            String temp = br.readLine();
            Client client = Client.create();
            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("id",temp);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON + ";charset=utf-8").header("Login","lol").header("Pass","lol").delete(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException(response.getEntity(String.class));
            }
            GenericType<String> type = new GenericType<String>() {};
            String check = response.getEntity(type);
            if (Integer.parseInt(check) == 0){
                System.out.println("There is no row with this id or something happens....");
            } else {
                System.out.println("Count of deleted rows: "+check);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
       
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp="";
        while (true){
            System.out.println("Write one of five commands:");
            System.out.println("update - update team");
            System.out.println("insert - insert team");
            System.out.println("delete - delete team");
            System.out.println("show - show teams");
            System.out.println("exit - exit from application");
            temp = br.readLine();
            switch(temp){
                case "insert": insertTeam(); break;
                case "update": updateTeam(); break;
                case "delete": deleteTeam(); break;
                case "show": showTeam(); break;
                case "exit": System.exit(0);
                default:
                    System.out.println("There is no such command.... Retry, please.");
            }
        }
    }
}
