package Commands;

import Display.ConsoleMenu;
import Vegetables.Vegetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SearchVegetableCommand implements Command {
    private Vegetable[] availableVegetables;
    private Scanner scanner;

    public SearchVegetableCommand(Vegetable[] availableVegetables) {
        this.availableVegetables = availableVegetables;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        try {
            ConsoleMenu.clearConsole();

            int choice = 0;
            while (true) {
                System.out.println("Виберіть критерій пошуку:");
                System.out.println("1. За вітаміном");
                System.out.println("2. За смаком");

                System.out.print("Обрано: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    System.out.println("Натисніть Enter для продовження.");
                    scanner.nextLine();
                }
            }

            List<Vegetable> matchingVegetables = new ArrayList<>();

            Logger LOGGER = LoggerFactory.getLogger(SearchVegetableCommand.class);

            if (choice == 1) {
                Set<String> uniqueVitamins = new HashSet<>();
                for (int i = 0; i < availableVegetables.length; i++) {
                    uniqueVitamins.addAll(availableVegetables[i].getVitamins());
                }

                System.out.println("Доступні вітаміни: " + String.join(", ", uniqueVitamins));
                System.out.print("Введіть назву вітаміну: ");
                String vitamin = scanner.nextLine().toUpperCase();

                for (int i = 0; i < availableVegetables.length; i++) {
                    if (availableVegetables[i].getVitamins().contains(vitamin)) {
                        matchingVegetables.add(availableVegetables[i]);
                    }
                }
                LOGGER.info("Виведено доступні овочі, що містять " + vitamin + ".");

            } else if (choice == 2) {
                Set<String> uniqueTastes = new HashSet<>();
                for (int i = 0; i < availableVegetables.length; i++) {
                    uniqueTastes.add(availableVegetables[i].getTaste().toLowerCase());
                }

                System.out.println("Доступні смаки: " + String.join(", ", uniqueTastes));
                System.out.print("Введіть смак: ");
                String taste = scanner.nextLine().toLowerCase();

                for (int i = 0; i < availableVegetables.length; i++) {
                    if (availableVegetables[i].getTaste().toLowerCase().contains(taste)) {
                        matchingVegetables.add(availableVegetables[i]);
                    }
                }
                LOGGER.info("Виведено доступні овочі зі смаком " + taste + ".");
            }

            if (matchingVegetables.isEmpty()) {
                System.out.println("Немає овочів, що відповідають заданому критерію.");
                System.out.println("Натисніть Enter для продовження.");
                scanner.nextLine();
            } else {
                Vegetable[] filteredVegetables = new Vegetable[matchingVegetables.size()];
                for (int i = 0; i < matchingVegetables.size(); i++) {
                    filteredVegetables[i] = matchingVegetables.get(i);
                }
                new DisplayAvailableVegetablesCommand(filteredVegetables, true, scanner).execute();
            }
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(SearchVegetableCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
