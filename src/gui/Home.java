package gui;
import handlers.PlayGameHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import drivers.Session;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.InputStream;

public class Home extends BaseScene {
    public Home(Session session) {
        super(session);
    }

    public void setup() throws Exception {
        Button play = new Button("Play");
//        Button htpBtn = new Button("How To Play");
        play.setPrefSize(350,100);
//        htpBtn.setPrefSize(350,100);

        //set event handlers
        play.setOnAction(new PlayGameHandler(getSession()));

        StackPane pane = new StackPane();
        //background
        Image img = new Image("file:\\..\\images\\homescreen.jpg");
        ImageView background = new ImageView(img);
        background.setFitHeight(750);
        background.setFitWidth(1400);
        pane.getChildren().add(background);

        //add vertical box with the buttons to pane
        VBox box = new VBox();
        box.getChildren().add(play);
//        box.getChildren().add(htpBtn);
        box.setAlignment(Pos.CENTER);
        pane.getChildren().add(box);

        setScene(new Scene(pane, 1400, 750));
        display();
    }
}