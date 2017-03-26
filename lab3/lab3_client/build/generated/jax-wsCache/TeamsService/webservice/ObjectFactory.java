
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
    private final static QName _UpdateTeamResponse_QNAME = new QName("http://WebService/", "updateTeamResponse");
    private final static QName _InsertTeamResponse_QNAME = new QName("http://WebService/", "insertTeamResponse");
    private final static QName _UpdateTeam_QNAME = new QName("http://WebService/", "updateTeam");
    private final static QName _DeleteTeam_QNAME = new QName("http://WebService/", "deleteTeam");
    private final static QName _DataError_QNAME = new QName("http://WebService/", "DataError");
    private final static QName _DeleteTeamResponse_QNAME = new QName("http://WebService/", "deleteTeamResponse");
    private final static QName _GetTeamsResponse_QNAME = new QName("http://WebService/", "getTeamsResponse");
    private final static QName _InsertTeam_QNAME = new QName("http://WebService/", "insertTeam");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteTeamResponse }
     * 
     */
    public DeleteTeamResponse createDeleteTeamResponse() {
        return new DeleteTeamResponse();
    }

    /**
     * Create an instance of {@link GetTeamsResponse }
     * 
     */
    public GetTeamsResponse createGetTeamsResponse() {
        return new GetTeamsResponse();
    }

    /**
     * Create an instance of {@link InsertTeam }
     * 
     */
    public InsertTeam createInsertTeam() {
        return new InsertTeam();
    }

    /**
     * Create an instance of {@link TeamServiceFault }
     * 
     */
    public TeamServiceFault createTeamServiceFault() {
        return new TeamServiceFault();
    }

    /**
     * Create an instance of {@link DeleteTeam }
     * 
     */
    public DeleteTeam createDeleteTeam() {
        return new DeleteTeam();
    }

    /**
     * Create an instance of {@link UpdateTeam }
     * 
     */
    public UpdateTeam createUpdateTeam() {
        return new UpdateTeam();
    }

    /**
     * Create an instance of {@link InsertTeamResponse }
     * 
     */
    public InsertTeamResponse createInsertTeamResponse() {
        return new InsertTeamResponse();
    }

    /**
     * Create an instance of {@link UpdateTeamResponse }
     * 
     */
    public UpdateTeamResponse createUpdateTeamResponse() {
        return new UpdateTeamResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTeamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "updateTeamResponse")
    public JAXBElement<UpdateTeamResponse> createUpdateTeamResponse(UpdateTeamResponse value) {
        return new JAXBElement<UpdateTeamResponse>(_UpdateTeamResponse_QNAME, UpdateTeamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertTeamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "insertTeamResponse")
    public JAXBElement<InsertTeamResponse> createInsertTeamResponse(InsertTeamResponse value) {
        return new JAXBElement<InsertTeamResponse>(_InsertTeamResponse_QNAME, InsertTeamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTeam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "updateTeam")
    public JAXBElement<UpdateTeam> createUpdateTeam(UpdateTeam value) {
        return new JAXBElement<UpdateTeam>(_UpdateTeam_QNAME, UpdateTeam.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTeam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "deleteTeam")
    public JAXBElement<DeleteTeam> createDeleteTeam(DeleteTeam value) {
        return new JAXBElement<DeleteTeam>(_DeleteTeam_QNAME, DeleteTeam.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TeamServiceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "DataError")
    public JAXBElement<TeamServiceFault> createDataError(TeamServiceFault value) {
        return new JAXBElement<TeamServiceFault>(_DataError_QNAME, TeamServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTeamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "deleteTeamResponse")
    public JAXBElement<DeleteTeamResponse> createDeleteTeamResponse(DeleteTeamResponse value) {
        return new JAXBElement<DeleteTeamResponse>(_DeleteTeamResponse_QNAME, DeleteTeamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "getTeamsResponse")
    public JAXBElement<GetTeamsResponse> createGetTeamsResponse(GetTeamsResponse value) {
        return new JAXBElement<GetTeamsResponse>(_GetTeamsResponse_QNAME, GetTeamsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertTeam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "insertTeam")
    public JAXBElement<InsertTeam> createInsertTeam(InsertTeam value) {
        return new JAXBElement<InsertTeam>(_InsertTeam_QNAME, InsertTeam.class, null, value);
    }

}
