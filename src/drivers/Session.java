package drivers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Session extends Application {
    private Stage stage;

    public void setStage(Scene scene) {
        stage.setScene(scene);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Plant Defense");
        stage.setResizeable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}