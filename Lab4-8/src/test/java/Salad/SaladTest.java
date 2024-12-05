package Salad;

import Vegetables.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaladTest {
    private Map<Vegetable, Double> testIngredients;
    private Vegetable[] availableVegetables;
    private Salad salad;

    @BeforeEach
    public void setUp() {
        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        testIngredients = new HashMap<Vegetable, Double>();
        testIngredients.put(availableVegetables[0], 13.0);

        salad = new Salad();
    }

    @Test
    public void test() {
        salad.addIngredient(availableVegetables[0], 12);
        salad.addIngredient(availableVegetables[0], 1);

        assertEquals(salad.getIngredients().get(availableVegetables[0]), testIngredients.get(availableVegetables[0]));
    }


}