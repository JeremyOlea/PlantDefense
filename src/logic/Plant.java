package logic;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Plant extends Character{
    private int price;
    private int row;
    private int column;
    private ImageView plantImg;
    private ImageView bullet = null;
    private boolean isDead = false; //Used to check if the plant is alive or not
    private boolean hasBullet = false;
    private double xPosition;
    private double yPosition;
    private double bulletStartPosition; //Where the PeaShooters mouth is
    private double bulletXPosition; //The x coordinate of the bullet
    private double bulletYPosition; //The x coordinate of the bullet
    private int bulletTimer = 0; //Checks how long each bullet has been shot out for. Resets once it hits bulletTimeLimit
    private final int BULLET_TIME_LIMIT = 1200; //How long each bullet can be shot out for
    private ImageView sunGIF = new ImageView(new Image(new FileInputStream("file:\\..\\images\\sun.gif")));
    private Button sunGif = new Button("", sunGIF);

    public Plant(String character) throws FileNotFoundException {
        super(character);
        if (character == "PeaShooter") {
            this.isDead = false;
            setDamage(20);
            setHealth(200);
            setPrice(100);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\pea-shooter.gif")));
            bullet = new ImageView(new Image( new FileInputStream("file:\\..\\images\\pea-bullet.png")));
        }
        else if (character == "Sunflower") {
            this.isDead = false;
            setHealth(300);
            setPrice(50);
            plantImg = new ImageView(new Image(new FileInputStream("file:\\..\\images\\Sunflower.gif")));
        }
        else if (character == "Frozen PeaShooter") {
            //TODO
        }
        else if (character == "Wallnut") {
            //TODO
        }
        else if (character == "Potato Mine") {
            //TODO
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

    public Node getPlantImg() {
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
}
