/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1_jee_client;

import webservice.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.*;


public class Client {
    public static void main(String[] args) throws NoSuchMethodError, MalformedURLException, IOException, InterruptedException {
        webservice.TeamService service = new webservice.TeamService();
        webservice.TeamWebService port = service.getTeamWebServicePort();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Table 'Teams' consist of 5 columns: name, city, stadium, cups and foundation");
        System.out.println("For selection all rows print all or nothing select, other way print column name like name=Arsenal to select information about club");
        System.out.println("For multiply selection write column name and <,> or = and value, after thar write 'and' and another columnt name and expression");
        System.out.println("Example, 'city=London and cups>3 and foundation>1900'");    
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
            javax.xml.ws.AsyncHandler<webservice.GetAllTeamsResponse> asyncHandler = new javax.xml.ws.AsyncHandler<webservice.GetAllTeamsResponse>() {
                public void handleResponse(javax.xml.ws.Response<webservice.GetAllTeamsResponse> response) {
                    try {
                        List<Team> teams = response.get().getReturn();
                        for (Team team : teams) {
                        System.out.println("name: " + team.getName() +", city: " + team.getCity() + ", stadium: " + team.getStadium() + ", year of foudation: " + team.getYear() + ", total cups: " +team.getCups());
                        }
                        System.out.println("Total teams: " + teams.size());
                    } catch(Exception ex) {
                        // TODO handle exception
                        
                    }
                }
            };
            java.util.concurrent.Future<? extends java.lang.Object> result = port.getAllTeamsAsync(parametr, asyncHandler);
            while(!result.isDone()) {
                // relax, nothing to do....
                Thread.sleep(1);
            }
        }
    }
}
    

