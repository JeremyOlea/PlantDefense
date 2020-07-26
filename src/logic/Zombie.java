package logic;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class Zombie extends Character {
    int START_POSITION = 1500;
    private double speed = 0.2;
    private int row;
    private int col;
    private ImageView zombieImg;
    private int position = 1650; //end of screen
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 130;
    private boolean isDead;

    public Zombie(String character, int row) throws FileNotFoundException {
        super(character);
        this.row = row;
        switch(character) {
            case "Cone":
                zombieImg = new ImageView(new Image(new FileInputStream("file:\\..\\..\\images\\Conehead.gif")));
                setHealth(350);
                setDamage(10);
                break;
            default:
                setHealth(200);
                setDamage(10);
                break;
        }
        isDead = false;
        this.row = row;
        zombieImg.setX(START_POSITION);
        zombieImg.setY(getZombieYPosition(row));
        zombieImg.setFitHeight(IMAGE_HEIGHT);
        zombieImg.setFitWidth(IMAGE_WIDTH);

        //Setting the preserve ratio of the image view
        zombieImg.setPreserveRatio(true);
    }

    private double getZombieYPosition(int row) {
        if (row == 0) {
            return 21;
        }
        else if (row == 1) {
            return 310;
        }
        else if (row == 2){
            return 410;
        }
        else if (row == 3) {
            return 525;
        }
        else {
            return 630;
        }
    }

    public void startZombieThread() {
        int END_OF_FIELD = 200;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        position -= speed;
                        setColumn(position);
                        zombieImg.setX(position);
                        if(getHealth() <= 0) {
                            isDead = true;
                        }
                        if(isDead) {
                            cancel();
                        }

                        if(position <= END_OF_FIELD) {
                            gameOver();
                            cancel();
                        }
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 10);
    }

    private void setColumn(double position) {
        if (position > 250 && position <= 366) {
            this.col = 0;
        }
        else if (position > 366 && position <= 477) {
            this.col = 1;
        }
        else if (position > 477 && position <= 569) {
            this.col = 2;
        }
        else if (position > 569 && position <= 671) {
            this.col = 3;
        }
        else if (position > 671 && position <= 774) {
            this.col = 4;
        }
        else if (position > 774 && position <= 878) {
            this.col = 5;
        }
        else if (position > 878 && position <= 978) {
            this.col = 6;
        }
        else if (position > 978 && position <= 1074) {
            this.col = 7;
        }
        else if (position > 1074 && position <= 1170) {
            this.col = 8;
        }
        else {
            this.col = 9;
        }
    }

    private void gameOver() {
        //TODO print game over
        System.out.println("Game Over");
    }


    public void addToPosition(int position) {
        this.position += position;
    }
}
