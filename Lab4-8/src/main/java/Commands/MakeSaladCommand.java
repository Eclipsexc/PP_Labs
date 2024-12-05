package Commands;

import Display.ConsoleMenu;
import Salad.*;
import Vegetables.Vegetable;
import FileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MakeSaladCommand implements Command {
    private final Vegetable[] availableVegetables;
    private final FileManager fileManager;

    public MakeSaladCommand(Vegetable[] availableVegetables, FileManager fileManager) {
        this.availableVegetables = availableVegetables;
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);
            ConsoleMenu.clearConsole();
            Salad salad = new Salad();
            String forLogger = "";

            while (true) {
                ConsoleMenu.clearConsole();
                System.out.println("Поточний склад салату:");
                ShowSalad.displaySaladInfo(salad);

                System.out.println("\nДоступні овочі:");
                new DisplayAvailableVegetablesCommand(availableVegetables, true, scanner).execute();

                System.out.println("\nОберіть овоч для додавання у салат або натисніть \"q\" для завершення:");
                System.out.print("Ваш вибір (число або \"q\"): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("q")) {
                    break;
                }

                int choice = Integer.parseInt(input) - 1;
                if (choice < 0 || choice >= availableVegetables.length) {
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    System.out.println("Натисніть Enter для продовження.");
                    scanner.nextLine();
                    continue;
                }

                Vegetable selectedVegetable = availableVegetables[choice];
                System.out.print("Введіть вагу у грамах для " + selectedVegetable.getName() + ": ");
                String weightInput = scanner.nextLine().trim();
                double weight = Double.parseDouble(weightInput);

                salad.addIngredient(selectedVegetable, weight);
                forLogger = selectedVegetable.getName() + " - " + weight + " грам; ";
            }

            if (!salad.getIngredients().isEmpty()) {
                fileManager.saveSalad(salad);
                System.out.println("Салат збережено у файл.");
                Logger LOGGER = LoggerFactory.getLogger(MakeSaladCommand.class);
                LOGGER.info("Створено салат з наступними інградієнтами: " + forLogger + ".");
            } else {
                System.out.println("Салат порожній і не буде збережений.");
            }
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(DisplayAvailableVegetablesCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
