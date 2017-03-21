package WebService;

import PostgreSQL.*;
import Teams.Team;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService(serviceName = "TeamService")
public class TeamWebService {
    @WebMethod(operationName = "getTeams")
    public List<Team> getTeams(String parametrs) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Team> teams = dao.getTeams(parametrs);
        return teams;
    }
    
    @WebMethod(operationName = "insertTeam")
    public int insertTeam(String parametrs){
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.insertTeam(parametrs);
    }
    
    @WebMethod(operationName = "deleteTeam")
    public int deleteTeam(String parametrs){
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deleteTeam(parametrs);
    }
    
    @WebMethod(operationName = "updateTeam")
    public int updateTeam(String parametrs){
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updateTeam(parametrs);
    }
}
