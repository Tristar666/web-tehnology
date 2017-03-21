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
        String parametr = "";
        String temp = br.readLine();
        int check=0;
        for (int i=0;i<temp.length();++i){
            switch(temp.charAt(i)){
                case '=': parametr += temp.charAt(i); parametr +='\''; check = 1; break;
                case '>': parametr += temp.charAt(i); parametr +='\''; check = 1; break;
                case '<': parametr += temp.charAt(i); parametr +='\''; check = 1; break;
                case ' ': 
                    if (check == 1){
                        parametr += '\'';
                    }
                    check = 0;
                default: parametr += temp.charAt(i);
            }
        }
        if (check == 1){
            parametr += '\'';
        }
        List<Team> teams = teamService.getTeamWebServicePort().getTeams(parametr);
        for (Team team : teams) {
            System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
        }
        System.out.println("Total teams: " + teams.size());
    }

    private static boolean check_field(String str){
        switch(str){
            case "city": return true;
            case "name": return true;
            case "stadium": return true;
            case "cups": return true;
            case "foundation": return true;
            default: return false;
        }
    }
    
    private static void insertTeam() throws MalformedURLException, IOException{
        String param[] = {"name","city","stadium","cups","foundation"};
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String parametrs = "insert into teams (name,city,stadium,cups,foundation) values(";
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
            else parametrs +="'"+temp+"')";
        }
        System.out.println(parametrs);
        int check = teamService.getTeamWebServicePort().insertTeam(parametrs);
        if (check <= 0)
            System.out.println("There is no row with this id or something happens....");
        else System.out.println("Id of inserted row is " + check);
    }
    
    private static void updateTeam() throws MalformedURLException, IOException{
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String parametrs = "update teams set ";
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        while (true){
            System.out.println("Write which field you want to update");
            String temp = br.readLine();
            if (!check_field(temp)){
                System.out.println("Error, this field doesn't exit!");
                continue;
            }
            System.out.println("Write new value");
            String value = br.readLine();
            parametrs += temp +"="+"'"+value+"' ";
            System.out.println("If you don't want change more data write NO");
            value = br.readLine();
            if (value.toLowerCase().equals("no"))
                break;
            else parametrs +=", ";
        }
        parametrs +="where id=";
        while (true){
            System.out.println("Write id, please");
            String temp = br.readLine();
            int check = teamService.getTeamWebServicePort().updateTeam(parametrs+temp);
            if (check == 0){
                System.out.println("There is no row with this id or something happens....");
                System.out.println("do you want continue?");
                System.out.println("yes/no");
                temp = br.readLine();
                if (temp.toLowerCase().equals("no"))
                    break;
            }
            else {
                System.out.println("Count of updated rows: "+check);
                break;
            }
        }
    }
    
    private static void deleteTeam() throws MalformedURLException, IOException{
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Write id to delete row");
            String temp = br.readLine();
            String parametrs = "delete from teams where id=";
            int check = teamService.getTeamWebServicePort().deleteTeam(parametrs+temp);
            if (check == 0){
                System.out.println("There is no row with this id or something happens....");
                System.out.println("do you want continue?");
                System.out.println("yes/no");
                temp = br.readLine();
                if (temp.toLowerCase().equals("no"))
                    break;
            }
            else {
                System.out.println("Count of deleted rows: "+check);
                break;
            }
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
