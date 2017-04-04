package client;

import java.io.*;
import webservice.*;
import java.util.*;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.handler.MessageContext;

public class client {
    
    private static void header(TeamWebService port){       
        Map<String, Object> req_ctx = ((BindingProvider)port).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/TeamsService?wsdl");
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList("lol"));
        headers.put("Password", Collections.singletonList("lol"));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }
    
    private static void showTeam(TeamWebService port) throws MalformedURLException, IOException{
        header(port);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        System.out.println("For selection print column name like name=Arsenal to select information about club");
        System.out.println("For multiply selection write column name and <,> or = and value, after thar write 'and' and another columnt name and expression");
        System.out.println("Example, 'city=London and cups>3 and foundation>1900'");
        System.out.println("For exit write exit");
        String temp = br.readLine(); 
        try {
            List<Team> teams = port.getTeams(temp);
            for (Team team : teams) {
                System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
            }
            System.out.println("Total teams: " + teams.size());
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void insertTeam(TeamWebService port) throws MalformedURLException, IOException{
        header(port);
        String param[] = {"name","city","stadium","cups","foundation"};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String parametrs = "";
        for (int i=0;i<=4;++i){
            String temp = "";
            while (true){
                System.out.println("Input "+param[i]);
                temp = br.readLine();
                if (!temp.isEmpty())
                    break;
            }
            if (i != 4)
                parametrs +="'"+temp+"',";
            else parametrs +="'"+temp+"'";
        }
        try {
        int check = port.insertTeam(parametrs);
        if (check <= 0)
            System.out.println("There is no row with this id or something happens....");
        else System.out.println("Id of inserted row is " + check);
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void updateTeam(TeamWebService port) throws MalformedURLException, IOException {
        header(port);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String parametrs = "";
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        while (true){
            System.out.println("Write which field you want to update");
            String temp = br.readLine();
            System.out.println("Write new value");
            String value = br.readLine();
            parametrs += temp +"="+"'"+value+"' ";
            System.out.println("If you don't want change more data write NO");
            value = br.readLine();
            if (value.toLowerCase().equals("no"))
                break;
            else parametrs +=", ";
        }
        System.out.println("Write id, please");
        String temp = br.readLine();
        try {
        int check = port.updateTeam(parametrs,temp);
        if (check == 0){
            System.out.println("There is no row with this id or something happens....");
        } else {
                System.out.println("Count of updated rows: "+check);
        }
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void deleteTeam(TeamWebService port) throws MalformedURLException, IOException{
        header(port);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write id to delete row");
        String temp = br.readLine();
        try {
        int check = port.deleteTeam(temp);
        if (check == 0){
            System.out.println("There is no row with this id or something happens....");
        }
        else {
            System.out.println("Count of deleted rows: "+check);
        }
        } catch (DataError e){
             System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        TeamWebService port = teamService.getTeamWebServicePort();
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
                case "insert": insertTeam(port); break;
                case "update": updateTeam(port); break;
                case "delete": deleteTeam(port); break;
                case "show": showTeam(port); break;
                case "exit": System.exit(0);
                default:
                    System.out.println("There is no such command.... Retry, please.");
            }
        }
    }
}
