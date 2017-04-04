package WebService;

import DataError.DataError;
import PostgreSQL.*;
import TeamServiceFault.TeamServiceFault;
import Teams.Team;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(serviceName = "TeamService")
public class TeamWebService {
    @Resource
    WebServiceContext wsc;
    
    private boolean auth(){
        Map http_headers = (Map) wsc.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");
        
        String username = "";
        String password = "";

        if(userList!=null){
            //get username
            username = userList.get(0).toString();
        }

        if(passList!=null){
            //get password
            password = passList.get(0).toString();
        }
        return username.equals("lol") && password.equals("lol");
}
    
    @WebMethod(operationName = "getTeams")
    public List<Team> getTeams(String parametrs) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Team> teams = null;
        if (auth()){
            
            if (parametrs.isEmpty() || !dao.validateStringSelect(parametrs)){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("Empty string or parametr error!", fault);
            } 
            teams = dao.getTeams(dao.parseSelect(parametrs));
        } else {
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Auth error!!!", fault);
        }
        return teams;
    }
    
    @WebMethod(operationName = "insertTeam")
    public int insertTeam(String parametrs) throws DataError{
        int res=0;
        if (auth()){
            PostgreSQLDAO dao = new PostgreSQLDAO();
            if (!dao.checkInsert(parametrs)){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("Error in parametr!", fault);
            }
            res = dao.insertTeam(parametrs);
            if (res < 0){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("No row inserted!", fault);
            }
        } else {
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Auth error!!!", fault);
        }
        return res;
    }
    
    @WebMethod(operationName = "deleteTeam")
    public int deleteTeam(String parametrs) throws DataError{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        int res = 0;
        if (auth()){
            if (!dao.validateInt(parametrs)){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("Error in parametr!", fault);
            }
            res = dao.deleteTeam(parametrs);
            if (res == 0){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("No row deleted!", fault);
            }
        } else {
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Auth error!!!", fault);
        }
        return res;
    }
    
    @WebMethod(operationName = "updateTeam")
    public int updateTeam(String parametrs,String id) throws DataError{
        int res = 0;
        if (auth()){
            PostgreSQLDAO dao = new PostgreSQLDAO();
            if (!dao.checkUpdate(parametrs) || !dao.validateInt(id)){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("Error in parametr!", fault);
            }
            res = dao.updateTeam(parametrs,id);
            if (res == 0){
                TeamServiceFault fault = new TeamServiceFault();
                throw new DataError("No row updated!", fault);
            }
        } else {
            TeamServiceFault fault = new TeamServiceFault();
            throw new DataError("Auth error!!!", fault);
        }
        return res;
    }
}