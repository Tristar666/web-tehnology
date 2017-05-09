package TeamResource;

import PostgreSQL.*;
import Teams.Team;
import java.sql.Connection;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.*;

@RequestScoped
@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
            System.out.println("Error");
            Logger.getLogger(TeamResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("NOT Error");
        return result;
    }
    
    private boolean auth(String login, String pass){
        return login.equals("lol") && pass.equals("lol");
    }
    
    @PUT
    public String insertTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException, NamingException, InterruptedException{
        if (!auth(login,pass)){
            DataError de = new DataError(Response.Status.FORBIDDEN);
            de.setMessage("Wrong password or login!!!");
            throw de;
        }        
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseInsert(jsonString);
        String res = dao.insertTeam(jsonString,getConnection());
        if (Integer.parseInt(res) < 0){
            DataError de = new DataError(Response.Status.INTERNAL_SERVER_ERROR);
            de.setMessage("Insert problem...");
            throw de;
        }
        return res;
    }
    
    @DELETE
    public String deleteTeam(@QueryParam("id") String id,@HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, NamingException{
       if (!auth(login,pass)){
            DataError de = new DataError(Response.Status.FORBIDDEN);
            de.setMessage("Wrong password or login!!!");
            throw de;
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.validateInt(id)){
            DataError de = new DataError(Response.Status.BAD_REQUEST);
            de.setMessage("Wrong data type!!!");
            throw de;
        }           
        String res = dao.deleteTeam(id,getConnection());
        if (Integer.parseInt(res) < 0){
            DataError de = new DataError(Response.Status.INTERNAL_SERVER_ERROR);
            de.setMessage("Delete problem...");
            throw de;
        }           
        if (Integer.parseInt(res) == 0){
            DataError de = new DataError(Response.Status.NOT_FOUND);
            de.setMessage("No data with this id...");
            throw de;
        }                           
        return res;
    }
    
    @POST
    public String updateTeam(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException, NamingException{
        if (!auth(login,pass)){
            DataError de = new DataError(Response.Status.FORBIDDEN);
            de.setMessage("Wrong password or login!!!");
            throw de;
        }
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        jsonString = dao.parseUpdate(jsonString);
        String res = dao.updateTeam(jsonString,getConnection());
        if (Integer.parseInt(res) <= 0){
            DataError de = new DataError(Response.Status.INTERNAL_SERVER_ERROR);
            de.setMessage("Update problem...");
            throw de;
        }           
        return res;
    }
     
    @GET
    public Response getTeams(@QueryParam("name") String jsonString, @HeaderParam("Login") String login, @HeaderParam("Pass") String pass) throws DataError, JSONException, NamingException {
        if (!auth(login,pass)){
            DataError de = new DataError(Response.Status.FORBIDDEN);
            de.setMessage("Wrong password or login!!!");
            throw de;
        }       
        jsonString = jsonString.replaceAll("\\+", " "); //encoding problem
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (!dao.checkSelect(jsonString)){
            DataError de = new DataError(Response.Status.BAD_REQUEST);
            de.setMessage("Wrond data type or filed!!!");
            throw de;
        }                       
        jsonString = dao.parseSelect(jsonString);
        List<Team> teams = dao.getTeams(jsonString,getConnection());
        GenericEntity<List<Team>> entity = new GenericEntity<List<Team>>(teams) {};
        return Response.ok(entity).build();
    }
}
