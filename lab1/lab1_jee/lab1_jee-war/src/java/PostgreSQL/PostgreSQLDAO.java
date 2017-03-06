package PostgreSQL;

import Teams.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {
    public List<Team> getTeams(Connection conn, String parametrs) {
    List<Team> teams = new ArrayList<>();
        try {
        Statement stmt = conn.createStatement();
        String sql = "select * from teams";
        if (!parametrs.isEmpty()){
            if (!parametrs.equals("all")){
                sql += " where "+parametrs; 
            }
        }
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            String city = rs.getString("city");
            String stadium = rs.getString("stadium");
            int foundation = rs.getInt("foundation");
            int cups = rs.getInt("cups");
            Team team = new Team(name, city,stadium,foundation, cups);
            teams.add(team);
        }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return teams;
    }   
}