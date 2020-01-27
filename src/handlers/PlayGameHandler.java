package handlers;

import drivers.Session;
import gui.GameScene;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PlayGameHandler implements EventHandler<ActionEvent> {
    private Session session;

    public PlayGameHandler(Session session) {
        this.session = session;
    }

    @Override
    public void handle(ActionEvent event) {
        GameScene scene = new GameScene(session);
        System.out.println("Play pressed");
        try {
            scene.setup();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
