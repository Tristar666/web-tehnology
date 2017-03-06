
package webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTeams_QNAME = new QName("http://WebService/", "getTeams");
    private final static QName _GetTeamsResponse_QNAME = new QName("http://WebService/", "getTeamsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTeamsResponse }
     * 
     */
    public GetTeamsResponse createGetTeamsResponse() {
        return new GetTeamsResponse();
    }

    /**
     * Create an instance of {@link GetTeams }
     * 
     */
    public GetTeams createGetTeams() {
        return new GetTeams();
    }

    /**
     * Create an instance of {@link Team }
     * 
     */
    public Team createTeam() {
        return new Team();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "getTeams")
    public JAXBElement<GetTeams> createGetTeams(GetTeams value) {
        return new JAXBElement<GetTeams>(_GetTeams_QNAME, GetTeams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "getTeamsResponse")
    public JAXBElement<GetTeamsResponse> createGetTeamsResponse(GetTeamsResponse value) {
        return new JAXBElement<GetTeamsResponse>(_GetTeamsResponse_QNAME, GetTeamsResponse.class, null, value);
    }

}
