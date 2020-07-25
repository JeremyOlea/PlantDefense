package gui;

import drivers.Session;
import handlers.GardenButtonHandler;
import handlers.PlantButtonHandler;
import handlers.SunButtonHandler;
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
    private ArrayList<ArrayList<Plant>> plot = new ArrayList<>();
    private Button[] plotButtons;
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
        pane.getChildren().add(getMenuButtons(game.getPlayer(), game));
        startSunsThread(game);
        display();
    }

    private void startSunsThread(Game game) throws FileNotFoundException {
        int sunsTimer = 5000;
        int periodBetweenEvents = 10000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ImageView sunImg = null;
                        try {
                            sunImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sun.gif")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Button sunObj = new Button("sun", sunImg);
                        sunObj.setStyle("-fx-background-color: transparent;");
                        sunObj.setFont(new Font(0));
                        sunObj.setOnAction(new SunButtonHandler(game.getPlayer()));
                        sunObj.setLayoutX((int)(Math.random() * WIDTH));
                        sunObj.setLayoutY((int)(Math.random() * HEIGHT));
                        fullScreen.getChildren().add(sunObj);
                    }
                });
            };
        }, sunsTimer, periodBetweenEvents);
    }

    /*
    * Creates the menu buttons for selecting plants and sets the button handlers to the cards and the plot tiles
    */
    private Node getMenuButtons(Player player, Game game) throws FileNotFoundException {
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
        plotButtons = new Button[NUM_PLOT_ROWS * NUM_PLOT_COLS];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(65, 60, 50, 0));
        grid.setAlignment(Pos.BOTTOM_RIGHT);

        int counter = 0;
        for(int i = 0; i < NUM_PLOT_ROWS; i++) {
            for(int j = 0; j < NUM_PLOT_COLS; j++) {
                for(int k = 0; k < NUM_PLOT_ROWS*NUM_PLOT_COLS; k++) {
                    plotButtons[k] = new Button(i + "," + j);
                    plotButtons[k].setStyle("-fx-background-color: transparent;");
                    plotButtons[k].setFont(new Font(0));
                    plotButtons[k].setPrefSize(100, 106);
                    plotButtons[k].setOnAction(new GardenButtonHandler(player, game));
                }
                grid.add(plotButtons[counter], j, i);
                counter++;
            }
        }

        root.getChildren().add(box);
        root.getChildren().add(grid);
        return root;
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

    public static void createSunCounter(Player player) {
        Button sunCounter = new Button("sun counter");
        sunCounter.setText("" + player.getSuns());
        sunCounter.setStyle("-fx-background-color: transparent;");
        sunCounter.setPrefSize(170, 70);
        sunCounter.setFont(new Font("Arial", 38));
    }
}
