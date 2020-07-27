package logic;

import gui.GameScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class Zombie extends Character {
    private double speed = 0.01;
    private boolean frozen = false;
    private int row;
    private int col;
    private ImageView zombieImg;
    private int position = 1400; //end of screen
    private final int IMAGE_WIDTH = 130;
    private boolean isDead;
    private int zombiePeriod = 25;
    private boolean beenFrozen = false;

    public Zombie(String character, int row) throws FileNotFoundException {
        super(character);
        this.row = row < 5 && row >= 0 ? row : 0;
        switch(character) {
            case "Cone":
                zombieImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Conehead.gif")));
                zombieImg.setFitHeight(145);
                setHealth(350);
                setDamage(10);
                break;
            default:
                zombieImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Zombieidle.gif")));
                zombieImg.setFitHeight(125);
                setHealth(200);
                setDamage(10);
                break;
        }
        isDead = false;
        zombieImg.setX(position);
        zombieImg.setY(getZombieYPosition(row));
        zombieImg.setFitWidth(IMAGE_WIDTH);

        zombieImg.setPreserveRatio(true);
    }

    private double getZombieYPosition(int row) {
        if (row == 0) {
            return 120;
        }
        else if (row == 1) {
            return 220;
        }
        else if (row == 2){
            return 310;
        }
        else if (row == 3) {
            return 410;
        }
        else {
            return 525;
        }
    }

    public void startZombieThread() {
        int END_OF_FIELD = 200;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println(getHealth());
//                        System.out.println(speed);
                        position -= speed;
                        setColumn(position);
                        zombieImg.setX(position);
                        if(getHealth() <= 0) {
                            isDead = true;
                        }

                        if(isDead) {
                            System.out.println("Zombie Dead");
                            timer.cancel();
                            timer.purge();
                        }

                        if(position <= END_OF_FIELD) {
                            gameOver();
                            timer.cancel();
                            timer.purge();
                        }
                        if(frozen && !beenFrozen) {
                            beenFrozen = true;
                            zombiePeriod = 30;
                            timer.cancel();
                            timer.purge();
                            startZombieThread();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 50, zombiePeriod);
    }

    private void setColumn(double position) {
//        int col_prev = col;
        if (position > 295 && position <= 416) {
            this.col = 0;
        }
        else if (position > 416 && position <= 518) {
            this.col = 1;
        }
        else if (position > 518 && position <= 625) {
            this.col = 2;
        }
        else if (position > 625 && position <= 730) {
            this.col = 3;
        }
        else if (position > 730 && position <= 855) {
            this.col = 4;
        }
        else if (position > 855 && position <= 959) {
            this.col = 5;
        }
        else if (position > 959 && position <= 1069) {
            this.col = 6;
        }
        else if (position > 1069 && position <= 1167) {
            this.col = 7;
        }
        else if (position > 1167 && position <= 1309){
            this.col = 8;
        } else {
            this.col = 9;
        }
//        if(col != col_prev) System.out.println("Zombie at column " + col);
    }

    private void gameOver() {
        System.out.println("You Lose!");
        System.exit(0);
    }


    public void addToPosition(int position) {
        this.position += position;
    }

    public Node getZombieImg() {
        return zombieImg;
    }

    public int getRow() {
        return row;
    }

    public  int getCol() {
        return col;
    }

    public int getPosition() {
        return position;
    }

    public void stopZombie() {
        speed = 0;
    }

    public void startZombie() {
        speed = 0.1;
    }

    public void setFrozenTrue() {
        this.frozen = true;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
}
