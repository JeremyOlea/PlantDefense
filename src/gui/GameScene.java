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
import javafx.scene.text.Text;
import logic.Game;
import logic.Plant;
import logic.Player;
import logic.Zombie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameScene extends BaseScene {
    private int NUM_PLOT_ROWS = 5;
    private int NUM_PLOT_COLS = 9;
    private int WIDTH = 1400;
    private int HEIGHT = 720;
    private ArrayList<ArrayList<Plant>> plot = new ArrayList<>();
    private Button[] plotButtons;
    private Game game;
    private StackPane sunCounter;
    private Text sunCounterText;
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
        game = new Game(player, plot);

        for(int i = 0; i < game.getZombies().size(); i++) {
            fullScreen.getChildren().addAll(game.getZombies().get(i).getZombieImg());
        }
        pane.getChildren().add(getMenuButtons(game.getPlayer(), game));
        game.setSunCounterText(sunCounterText);
        startSunsThread(game);
        setScene(new Scene(fullScreen, WIDTH, HEIGHT));
        display();

        runGame(game);
    }

    private void runGame(Game game) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < NUM_PLOT_ROWS; i++) {
                            for(int j = 0; j < NUM_PLOT_COLS; j++) {
                                Plant plant = game.getPlant(i, j);
                                if(plant != null) {
//                                    System.out.println("Plant " + i + ", " + j + " has " + plant.getName());
                                    plant.moveBullet(game.getZombieRow(i));
                                }
                            }
                            for(int k = 0; k < game.getZombieRow(i).size(); k++) {
                                Zombie zombie = game.getZombieRow(i).get(k);
                                if(zombie.getRow() != 9 && zombie.getCol() != 9 && !zombie.getIsDead()) {
                                    Plant plant = game.getPlant(zombie.getRow(), zombie.getCol());
                                    if(plant != null) {
                                        zombie.stopZombie();
                                        plant.takeDamage(zombie.getDamage());
                                        if(plant.getName().equals("Potato Mine")) {
                                            zombie.takeDamage(plant.getDamage());
                                            plant.setHealth(0);
                                            try {
                                                potatoMineAnimation(plant);
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if(plant.getHealth() <= 0) {
                                            plant.setIsDead(true);
                                            removePlant(zombie.getRow(), zombie.getCol());
                                            zombie.startZombie();
                                        }
                                    }
                                }
                                if(zombie.getIsDead()) {
                                    game.getZombieRow(i).remove(zombie);
                                    fullScreen.getChildren().remove(zombie.getZombieImg());
                                }
                            }
                        }
                        if(!game.gameStillRunning()) {
                            System.out.println("You Won!");
                            timer.cancel();
                            timer.purge();
                            System.exit(0);
                        }
                    }
                });
            }
        }, 1, 100);
    }

    private void potatoMineAnimation(Plant plant) throws FileNotFoundException {
      ImageView explosion = new ImageView(new Image(new FileInputStream(("file:\\..\\images\\boom.gif"))));
      explosion.setLayoutX(plant.getPlantImg().getLayoutX());
      explosion.setLayoutY(plant.getPlantImg().getLayoutY());
      explosion.setFitHeight(95);
      explosion.setFitWidth(65);
      fullScreen.getChildren().add(explosion);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fullScreen.getChildren().remove(explosion);
                        timer.cancel();
                        timer.purge();
                    }
                });
            }
        }, 800);
    }

    private void removePlant(int row, int col) {
        Plant plant = game.getPlant(row, col);
        fullScreen.getChildren().remove(plant.getPlantImg());
        game.remove(row, col);
    }

    private void displayPlant(Plant plant) {
        plant.setImagePosition();
        ImageView plantImage = plant.getPlantImg();
        if(plantImage != null) {
            fullScreen.getChildren().addAll(plantImage);
        }
    }

    private void startSunsThread(Game game) {
        int sunsTimer = 5000;
        int periodBetweenEvents = 5000;
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
                        sunObj.setOnAction(new SunButtonHandler(game));
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

        int column = 0;
        box.getChildren().add(column, createSunCounter(player));
        column++;
        //create menu (plant selector)
        for(int plantNum = 0; plantNum < 5; plantNum++) {
            ImageView plant = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Plant" + plantNum +".jpg")));
            Button plantCards = new Button("p"+plantNum, plant);
            plantCards.setFont(new Font(0));
            plantCards.setStyle("-fx-background-color: transparent;");
            plantCards.setOnAction(new PlantButtonHandler(player));
            box.getChildren().add(column, plantCards);
            column++;
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
                    plotButtons[k].setPrefSize(110, 93);
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
                arr.add(null);
            }
            plot.add(arr);
        }
    }

    private Node createSunCounter(Player player) throws FileNotFoundException {
        sunCounter = new StackPane();
        ImageView img = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sunCounter.png")));
        sunCounterText = new Text(""+player.getSuns());
        sunCounterText.setFont(new Font("Arial", 38));
        sunCounter.getChildren().add(img);
        sunCounter.getChildren().add(sunCounterText);
        sunCounter.setAlignment(Pos.CENTER);
        sunCounter.setMargin(sunCounterText, new Insets(0, 0, 0, 50));
        return sunCounter;
    }

    public static Pane getScreenPane() {
        return fullScreen;
    }
}
