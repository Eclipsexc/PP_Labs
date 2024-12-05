package Salad;

import Vegetables.Vegetable;
import java.util.Map;


public class SaladCalculator {

    public SaladCalculator() {}

    public double calculateTotalCalories(Salad salad) {
        double totalCalories = 0.0;
        for (Map.Entry<Vegetable, Double> entry : salad.getIngredients().entrySet()) {
            Vegetable vegetable = entry.getKey();
            double weight = entry.getValue();
            totalCalories += (vegetable.getCaloriesPer100g() * weight) / 100;
        }
        return totalCalories;
    }

    public double calculateTotalWeight(Salad salad) {
        double totalWeight = 0.0;
        for (double weight : salad.getIngredients().values()) {
            totalWeight += weight;
        }
        return totalWeight;
    }
}
