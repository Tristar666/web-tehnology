package TeamResource;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.WebApplicationException;

public class DataError extends WebApplicationException  {

    private static final long serialVersionUID = 6647544772732631047L;
    
    private String message;
    
    @Override
    public String getMessage(){
        return this.message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public DataError(){
        super();
    }
    
    public DataError(Status st) {
        super(st);       
    }
    
    public DataError(Status st, Throwable th){
        super(th,st);        
    }
    
}