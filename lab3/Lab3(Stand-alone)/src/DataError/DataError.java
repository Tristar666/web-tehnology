package DataError;

import TeamServiceFault.TeamServiceFault;
import javax.xml.ws.WebFault;

@WebFault(faultBean = "TeamServiceFault.TeamServiceFault")
public class DataError extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;

    private final TeamServiceFault fault;

    public DataError(String message, TeamServiceFault fault) {
        super(message);
        this.fault = fault;
    }
    
    public DataError(String message, TeamServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    
    public TeamServiceFault getFaultInfo() {
        return fault;
    }
}
