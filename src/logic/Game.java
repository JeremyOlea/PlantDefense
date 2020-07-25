package logic;

import java.util.ArrayList;

public class Game {
    private Player player;
    private ArrayList<ArrayList<Plant>> plot;

    public Game(Player player, ArrayList<ArrayList<Plant>> plot) {
        this.player = player;
        this.plot = plot;
    }

    public Player getPlayer() {
        return player;
    }
}
