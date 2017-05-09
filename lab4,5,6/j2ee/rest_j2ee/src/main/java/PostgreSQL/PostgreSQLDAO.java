package PostgreSQL;

import Teams.*;
import TeamResource.DataError;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class PostgreSQLDAO {
    
    private boolean check_field(String str){
        switch(str){
            case "city": return true;
            case "id": return true;
            case "name": return true;
            case "stadium": return true;
            case "cups": return true;
            case "foundation": return true;
            default: return false;
        }
    }
    
    public boolean checkSelect(String jsonString) throws JSONException{
        JSONObject jsonObj = new JSONObject(jsonString);
            Iterator<?> keys = jsonObj.keys();
            while(keys.hasNext() ) {
                String keyStr = (String)keys.next();
                if (!check_field(keyStr.substring(0,keyStr.length()-1)))
                    return false;
                Object keyvalue = jsonObj.get(keyStr);
                if (keyStr.equals("cups") || keyStr.equals("foundation")){
                    if (!validateInt((String) keyvalue))
                        return false;
                }
            }
        return true;
    }   
    
    public String parseSelect(String jsonString){
        String temp = "";
        try {            
            JSONObject jsonObj = new JSONObject(jsonString);
            Iterator<?> keys = jsonObj.keys();
            while(keys.hasNext() ) {
                String keyStr = (String)keys.next();                            
                Object keyvalue = jsonObj.get(keyStr);                
                temp += keyStr+"'"+keyvalue+"'"+"and ";
            }
            //System.out.println("In parse "+temp);
        } catch(Exception e){
            System.out.println("In DAO "+e.getMessage());
        }
        return temp.substring(0,temp.length()-4);
    }
    
    public boolean validateInt(String data){
        for (int i=0;i<data.length();++i){
            if ((int)data.charAt(i) < 48 || (int)data.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }
    
    public String parseInsert(String jsonString) throws DataError, JSONException{
        String temp = "";
        JSONObject jsonObj = new JSONObject(jsonString);
        Iterator<?> keys = jsonObj.keys();
        while(keys.hasNext() ) {
            String keyStr = (String)keys.next();
            if (!check_field(keyStr)){
                DataError de = new DataError(Response.Status.BAD_REQUEST);
                de.setMessage("Wrong field!!!");
                throw de;
            }           
            Object keyvalue = jsonObj.get(keyStr);
            if (keyStr.equals("cups") || keyStr.equals("foundation")){
                if (!validateInt((String) keyvalue)){
                    DataError de = new DataError(Response.Status.BAD_REQUEST);
                    de.setMessage("Wrong data type!!!");
                    throw de;
                }           
            }
            temp += "'"+keyvalue+"'"+",";
        }
        return temp.substring(0,temp.length()-1);
    }
    
    public String parseUpdate(String jsonString) throws JSONException, DataError {
        String temp = "";
        JSONObject jsonObj = new JSONObject(jsonString);
        Iterator<?> keys = jsonObj.keys();
        while(keys.hasNext() ) {
            String keyStr = (String)keys.next();
            if (!check_field(keyStr)){
                DataError de = new DataError(Response.Status.BAD_REQUEST);
                de.setMessage("Wrong field!!!");
                throw de;
            }           
            Object keyvalue = jsonObj.get(keyStr);
            if (keyStr.equals("cups") || keyStr.equals("foundation")){
                if (!validateInt((String) keyvalue)){
                    DataError de = new DataError(Response.Status.BAD_REQUEST);
                    de.setMessage("Wrong data type!!!");
                    throw de;
                }           
            }
            if (keyStr.equals("id")){
                if (!validateInt((String) keyvalue)){
                    DataError de = new DataError(Response.Status.BAD_REQUEST);
                    de.setMessage("Wrong data type!!!");
                    throw de;
                }           
                temp = temp.substring(0,temp.length()-1);
                temp += "where "+keyStr+"='"+keyvalue+"'";
            } else temp += keyStr+"='"+keyvalue+"'"+",";
        }
        return temp;
    }
   
    public String deleteTeam(String parametrs, Connection conn){
        int res = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from teams where id="+parametrs);
            res = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return Integer.toString(res);
    }
    
    public String updateTeam(String parametrs, Connection conn){
        int res = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("update teams set "+parametrs);
            res = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return Integer.toString(res);
    }
    
    public String insertTeam(String parametrs, Connection conn){
        int key = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into teams (name,city,stadium,cups,foundation) values("+parametrs+")",Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return Integer.toString(key);
    }
    
    public List<Team> getTeams(String parametrs, Connection conn) {
        List<Team> teams = new ArrayList<>();
        try {
            //System.out.println("In sql "+parametrs);
            Statement stmt = conn.createStatement();
            String sql = "select * from teams";
            if (!parametrs.isEmpty()){
                    sql += " where "+parametrs; 
            }
            //System.out.println("In sql2 "+sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String stadium = rs.getString("stadium");
                int foundation = rs.getInt("foundation");
                int cups = rs.getInt("cups");
                Team team = new Team(id,name, city,stadium,foundation, cups);
                teams.add(team);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    return teams;
    }   
}