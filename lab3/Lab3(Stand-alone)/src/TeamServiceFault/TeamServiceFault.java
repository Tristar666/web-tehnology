package TeamServiceFault;

public class TeamServiceFault {
    private static final String DEFAULT_MESSAGE = "No data with this id";

    protected String message;
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
        
    public static TeamServiceFault defaultInstance() {
        TeamServiceFault fault = new TeamServiceFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}    
