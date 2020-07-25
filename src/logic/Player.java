package logic;

public class Player {
    private int suns = 50;
    private String selected = "";

    public Player() {

    }

    public int getSuns() {
        return suns;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void incSuns() {
        suns += 25;
    }

    public void decSuns(int dec) {
        suns -= dec;
    }
}
