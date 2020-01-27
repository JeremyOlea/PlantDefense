package handlers;

import drivers.Session;
import gui.GameScene;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PlayGameHandler implements EventHandler {
    private Session session;

    public PlayGameHandler(Session session) {
        this.session = session;
    }

    @Override
    public void handle(Event event) {
        GameScene scene = new GameScene(session);
        try {
            scene.setup();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
