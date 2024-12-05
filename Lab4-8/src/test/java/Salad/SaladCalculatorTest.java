package Salad;

import Vegetables.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaladCalculatorTest {
    private Vegetable[] availableVegetables;
    private Salad salad;
    private SaladCalculator saladCalculator;

    @BeforeEach
    public void setUp() {
        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        salad = new Salad();
        salad.addIngredient(availableVegetables[0], 12);
        saladCalculator = new SaladCalculator();
    }

    @Test
    public void test() {
        double totalCal = saladCalculator.calculateTotalCalories(salad);

        assertEquals(totalCal, (double) (160 * 12) /100);

        double totalWeight = saladCalculator.calculateTotalWeight(salad);
        assertEquals(totalWeight, 12);
    }
}
