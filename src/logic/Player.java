package logic;

public class Player {
    private int suns = 50;
    private boolean hasSelected = false;
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

    public boolean getHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }
}
