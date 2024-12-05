package Commands;

import Display.ConsoleMenu;
import Vegetables.Vegetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortVegetablesCommand implements Command {
    private Vegetable[] availableVegetables;

    public SortVegetablesCommand(Vegetable[] availableVegetables) {
        this.availableVegetables = availableVegetables;
    }

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);

            ConsoleMenu.clearConsole();

            System.out.println("Виберіть параметр для сортування:");
            System.out.println("1. Калорійність");
            System.out.println("2. Білки");
            System.out.println("3. Вуглеводи");
            System.out.println("4. Жири");

            int choice = -1;
            while (choice < 1 || choice > 4) {
                System.out.print("Обрано: ");
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 4) {
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                }
            }

            System.out.println("Оберіть порядок сортування:");
            System.out.println("1. За зростанням");
            System.out.println("2. За спаданням");

            boolean ascending = true;
            while (true) {
                System.out.print("Обрано: ");
                String input = scanner.nextLine().trim();
                int orderChoice = Integer.parseInt(input);
                if (orderChoice == 1) {
                    ascending = true;
                    break;
                } else if (orderChoice == 2) {
                    ascending = false;
                    break;
                } else {
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                }
            }

            final int sortChoice = choice;

            List<Vegetable> vegetablesList = new ArrayList<>();
            for (Vegetable vegetable : availableVegetables) {
                vegetablesList.add(vegetable);
            }

            Comparator<Vegetable> comparator = Comparator.comparingDouble(v -> switch (sortChoice) {
                case 1 -> v.getCaloriesPer100g();
                case 2 -> v.getProteinsPer100g();
                case 3 -> v.getCarbohydratesPer100g();
                case 4 -> v.getFatsPer100g();
                default -> 0.0;
            });

            if (!ascending) {
                comparator = comparator.reversed();
            }

            Logger LOGGER = LoggerFactory.getLogger(SortVegetablesCommand.class);
            switch (sortChoice) {
                case 1 -> LOGGER.info("Салат відсортовано по калорійності і виведено в консоль.");
                case 2 -> LOGGER.info("Салат відсортовано по вмісту білка і виведено в консоль.");
                case 3 -> LOGGER.info("Салат відсортовано по вмісту вуглеводів і виведено в консоль.");
                case 4 -> LOGGER.info("Салат відсортовано по вмісту жирів і виведено в консоль.");
            }


            vegetablesList.sort(comparator);

            for (int i = 0; i < vegetablesList.size(); i++) {
                availableVegetables[i] = vegetablesList.get(i);
            }

            ConsoleMenu.clearConsole();
            System.out.println("Відсортовані овочі:");
            new DisplayAvailableVegetablesCommand(availableVegetables, true, scanner).execute();

            System.out.println("\nНатисніть Enter для повернення до меню.");
            scanner.nextLine();
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(SortVegetablesCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
