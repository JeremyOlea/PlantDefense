package gui;
import javafx.scene.Scene;
import drivers.Session;

public class Home extends BaseScene {
    public Home(Session session) {
        super(session);
    }

    public void setup() extends Exception {
        Button play = new Button("Play");
        Button credits = new Button("Credits");
        play.setPrefSize(350,100);
    }
}