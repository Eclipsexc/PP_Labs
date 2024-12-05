package Commands;

import Display.ConsoleMenu;
import FileManager.FileManager;
import Salad.*;
import Vegetables.Vegetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AnalyzeNutritionalContentCommand implements Command {
    private FileManager fileManager;

    public AnalyzeNutritionalContentCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);

            ConsoleMenu.clearConsole();
            new DisplaySaladsListCommand(fileManager, scanner).execute();
            List<Salad> saladsList = fileManager.loadSaladsFromFile();

            if (saladsList.isEmpty()) {
                System.out.println("Список салатів порожній.");
                return;
            }

            int saladChoice = -1;
            while (true) {
                System.out.print("Ваш вибір: ");
                String input = scanner.nextLine();
                saladChoice = Integer.parseInt(input) - 1;
                if (saladChoice >= 0 && saladChoice < saladsList.size()) {
                    break;
                } else {
                    System.out.println("Некоректний вибір. Введіть номер салату ще раз.");
                }
            }

            ConsoleMenu.clearConsole();
            Salad selectedSalad = saladsList.get(saladChoice);

            System.out.println("Ви обрали:");
            System.out.println("Салат #" + (saladChoice + 1));
            ShowSalad.displaySaladInfo(selectedSalad);

            SaladCalculator calculator = new SaladCalculator();
            double totalWeight = calculator.calculateTotalWeight(selectedSalad);
            double totalCalories = calculator.calculateTotalCalories(selectedSalad);

            System.out.println("\nАналіз:");
            System.out.println("Загальна вага: " + totalWeight + " г");
            System.out.println("Калорійність: " + totalCalories + " ккал");

            Vegetable highestCalorieVegetable = null;
            Vegetable lowestCalorieVegetable = null;
            double maxCalories = 0;
            double minCalories = Double.MAX_VALUE;

            List<Map.Entry<Vegetable, Double>> ingredientsList = new ArrayList<>(selectedSalad.getIngredients().entrySet());

            for (Map.Entry<Vegetable, Double> entry : ingredientsList) {
                Vegetable vegetable = entry.getKey();
                double weight = entry.getValue();
                double vegetableCalories = (vegetable.getCaloriesPer100g() * weight) / 100;

                if (vegetableCalories > maxCalories) {
                    maxCalories = vegetableCalories;
                    highestCalorieVegetable = vegetable;
                }
                if (vegetableCalories < minCalories) {
                    minCalories = vegetableCalories;
                    lowestCalorieVegetable = vegetable;
                }
            }

            if (highestCalorieVegetable != null && lowestCalorieVegetable != null) {
                System.out.println("Найкалорійніший овоч: " + highestCalorieVegetable.getName() + " (" + maxCalories + " ккал)");
                System.out.println("Найменш калорійний овоч: " + lowestCalorieVegetable.getName() + " (" + minCalories + " ккал)");
            }

            Set<String> uniqueVitamins = new HashSet<>();
            for (Vegetable vegetable : selectedSalad.getIngredients().keySet()) {
                uniqueVitamins.addAll(vegetable.getVitamins());
            }

            System.out.println("Вітаміни, що містяться у салаті: " + String.join(", ", uniqueVitamins));

            double totalProteins = 0.0;
            double totalCarbohydrates = 0.0;
            double totalFats = 0.0;

            for (Map.Entry<Vegetable, Double> entry : ingredientsList) {
                Vegetable vegetable = entry.getKey();
                double weight = entry.getValue();

                totalProteins += (vegetable.getProteinsPer100g() * weight) / 100;
                totalCarbohydrates += (vegetable.getCarbohydratesPer100g() * weight) / 100;
                totalFats += (vegetable.getFatsPer100g() * weight) / 100;
            }

            System.out.printf("Білки: %.2f г%n", totalProteins);
            System.out.printf("Вуглеводи: %.2f г%n", totalCarbohydrates);
            System.out.printf("Жири: %.2f г%n", totalFats);

            Logger LOGGER = LoggerFactory.getLogger(AnalyzeNutritionalContentCommand.class);
            LOGGER.info("Салат був проаналізований і виведений в консоль.");

            System.out.println("\nНатисніть Enter для повернення в меню.");
            scanner.nextLine();
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(AnalyzeNutritionalContentCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
