package logic;

import gui.GameScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Zombie extends Character {
    private int speed = 0.2;
    private int row;
    private ImageView;
    private int position = 1400; //end of screen
    private boolean isDead;

    public Zombie(String character, int row) {
        super(character);
        this.row = row;
        switch(character) {
            case "Cone":
                setHealth(350);
                setDamage(10);
                break;
            default:
                setHealth(200);
                setDamage(10);
                break;
        }
        isDead = false;

    }

    public void startZombieThread() {
        int ENDOFFIELD = 200;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                position -= speed;
                if(getHealth() <= 0) {
                    isDead = true;
                }
                if(isDead) {
                    cancel();
                }

                if(position <= ENDOFFIELD) {
                    gameOver();
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 10);
    }

    public void gameOver() {
        //TODO print game over
        System.out.println("Game Over");
    }


}
