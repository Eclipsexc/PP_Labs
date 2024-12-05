package Lab3.Droids;

public class Droid {
    protected String name;
    protected int damage;
    protected int health;
    protected int speed;
    protected int stars;
    protected int gems;
    protected String abilityName;
    protected String description;
    protected boolean ultimateActive;
    protected boolean currentPlayer;

    public Droid(String name, int damage, int health, int speed) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.speed = speed;
        this.stars = 0;
        this.gems = 0;
        this.ultimateActive = false;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void addGems() {
        gems++;
    }
    public int getGems() {
        return gems;
    }
    public void addStar() {
        stars++;
    }
    public String getAbilityName(){
        return abilityName;
    }
    public String getDescription(){
        return description;
    }

    public boolean isUltimateActive() {
        return ultimateActive;
    }

    public boolean isAbilityActive() {
        return ultimateActive;
    }

    public void deactivateAbility() {
        ultimateActive = false;
    }

    public int getStars() {
        return stars;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            health = 0;
        }
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void ability() {
        if (ultimateActive) {
            System.out.println(this.name + " використовує здатність!");
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void displayFighterInfo() {
        System.out.println("===\u001B[36mІнформація щодо \"" + name + "\"\u001B[0m===");
        System.out.println("\u001B[32mЗдоров'я: \u001B[0m" + health);
        System.out.println("\u001B[31mУшкодження: \u001B[0m" + damage);
        System.out.println("\u001B[34mШвидкість: \u001B[0m" + speed);
        System.out.println("\u001B[35m\"Зірочок\" до ульти: \u001B[0m" + stars + "/3");
        System.out.println("\u001B[33mСуперздатність активована: \u001B[0m" + (isAbilityActive() ? "Так" : "ні"));
        System.out.println("==========================");
    }
    public boolean isTypeOf(String type) {
        return type.equalsIgnoreCase(this.getClass().getSimpleName());
    }
}
