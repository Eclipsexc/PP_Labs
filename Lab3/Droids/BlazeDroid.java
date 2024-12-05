package Lab3.Droids;

import java.util.LinkedList;
import java.util.Queue;

public class BlazeDroid extends Droid {
    public static final int MAX_TRAIL_LENGTH = 5;
    public Queue<int[]> blazeDroidTrail;

    public BlazeDroid(String name) {
        super(name, 70, 300, 2);
        this.blazeDroidTrail = new LinkedList<>();

        this.abilityName = "Пекельна траєкторія";
        this.description = "Представник вогняної стихії, що залишає за собою полум'яний слід,\n" +
                "немов змія, що граційно прокладає маршрут через поле битви.\n" +
                "Кожен хто наважиться ступити на його слід, відчує на собі потужний опік.\n" +
                "Дроїд створює вогняну пастку, змушуючи противників щоразу остерігатись і уважно продумувати наступний крок.";
    }

    @Override
    public void ability() {
        if (stars == 3) {
            this.damage -= 20;
            System.out.println(this.name + " застосовує свою здібність та залишає полум'яний слід.");
            ultimateActive = true;
        }
    }

    @Override
    public boolean isAbilityActive() {
        return ultimateActive;
    }

    @Override
    public void deactivateAbility() {
        ultimateActive = false;
        this.damage += 20;
        System.out.println(this.name + " ульта деактивована.");
        super.deactivateAbility();
    }
    @Override
    public boolean isTypeOf(String type) {
        return type.equalsIgnoreCase("BlazeDroid");
    }
}
