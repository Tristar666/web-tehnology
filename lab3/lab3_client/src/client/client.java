package client;

import java.io.*;
import webservice.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.lang.Boolean;

public class client {
    
    private static void showTeam() throws MalformedURLException, IOException{
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        System.out.println("For selection all rows print all or nothing select, other way print column name like name=Arsenal to select information about club");
        System.out.println("For multiply selection write column name and <,> or = and value, after thar write 'and' and another columnt name and expression");
        System.out.println("Example, 'city=London and cups>3 and foundation>1900'");
        System.out.println("For exit write exit");
        String temp = br.readLine(); 
        try {
            List<Team> teams = teamService.getTeamWebServicePort().getTeams(temp);
            for (Team team : teams) {
                System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
            }
            System.out.println("Total teams: " + teams.size());
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void insertTeam() throws MalformedURLException, IOException{
        String param[] = {"name","city","stadium","cups","foundation"};
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
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
        int check = teamService.getTeamWebServicePort().insertTeam(parametrs);
        if (check <= 0)
            System.out.println("There is no row with this id or something happens....");
        else System.out.println("Id of inserted row is " + check);
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void updateTeam() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
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
        int check = teamService.getTeamWebServicePort().updateTeam(parametrs,temp);
        if (check == 0){
            System.out.println("There is no row with this id or something happens....");
        } else {
                System.out.println("Count of updated rows: "+check);
        }
        } catch (DataError e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void deleteTeam() throws MalformedURLException, IOException{
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write id to delete row");
        String temp = br.readLine();
        try {
        int check = teamService.getTeamWebServicePort().deleteTeam(temp);
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
