package handlers;

import gui.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import logic.Player;

public class SunButtonHandler implements EventHandler<ActionEvent> {
    private Player player;

    public SunButtonHandler(Player player) {
        this.player = player;
    }

    @Override
    public void handle(ActionEvent event) {
        Button source = (Button) event.getSource();
        source.setDisable(true);
        source.setStyle("-fx-opacity: 0.0;");
        player.incSuns();
    }
}
