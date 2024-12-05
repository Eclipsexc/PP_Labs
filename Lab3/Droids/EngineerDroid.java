package Lab3.Droids;

public class EngineerDroid extends Droid {
    public byte ultimateUsed;
    private boolean barrierActive;
    public byte fail;

    public EngineerDroid(String name) {
        super(name, 30, 500, 2);
        this.barrierActive = false;
        this.ultimateUsed = 0;
        this.fail =0;
        this.abilityName = "Інженерний підхід";
        this.description = "Справжній майстер свого ремесла наділений талантом\n" +
                "зводити неприступні оборонні конструкції. Забезпечуючи надійний захист,\n" +
                "Інженер застосовує важку артилерію, що збільшують його шанси скривдити противника,\n" +
                "проте через застосування такої могутньої зброї приходиться поплатитись швидкістю дроїда";
    }

    @Override
    public void ability() {
        if (!barrierActive && stars == 3) {
            barrierActive = true;
            this.damage += 20;
            this.speed = 1;
            System.out.println(name + " застосовує свою здібність для створення паркана.");
        } else {
            System.out.println(name + " застосовує свою здібність та створив паркан.");
        }
    }

    @Override
    public boolean isAbilityActive() {
        return barrierActive;
    }

    @Override
    public void deactivateAbility() {
        if (barrierActive) {
            barrierActive = false;
            ultimateUsed = 0;
            this.damage -= 20;
            this.speed = 2;
            System.out.println(name + " зруйнував паркан.");
        } else {
            System.out.println(name + " паркан вже зруйнований.");
        }
        super.deactivateAbility();
    }

    public byte isUltimateUsed() {
        return ultimateUsed;
    }
    @Override
    public boolean isTypeOf(String type) {
        return type.equalsIgnoreCase("EngineerDroid");
    }
}
