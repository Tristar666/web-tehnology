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
    
    public int deleteTeam(String parametrs){
        int res = -1;
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(parametrs);
            res = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return res;
    }
    
    public int updateTeam(String parametrs){
        int res = -1;
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(parametrs);
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
            PreparedStatement stmt = conn.prepareStatement(parametrs,Statement.RETURN_GENERATED_KEYS);
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