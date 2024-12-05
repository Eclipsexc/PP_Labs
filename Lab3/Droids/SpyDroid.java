package Lab3.Droids;

public class SpyDroid extends Droid {
    private boolean isInvisible;

    public SpyDroid(String name) {
        super(name, 40, 200, 2);
        this.isInvisible = false;
        this.abilityName = "Квантовий маневр";
        this.description = "Високотехнологічний кібер-шпигун, створений для розвідувальних місій, залишаючись у тіні.\n" +
                "Завдяки можливості зменшуватись у розмірах і пересуватися з неймовірною швидкістю,\n" +
                "він стає майже невловимим на полі бою, поглинаючи половину отриманого урону.";
    }

    @Override
    public void ability() {
        if (!isInvisible && stars == 3) {
            isInvisible = true;
            this.speed = 3;
            this.damage += 15;
            System.out.println("Spy Droid став невидимим та збільшив швидкість!");
        } else {
            System.out.println("Spy Droid вже невидимий.");
        }
    }

    @Override
    public boolean isAbilityActive() {
        return isInvisible;
    }

    @Override
    public void deactivateAbility() {
        if (isInvisible) {
            isInvisible = false;
            this.speed = 2;
            this.damage -= 15;
            System.out.println("Spy Droid тепер видимий.");
        } else {
            System.out.println("Spy Droid вже видимий.");
        }
        super.deactivateAbility();
    }
    @Override
    public boolean isTypeOf(String type) {
        return type.equalsIgnoreCase("SpyDroid");
    }
}
