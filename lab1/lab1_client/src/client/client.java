package client;

import java.io.*;
import webservice.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.lang.Boolean;

public class client {
    public static void main(String[] args) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/TeamsService?wsdl");
        TeamService teamService = new TeamService(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        System.out.println("For selecting all rows print all or nothing select, other way print column name like name=Arsenal to select information about club");
        while (true){
            System.out.println("For exit write exit");
            String parametr = "";
            String temp = br.readLine();
            int check=0;
            if (temp.equals("exit")){
                System.exit(0);
            }
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
            //System.out.println(parametr);
            List<Team> teams = teamService.getTeamWebServicePort().getTeams(parametr);
            for (Team team : teams) {
                System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
            }
            System.out.println("Total teams: " + teams.size());
        }
    }
}
