package Vegetables;

import java.util.List;

public class Pepper extends Vegetable {
    private int spicinessLevel;

    public Pepper(String name, double caloriesPer100g, List<String> vitamins, double proteinsPer100g,
                  double carbohydratesPer100g, double fatsPer100g, String taste, int spicinessLevel) {
        super(name, caloriesPer100g, vitamins, proteinsPer100g, carbohydratesPer100g, fatsPer100g, taste);
        this.spicinessLevel = spicinessLevel;
    }

}
