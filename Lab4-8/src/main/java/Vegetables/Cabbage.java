package Vegetables;

import java.util.List;

public class Cabbage extends Vegetable {
    private String glucosinolateContent;

    public Cabbage(String name, double caloriesPer100g, List<String> vitamins, double proteinsPer100g,
                   double carbohydratesPer100g, double fatsPer100g, String taste, String glucosinolateContent) {
        super(name, caloriesPer100g, vitamins, proteinsPer100g, carbohydratesPer100g, fatsPer100g, taste);
        this.glucosinolateContent = glucosinolateContent;
    }

}
