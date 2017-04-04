package PostgreSQL;

import Teams.*;
import connectionUtils.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {
    
    private boolean check_field(String str){
        switch(str){
            case "city": return true;
            case "name": return true;
            case "stadium": return true;
            case "cups": return true;
            case "foundation": return true;
            default: return false;
        }
    }
     
    private boolean checkMultiple(String str){
        switch(str){
            case "and": return true;
            case "or": return true;
            default: return false;
        }
    }
    
    public boolean validateStringSelect(String str){
        String tempColumn="";
        String tempData="";
        int check = 0;
        for (int i=0;i<str.length();++i){
            switch(check){    
                case 0: //check column name
                    switch(str.charAt(i)){
                        case '=': check = 1; if (!check_field(tempColumn)) return false; break;
                        case '>': check = 1; if (!check_field(tempColumn)) return false; break;
                        case '<': check = 1; if (!check_field(tempColumn)) return false; break;
                        default: tempColumn += str.charAt(i);
                    }
                    break;
                case 1: // check data after =,>,<
                    switch(str.charAt(i)){
                        case ' ': 
                            check = 2;
                            if (tempColumn.equals("foundation") || tempColumn.equals("cups"))
                                if (!validateInt(tempData)) 
                                    return false; 
                            tempData="";
                            tempColumn="";
                            break;
                        default: tempData += str.charAt(i);
                    }
                    break;
                case 2: //check for and or for multiplie select
                    switch(str.charAt(i)){
                        case ' ':
                            check = 0;
                            if (!checkMultiple(tempColumn))
                                return false;
                            tempColumn="";
                            break;
                        default: tempColumn += str.charAt(i);
                    }
                    break;
            }
        }
        if (tempColumn.isEmpty() || tempData.isEmpty())
            return false;
        return true;
    }
    
    public String parseSelect(String temp){
        String parametr="";
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
        return parametr;
    }
    
    public boolean validateInt(String data){
        for (int i=0;i<data.length();++i){
            if ((int)data.charAt(i) < 48 || (int)data.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }
    
    public boolean checkInsert(String str){
        String[] result = str.split(",");
        for (int i=3;i<=4;++i) {
           if (!validateInt(result[i].substring(1,result[i].length()-1))){
               return false;
           }
        }
        return true;
    }
    
    public boolean checkUpdate(String str){
        String[] str1 = str.split("\\s+, ");
        for (int i=0;i<str1.length; i+=1){
            String[] temp = str1[i].split("=");
            if (temp.length < 2)
                return false;
            if (!check_field(temp[0])){
                return false;
            }
            if (temp[0].equals("cups") || temp[0].equals("foundation")){
                if (!validateInt(temp[1].substring(1, temp[1].length()-1)))
                    return false;
            }
        }
        return true;
    }
    
    public int deleteTeam(String parametrs){
        int res = -1;
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from teams where id="+parametrs);
            res = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return res;
    }
    
    public int updateTeam(String parametrs, String id){
        int res = -1;
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("update teams set "+parametrs+" where id='"+"'");
            res = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return res;
    }
    
    public int insertTeam(String parametrs){
        int key = -1;
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into teams (name,city,stadium,cups,foundation) values("+parametrs+")",Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return key;
    }
    
    public List<Team> getTeams(String parametrs) {
    List<Team> teams = new ArrayList<>();
    Connection connection = null;
    try {
        connection = ConnectionUtil.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "select * from teams";
        if (!parametrs.isEmpty()){
            if (!parametrs.equals("all")){
                sql += " where "+parametrs; 
            }
        }
        
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