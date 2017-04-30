package TeamResource;

import PostgreSQL.*;
import Teams.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.*;

@RequestScoped
@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TeamResource {

    /*
    
    getConnection() return Connection
    
    */
    
    private Connection getConnection() throws NamingException {
        Connection result = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/postgres");
            result = ds.getConnection();
        } catch (SQLException ex) {
            System.out.println("Error LOL");
            Logger.getLogger(TeamResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("NOT Error LOL");
        return result;
    }
    
    private boolean auth(String login, String pass){
        return login.equals("lol") && pass.equals("lol");
    }
    
    
    @PUT
    public String insertTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException, NamingException{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseInsert(jsonString);
        String res = dao.insertTeam(jsonString,getConnection());
        if (Integer.parseInt(res) < 0)
            throw new DataError("Insert problem...");
        return res;
    }
    
    @DELETE
    public String deleteTeam(@QueryParam("id") String id,@HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, NamingException{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.validateInt(id))
            throw new DataError("Wrong data type!!!");
        String res = dao.deleteTeam(id,getConnection());
        if (Integer.parseInt(res) < 0)
            throw new DataError("Delete problem...");
        if (Integer.parseInt(res) == 0)
                throw new DataError("No data with this id...");
        return res;
    }
    
    @POST
    public String updateTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException, NamingException{
        if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseUpdate(jsonString);
        String res = dao.updateTeam(jsonString,getConnection());
        if (Integer.parseInt(res) <= 0)
            throw new DataError("Update problem...");
        return res;
    }
    
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    @GET
    public void getTeams(@Suspended final AsyncResponse asyncResponse, @QueryParam(value = "name") final String jsonString) {        
        System.out.println(jsonString);
        executorService.submit(new Runnable() {               
            @Override
            public void run() {
                try {
                    asyncResponse.resume(doGetTeams(jsonString));
                    /*System.out.println(jsonString);
                    asyncResponse.resume(Response.ok(jsonString).build());*/
                } catch (DataError ex) {
                    Logger.getLogger(TeamResource.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(TeamResource.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(TeamResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
    }

    private Response doGetTeams(String jsonString) throws DataError, JSONException, NamingException {
        /*if (!auth(login,pass))
            throw new DataError("Wrong password or login!!!");*/
        List<Team> teams = null;
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.checkSelect(jsonString))
            throw new DataError("Wrond data type or filed!!!");
        jsonString = dao.parseSelect(jsonString);
        teams = dao.getTeams(jsonString,getConnection());
        GenericEntity<List<Team>> entity = new GenericEntity<List<Team>>(teams) {};
        return Response.ok(entity).build();
        //return teams;
    }

}
