package Commands;

import Display.Main;
import Vegetables.Vegetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class DisplayAvailableVegetablesCommand implements Command {
    private Vegetable[] availableVegetables;
    private Scanner scanner;
    private boolean displayIndex;

    public DisplayAvailableVegetablesCommand(Vegetable[] availableVegetables, boolean displayIndex, Scanner scanner) {
        this.availableVegetables = availableVegetables;
        this.scanner = scanner;
        this.displayIndex = displayIndex;
    }

    @Override
    public void execute() {
        Logger LOGGER = LoggerFactory.getLogger(DisplayAvailableVegetablesCommand.class);
        try {
            System.out.printf("%-5s %-20s %-10s %-20s %-10s %-10s %-10s %-10s%n",
                    displayIndex ? "#" : "", "Назва", "Ккал/100г", "Вітаміни", "Білки", "Вуглеводи", "Жири", "Смак");
            System.out.println("-".repeat(100));

            for (int i = 0; i < availableVegetables.length; i++) {
                Vegetable vegetable = availableVegetables[i];
                if (displayIndex) {
                    System.out.printf("%-5d", i + 1);
                } else {
                    System.out.printf("%-5s", "");
                }
                System.out.printf("%-20s %-10.2f %-20s %-10.2f %-10.2f %-10.2f %-10s%n",
                        vegetable.getName(),
                        vegetable.getCaloriesPer100g(),
                        vegetable.getVitamins(),
                        vegetable.getProteinsPer100g(),
                        vegetable.getCarbohydratesPer100g(),
                        vegetable.getFatsPer100g(),
                        vegetable.getTaste());
            }

            scanner.nextLine();
        } catch (Exception e) {
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
