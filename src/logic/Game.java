package logic;

import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {
    private Player player;
    private ArrayList<ArrayList<Plant>> plot;
    private Text sunCounterText;
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Zombie> row0 = new ArrayList<>();
    private ArrayList<Zombie> row1 = new ArrayList<>();
    private ArrayList<Zombie> row2 = new ArrayList<>();
    private ArrayList<Zombie> row3 = new ArrayList<>();
    private ArrayList<Zombie> row4 = new ArrayList<>();

    public Game(Player player, ArrayList<ArrayList<Plant>> plot) throws FileNotFoundException {
        this.player = player;
        this.plot = plot;
        setFirstWave();
    }

    public void setFirstWave() throws FileNotFoundException {
        zombies.add(new Zombie("Zombie", 3));
//        zombies.add(new Zombie("Zombie", 2));
//        zombies.add(new Zombie("Cone",0));
//        zombies.add(new Zombie("Zombie", 1));
//        zombies.add(new Zombie("Cone", 3));
//        zombies.add(new Zombie("Zombie", 1));
//        zombies.add(new Zombie("Cone", 4));
//        zombies.add(new Zombie("Zombie", 2));
        int zombieSpacing = 230;
        for (int i = 0; i < zombies.size(); i++) {
            zombies.get(i).addToPosition(zombieSpacing);
            zombieSpacing += zombieSpacing;
            addZombie(zombies.get(i));
        }
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    private void startZombieThread() {
        for(int i = 0; i < zombies.size(); i++) {
            zombies.get(i).startZombieThread();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Plant getPlant(int row, int col) {
        return plot.get(row).get(col);
    }

    public void setPlant(Plant plant) {
        int row = plant.getRow();
        int column = plant.getColumn();
        plot.get(row).set(column, plant);
    }

    public boolean isFreePosition(int row, int col) {
        return plot.get(row).get(col) == null;
    }

    public void addZombie(Zombie zombie) {
        switch(zombie.getRow()) {
            case 1:
                row1.add(zombie);
                break;
            case 2:
                row2.add(zombie);
                break;
            case 3:
                row3.add(zombie);
                break;
            case 4:
                row4.add(zombie);
                break;
            default:
                row0.add(zombie);
        }
        zombie.startZombieThread();
    }

    public ArrayList<Zombie> getZombieRow(int row) {
        switch(row) {
            case 1:
                return row1;
            case 2:
                return row2;
            case 3:
                return row3;
            case 4:
                return row4;
            default:
                return row0;
        }
    }

    public void remove(int row, int col) {
        plot.get(row).set(col, null);
    }

    public void setSunCounterText(Text sunCounterText) {
        this.sunCounterText = sunCounterText;
    }

    public Text getSunCounterText() {
        return sunCounterText;
    }

    public boolean gameStillRunning() {
        if(row0.size() != 0) return true;
        if(row1.size() != 0) return true;
        if(row2.size() != 0) return true;
        if(row3.size() != 0) return true;
        if(row4.size() != 0) return true;
        return false;
    }
}
