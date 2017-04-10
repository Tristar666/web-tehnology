package TeamResource;

public class DataError extends Exception {

    private static final long serialVersionUID = 6647544772732631047L;
    
    private String message;
    
    @Override
    public String getMessage(){
        return this.message;
    }
    
    public DataError(){
        super("Omg something happened...");
    }
    
    public DataError(String message) {
        super(message);
        this.message = message;
    }
    
    public DataError(String message, Throwable th){
        super(message,th);
        this.message = message;
    }
    
}

