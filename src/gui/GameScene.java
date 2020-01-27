package gui;

import drivers.Session;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameScene extends BaseScene {

    public GameScene(Session session) {
        super(session);
    }

    @Override
    public void setup() throws Exception {
        StackPane pane = new StackPane();
        //background
        Image img = new Image("file:\\..\\images\\Garden.PNG");
        ImageView background = new ImageView(img);
        background.setFitHeight(750);
        background.setFitWidth(1400);
        pane.getChildren().add(background);

        setScene(new Scene(pane, 1400, 750));
        display();
    }
}
