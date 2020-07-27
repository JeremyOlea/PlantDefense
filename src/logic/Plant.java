package logic;

import gui.GameScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Plant extends Character{
    private int price;
    private int row;
    private int column;
    private ImageView plantImg = null;
    private ImageView bullet = null;
    private boolean willFreeze = false;
    private boolean isDead = false; //Used to check if the plant is alive or not
    private boolean hasBullet = false;
    private double xPosition = -1;
    private double yPosition = -1;
    private double bulletStartPosition; //Where the PeaShooters mouth is
    private double bulletXPosition; //The x coordinate of the bullet
    private double bulletYPosition; //The x coordinate of the bullet
    private int bulletTimer = 0; //Checks how long each bullet has been shot out for. Resets once it hits bulletTimeLimit
    private final int BULLET_TIME_LIMIT = 1200; //How long each bullet can be shot out for
    private ImageView sunGIF = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sun.gif")));
    private Button sunGif = new Button("", sunGIF);
    private boolean firedBullet = false;

    public Plant(String character) throws FileNotFoundException {
        super(character);
        if (character == "PeaShooter") {
            this.isDead = false;
            setDamage(20);
            setHealth(200);
            setPrice(100);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\pea-shooter.gif")));
            bullet = new ImageView(new Image( new FileInputStream("file:\\..\\images\\pea-bullet.png")));
            hasBullet = true;
        }
        else if (character == "SunFlower") {
            this.isDead = false;
            setHealth(300);
            setPrice(50);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Sunflower.gif")));
        }
        else if (character == "Frozen PeaShooter") {
            setDamage(12);
            setHealth(200);
            setPrice(175);
            willFreeze = true;
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\frozen-pea.gif")));
            bullet = new ImageView(new Image(new FileInputStream("file:\\..\\images\\frozen-pea-bullet.png")));
            hasBullet = true;
        }
        else if (character == "Wallnut") {
            setDamage(0);
            setPrice(50);
            setHealth(1000);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\walnut_full_life.gif")));
        }
        else if (character == "Potato Mine") {
            setDamage(100);
            setPrice(25);
            setHealth(10000);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\potato-mine-active.gif")));
        }
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int column) {
        this.column = column;
    }

    public ImageView getPlantImg() {
        return plantImg;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setBulletXPosition(double bulletXPosition) {
        this.bulletXPosition = bulletXPosition;
    }

    public void setBulletYPosition(double bulletYPosition) {
        this.bulletYPosition = bulletYPosition;
    }

    public void setBulletStartPosition(double bulletStartPosition) {
        this.bulletStartPosition = bulletStartPosition;
    }

    public void setImagePosition() {
        if(xPosition != -1 && yPosition != -1) {
            plantImg.setLayoutX(xPosition);
            plantImg.setLayoutY(yPosition);
        } else {
            System.out.println("no x or y pos");
        }

    }

    public void setBulletImagePosition() {
        if(bulletXPosition != -1 && bulletYPosition != -1 && hasBullet) {
            bullet.setLayoutX(bulletXPosition);
            bullet.setLayoutY(bulletYPosition);
        }
    }

    public void moveBullet(ArrayList<Zombie> zombies) {
        if (hasBullet && bullet != null && !isDead && !firedBullet) {
            GameScene.getScreenPane().getChildren().addAll(bullet);
            firedBullet = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(isDead) {
                                GameScene.getScreenPane().getChildren().remove(bullet);
                                timer.cancel();
                                timer.purge();
                            }
                            bulletTimer += 1;
                            if (bulletXPosition <= 1400 && bulletXPosition != -100) {
                                bulletXPosition += 0.75;
                            }
                            else {
                                bulletXPosition = -100;
                            }
                            if(bulletTimer > BULLET_TIME_LIMIT) {
                                bulletXPosition = bulletStartPosition;
                                bulletTimer = 0;
                            }
                            bullet.setLayoutX(bulletXPosition);
                            for(int i = 0; i < zombies.size(); i++) {
                                Zombie z = zombies.get(i);
                                if(z.getHealth() > 0) {
                                    if(hasBullet && bulletXPosition >= z.getPosition()) {
                                        z.takeDamage(getDamage());
                                        setBulletXPosition(-100);
                                        if(willFreeze) {
                                            z.setFrozenTrue();
                                        }
                                    }
                                }
                                if(z.getHealth() <= 0) {
                                    zombies.remove(z);
                                }
                            }
                        }
                    });

                }
            }, 0, 1);
        }
    }

    public Node getBulletImg() {
        return bullet;
    }

    public boolean getHasBullet() {
        return hasBullet;
    }

    public double getBulletXPosition() {
        return bulletXPosition;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean b) {
        isDead = true;
    }

    public int getPrice() {
        return price;
    }
}
