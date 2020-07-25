package handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import logic.Player;

public class PlantButtonHandler implements EventHandler<ActionEvent> {
    private Player player;
    private String plant;

    public PlantButtonHandler(Player player) {
        this.player = player;
    }

    @Override
    public void handle(ActionEvent event) {
        Button source = (Button) event.getSource();
        switch(source.getText()) {
            case "p0":
                plant = "Frozen PeaShooter";
                break;
            case "p1":
                plant = "PeaShooter";
                break;
            case "p2":
                plant = "SunFlower";
                break;
            case "p3":
                plant = "Potato Mine";
                break;
            case "p4":
                plant = "Wallnut";
                break;
            default:
                // Should never reach here
        }
        System.out.println("clicked " + plant);
    }
    //TODO
}
