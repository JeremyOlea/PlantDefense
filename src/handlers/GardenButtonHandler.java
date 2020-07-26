package handlers;

import gui.GameScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import logic.Game;
import logic.Plant;
import logic.Player;

import java.io.FileNotFoundException;

public class GardenButtonHandler implements EventHandler<ActionEvent> {
    private Player player;
    private Game game;

    public GardenButtonHandler(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void handle(ActionEvent event) {
        if(player.getHasSelected()) {
            Button source = (Button) event.getSource();
            String[] coordinates = source.getText().split(",");
            int row = Integer.parseInt(coordinates[0]);
            int col = Integer.parseInt(coordinates[1]);
            if (game.isFreePosition(row, col)) {
                System.out.println(player.getSelected() + " placed at " + source.getText());
                try {
                    Plant plant = new Plant(player.getSelected());
                    player.setHasSelected(false);
                    plant.setRow(row);
                    plant.setCol(col);
                    game.setPlant(plant);

                    plant.setxPosition(source.getLayoutX() + 25);
                    plant.setyPosition(source.getLayoutY() + 150);
                    plant.setImagePosition();
                    plant.setBulletXPosition(source.getLayoutX() + 65);
                    plant.setBulletYPosition(source.getLayoutY() + 155);
                    plant.setBulletStartPosition(source.getLayoutX() + 65);

                    GameScene.getScreenPane().getChildren().addAll(plant.getPlantImg());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(player.getSelected());
            }
        }
    }
}
