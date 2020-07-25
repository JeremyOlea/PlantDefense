package handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Player;

public class SunButtonHandler implements EventHandler<ActionEvent> {
    private Player player;

    public SunButtonHandler(Player player) {
        this.player = player;
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
