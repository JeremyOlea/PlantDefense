package gui;
import drivers.Session;
import javafx.scene.Scene;

public abstract class BaseScene {
    private Scene scene;
    private Session session;

    public BaseScene(Session session) {
        this.session = session;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public abstract void setup() throws Exception;

    protected void display() {
        session.getScene(this.scene);
    }
}