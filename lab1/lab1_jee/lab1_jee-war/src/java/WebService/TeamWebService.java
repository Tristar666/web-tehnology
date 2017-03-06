package WebService;

import PostgreSQL.*;
import Teams.Team;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.sql.DataSource;

@WebService(serviceName = "TeamService")
public class TeamWebService {
    @Resource(lookup = "jdbc/postgres")
    private DataSource dataSource;

    @WebMethod(operationName = "getAllTeams")
    public List<Team> getAllTeams(String parametr) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Team> teams = dao.getTeams(getConnection(),parametr);
        return teams;
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TeamWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
