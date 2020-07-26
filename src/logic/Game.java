package logic;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {
    private Player player;
    private ArrayList<ArrayList<Plant>> plot;
    private ArrayList<Zombie> zombies;

    public Game(Player player, ArrayList<ArrayList<Plant>> plot) throws FileNotFoundException {
        this.player = player;
        this.plot = plot;
//        setFirstWave();
    }

    public void setFirstWave() throws FileNotFoundException {
        zombies.add(new Zombie("Zombie", 4));
        zombies.add(new Zombie("Zombie", 3));
        zombies.add(new Zombie("Cone Zombie",1));
        zombies.add(new Zombie("Zombie", 2));
        zombies.add(new Zombie("Cone Zombie", 4));
        zombies.add(new Zombie("Zombie", 2));
        zombies.add(new Zombie("Cone Zombie", 5));
        zombies.add(new Zombie("Zombie", 3));
        int zombieSpacing = 230;
        for (int i = 0; i < zombies.size(); i++) {
            zombies.get(i).addToPosition(zombieSpacing);
            zombieSpacing += zombieSpacing;
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
}
