
package webservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "DataError", targetNamespace = "http://WebService/")
public class DataError
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private TeamServiceFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DataError(String message, TeamServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public DataError(String message, TeamServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: webservice.TeamServiceFault
     */
    public TeamServiceFault getFaultInfo() {
        return faultInfo;
    }

}
