package TeamResource;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Dmitriy
 */
@Provider
@Singleton
public class DataErrorMapper implements ExceptionMapper<DataError> {
 
    @Override
    public Response toResponse(DataError e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();    }
}
