package WebService;

import DataError.DataError;
import PostgreSQL.*;
import TeamServiceFault.TeamServiceFault;
import Teams.Team;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "TeamService")
public class TeamWebService {
    @WebMethod(operationName = "getTeams")
    public List<Team> getTeams(String parametrs) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (parametrs.isEmpty() || !dao.validateStringSelect(parametrs)){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Empty string or parametr error!", fault);
        } 
        List<Team> teams = dao.getTeams(dao.parseSelect(parametrs));
        return teams;
    }
    
    @WebMethod(operationName = "insertTeam")
    public int insertTeam(String parametrs) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.checkInsert(parametrs)){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Error in parametr!", fault);
        }
        int res = dao.insertTeam(parametrs);
        if (res < 0){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("No row inserted!", fault);
        }
        return res;
    }
    
    @WebMethod(operationName = "deleteTeam")
    public int deleteTeam(String parametrs) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.validateInt(parametrs)){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Error in parametr!", fault);
        }
        int res = dao.deleteTeam(parametrs);
        if (res == 0){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("No row deleted!", fault);
        }
        return res;
    }
    
    @WebMethod(operationName = "updateTeam")
    public int updateTeam(String parametrs,String id) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.checkUpdate(parametrs) || !dao.validateInt(id)){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Error in parametr!", fault);
        }
        int res = dao.updateTeam(parametrs,id);
        if (res == 0){
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("No row updated!", fault);
        }
        return res;
    }
}