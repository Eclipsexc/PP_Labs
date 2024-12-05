package Salad;
import Vegetables.Vegetable;
import java.util.Map;

public class ShowSalad {

    public static void displaySaladInfo(Salad salad) {
        if (salad == null) {
            System.out.println("Салат не існує або не переданий.");
            return;
        }

        Map<Vegetable, Double> ingredients = salad.getIngredients();

        if (ingredients == null || ingredients.isEmpty()) {
            System.out.println("- Салат порожній.");
            return;
        }

        System.out.println("Склад салату:");
        for (Map.Entry<Vegetable, Double> entry : ingredients.entrySet()) {
            Vegetable vegetable = entry.getKey();
            double weight = entry.getValue();
            System.out.printf("- %s: %.2f г%n", vegetable.getName(), weight);
        }
        SaladCalculator calculator = new SaladCalculator();

        double totalWeight = calculator.calculateTotalWeight(salad);
        double totalCalories = calculator.calculateTotalCalories(salad);

        System.out.printf("Загальна вага: %.2f г%n", totalWeight);
        System.out.printf("Енергетична цінність: %.2f ккал%n", totalCalories);
    }
}
