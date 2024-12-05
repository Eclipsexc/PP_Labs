package Vegetables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.slf4j.*;
public class VegetableTest {
    private Vegetable testVeg;

    @BeforeEach
    public void setUp() {
        testVeg = new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"),
                2.0, 9.0, 15.0, "кремовий");
    }

    @Test

    public void test() {
        assertEquals(testVeg.getName(), "Авокадо");
        assertEquals(testVeg.getCaloriesPer100g(), 160);
        assertEquals(testVeg.getVitamins().get(0), "C");
        assertEquals(testVeg.getVitamins().get(1), "E");
        assertEquals(testVeg.getVitamins().get(2), "K");
        assertEquals(testVeg.getVitamins().get(3), "B6");
        assertEquals(testVeg.getProteinsPer100g(), 2.0);
        assertEquals(testVeg.getCarbohydratesPer100g(), 9.0);
        assertEquals(testVeg.getFatsPer100g(), 15.0);
        assertEquals(testVeg.getTaste(), "кремовий");
    }
}
