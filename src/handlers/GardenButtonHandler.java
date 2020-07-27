package handlers;

import gui.GameScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import logic.Game;
import logic.Plant;
import logic.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class GardenButtonHandler implements EventHandler<ActionEvent> {
    private Player player;
    private Game game;
    private int row;
    private int col;
    private Plant plant;

    public GardenButtonHandler(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void handle(ActionEvent event) {
        if(player.getHasSelected()) {
            Button source = (Button) event.getSource();
            String[] coordinates = source.getText().split(",");
            row = Integer.parseInt(coordinates[0]);
            col = Integer.parseInt(coordinates[1]);
            if (game.isFreePosition(row, col)) {
                System.out.println(player.getSelected() + " placed at " + source.getText());
                try {
                    plant = new Plant(player.getSelected());
                    if(player.getSuns() >= plant.getPrice()) {
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
                        plant.setBulletImagePosition();

                        GameScene.getScreenPane().getChildren().addAll(plant.getPlantImg());
                        player.decSuns(plant.getPrice());
                        game.getSunCounterText().setText(""+player.getSuns());
                        if (plant.getName().equals("SunFlower"))
                            startSunsThread(source.getLayoutX() + 25, source.getLayoutY() + 150);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startSunsThread(double x, double y) {
        int sunsTimer = 5000;
        int periodBetweenEvents = 5000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(game.getPlant(row, col) == null) {
                            timer.cancel();
                            timer.purge();
                        }
                        ImageView sunImg = null;
                        try {
                            sunImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sun.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Button sunObj = new Button("sun", sunImg);
                        sunObj.setStyle("-fx-background-color: transparent;");
                        sunObj.setFont(new Font(0));
                        sunObj.setOnAction(new SunButtonHandler(game));
                        sunObj.setLayoutX(x);
                        sunObj.setLayoutY(y);
                        GameScene.getScreenPane().getChildren().add(sunObj);
                    }
                });
            };
        }, sunsTimer, periodBetweenEvents);
    }
}
