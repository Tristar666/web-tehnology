package TeamResource;

import PostgreSQL.*;
import Teams.Team;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import org.codehaus.jettison.json.*;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TeamResource {

    private boolean auth(String login, String pass){
        return login.equals("lol") && pass.equals("lol");
    }
    
    @GET
    public List<Team> getTeams(@QueryParam("name") String jsonString,@HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException {
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseSelect(jsonString);
        List<Team> teams = dao.getTeams(jsonString);
        return teams;
    }
    
    @PUT
    public String insertTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseInsert(jsonString);
        String res = dao.insertTeam(jsonString);
        if (Integer.parseInt(res) < 0)
            throw new DataError("Insert problem...");
        return res;
    }
    
    @DELETE
    public String deleteTeam(@QueryParam("id") String id,@HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.validateInt(id))
            throw new DataError("Wrong data type!!!");
        String res = dao.deleteTeam(id);
        if (Integer.parseInt(res) < 0)
            throw new DataError("Delete problem...");
        if (Integer.parseInt(res) == 0)
                throw new DataError("No data with this id...");
        return res;
    }
    
    @POST
    public String updateTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseUpdate(jsonString);
        String res = dao.updateTeam(jsonString);
        if (Integer.parseInt(res) <= 0)
            throw new DataError("Update problem...");
        return res;
    }
}
