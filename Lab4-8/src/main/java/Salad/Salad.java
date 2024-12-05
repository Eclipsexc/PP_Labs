package Salad;

import Vegetables.Vegetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Salad {
    private Map<Vegetable, Double> ingredients;

    public Salad() {
        ingredients = new HashMap<>();
    }

    public void addIngredient(Vegetable vegetable, double weight) {
        if (ingredients.containsKey(vegetable)) {
            ingredients.put(vegetable, ingredients.get(vegetable) + weight);
        } else {
            ingredients.put(vegetable, weight);
        }
    }

    public Map<Vegetable, Double> getIngredients() {
        return ingredients;
    }
}
