package gui;

import drivers.Session;
import handlers.GardenButtonHandler;
import handlers.PlantButtonHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import logic.Game;
import logic.Plant;
import logic.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameScene extends BaseScene {
    private int NUM_PLOT_ROWS = 5;
    private int NUM_PLOT_COLS = 9;
    private int WIDTH = 1220;
    private int HEIGHT = 720;
    private int sunsTimer = 5000;
    private ArrayList<ArrayList<Plant>> plot = new ArrayList<>();
    private static StackPane pane = new StackPane();
    private static Pane fullScreen = new Pane(pane);

    public GameScene(Session session) {
        super(session);
    }

    @Override
    public void setup() throws Exception {
        //background
        Image img = new Image("file:\\..\\images\\Garden.PNG");
        ImageView background = new ImageView(img);
        background.setFitHeight(750);
        background.setFitWidth(1400);
        pane.getChildren().add(background);

        Player player = new Player();
        initializePlot();
        Game game = new Game(player, plot);

        setScene(new Scene(fullScreen, WIDTH, HEIGHT));

        VBox root = new VBox();
        HBox box = new HBox();

        //create menu (plant selector)
        for(int col = 0; col < 5; col++) {
            ImageView plant = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Plant" + col +".jpg")));
            Button plantCards = new Button("p"+col, plant);
            plantCards.setFont(new Font(0));
            plantCards.setStyle("-fx-background-color: transparent;");
            plantCards.setOnAction(new PlantButtonHandler(player));
            box.getChildren().add(col, plantCards);
        }

        //Set button handlers to the garden tiles
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(65, 60, 50, 0));
        grid.setAlignment(Pos.BOTTOM_RIGHT);
        Button plotButtons = new Button();
        for(int i = 0; i < NUM_PLOT_ROWS; i++) {
            for(int j = 0; j < NUM_PLOT_COLS; j++) {
                for(int k = 0; k < NUM_PLOT_ROWS*NUM_PLOT_COLS; k++) {
                    plotButtons = new Button(i + "," + j);
                    plotButtons.setStyle("-fx-background-color: transparent;");
                    plotButtons.setFont(new Font(0));
                    plotButtons.setPrefSize(100, 106);
                    plotButtons.setOnAction(new GardenButtonHandler(player, game));
                }
                grid.add(plotButtons, j, i);
            }
        }

        root.getChildren().add(box);
        root.getChildren().add(grid);

        pane.getChildren().add(root);

        int maximumSunsOnScreen = 20;
        for(int i = 0; i < maximumSunsOnScreen; i++) {
            ImageView sunImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sun.gif")));
            Button sunObj = new Button("sun", sunImg);
            sunObj.setStyle("-fx-background-color: transparent;");
            sunObj.setFont(new Font(0));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fullScreen.getChildren().add(sunObj);
                        }
                    });
                };
            }, sunsTimer);

            sunObj.setLayoutX((int)(Math.random() * WIDTH));
            sunObj.setLayoutY((int)(Math.random() * HEIGHT));
            sunsTimer += 5000;
        }

        display();
    }
    /*
    * initialized the 2D array for the plot tiles
    */
    private void initializePlot() {
        for(int i = 0; i < NUM_PLOT_ROWS; i++) {
            ArrayList<Plant> arr = new ArrayList<>();
            for(int j = 0; j < NUM_PLOT_COLS; j++) {
                arr.add(new Plant("none"));
            }
            plot.add(arr);
        }
    }
}
