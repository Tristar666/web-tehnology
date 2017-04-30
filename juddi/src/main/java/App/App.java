package App;

public class App {

    public static void main(String[] args) {
        SimplePublishPortable spp = new SimplePublishPortable();
        spp.publish();
        SimpleBrowse sb = new SimpleBrowse();
        String[] strs ={"TeamsService"};
        sb.Browse(strs);
    }
}
