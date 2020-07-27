package handlers;

import gui.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import logic.Game;
import logic.Player;

public class SunButtonHandler implements EventHandler<ActionEvent> {
    private Game game;

    public SunButtonHandler(Game game) {
        this.game = game;
    }

    @Override
    public void handle(ActionEvent event) {
        Button source = (Button) event.getSource();
//        source.setDisable(true);
//        source.setStyle("-fx-opacity: 0.0;");
        game.getPlayer().incSuns();
        GameScene.getScreenPane().getChildren().remove(source);
        game.getSunCounterText().setText("" + game.getPlayer().getSuns());
    }
}
