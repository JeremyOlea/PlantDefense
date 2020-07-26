package logic;

public class Character {
    private String character;
    private int health;
    private int damage;

    public Character(String character) {
        this.character = character;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public String getCharacter() {
        return character;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public String getName() {
        return character;
    }
}
