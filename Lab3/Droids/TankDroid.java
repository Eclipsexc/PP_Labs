package Lab3.Droids;

public class TankDroid extends Droid {
    private boolean isShieldActive = false;

    public TankDroid(String name) {
        super(name, 30, 700, 1);
        this.abilityName = "Авангардний бар'єр";
        this.description = "Втілення непорушної оборони, володіє колосальним запасом здоров’я,\n" +
                "компенсуючи свою повільність та слабкий урон надзвичайною витривалістю.\n" +
                "Суперздібність створює навколо нього непробивний енергетичний щит, додаючи +200 HP,\n" +
                "що перетворює його на непохитну фортецю серед хаосу бою.";
    }

    @Override
    public void ability() {
        if (!isShieldActive && stars == 3) {
            isShieldActive = true;
            this.health +=200;
            System.out.println("Tank Droid активував щит! Тепер він захищений.");
        } else {
            System.out.println("Tank Droid вже находиться під захистом.");
        }
    }

    @Override
    public boolean isAbilityActive() {
        return isShieldActive;
    }

    @Override
    public void deactivateAbility() {
        if (isShieldActive) {
            isShieldActive = false;
            this.health -= 200;
            System.out.println("Tank Droid деактивував щит.");
        } else {
            System.out.println("Щит вже деактивований.");
        }
        super.deactivateAbility();
    }
    @Override
    public boolean isTypeOf(String type) {
        return type.equalsIgnoreCase("TankDroid");
    }
}
