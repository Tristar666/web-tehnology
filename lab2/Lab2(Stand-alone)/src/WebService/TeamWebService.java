package WebService;

import PostgreSQL.*;
import Teams.Team;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
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
    public List<Team> getTeams(String parametrs) {
        if (!auth()){
            return null;
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Team> teams = dao.getTeams(parametrs);
        return teams;
    }
    
    @WebMethod(operationName = "insertTeam")
    public int insertTeam(String parametrs){
        if (!auth()){
            return -1;
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.insertTeam(parametrs);
    }
    
    @WebMethod(operationName = "deleteTeam")
    public int deleteTeam(String parametrs){
        if (!auth()){
            return -1;
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deleteTeam(parametrs);
    }
    
    @WebMethod(operationName = "updateTeam")
    public int updateTeam(String parametrs){
        if (!auth()){
            return -1;
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updateTeam(parametrs);
    }
}
