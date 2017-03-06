package Teams;
public class Team {
    private String name;
    private int year;
    private String city;
    private String stadium;
    private int cups;
    
    public Team(){}
    
    public Team(String name, String city,String stadium, int year, int cups){
    
    this.name = name;
    this.year = year;
    this.city=city;
    this.stadium = stadium;
    this.cups = cups;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the stadium
     */
    public String getStadium() {
        return stadium;
    }

    /**
     * @param stadium the stadium to set
     */
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    /**
     * @return the cups
     */
    public int getCups() {
        return cups;
    }

    /**
     * @param cups the cups to set
     */
    public void setCups(int cups) {
        this.cups = cups;
    }
    
    public String ToString(){
        return "Team {"+name+", city: "+city+", stadium: "+stadium+", foundation: "+year+", total cups: "+cups+'}';
    }
}
