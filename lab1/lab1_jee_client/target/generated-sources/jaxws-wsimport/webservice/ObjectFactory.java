
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

    private final static QName _GetAllTeams_QNAME = new QName("http://WebService/", "getAllTeams");
    private final static QName _GetAllTeamsResponse_QNAME = new QName("http://WebService/", "getAllTeamsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllTeamsResponse }
     * 
     */
    public GetAllTeamsResponse createGetAllTeamsResponse() {
        return new GetAllTeamsResponse();
    }

    /**
     * Create an instance of {@link GetAllTeams }
     * 
     */
    public GetAllTeams createGetAllTeams() {
        return new GetAllTeams();
    }

    /**
     * Create an instance of {@link Team }
     * 
     */
    public Team createTeam() {
        return new Team();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTeams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "getAllTeams")
    public JAXBElement<GetAllTeams> createGetAllTeams(GetAllTeams value) {
        return new JAXBElement<GetAllTeams>(_GetAllTeams_QNAME, GetAllTeams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTeamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "getAllTeamsResponse")
    public JAXBElement<GetAllTeamsResponse> createGetAllTeamsResponse(GetAllTeamsResponse value) {
        return new JAXBElement<GetAllTeamsResponse>(_GetAllTeamsResponse_QNAME, GetAllTeamsResponse.class, null, value);
    }

}
