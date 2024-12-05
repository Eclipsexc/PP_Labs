package Salad;

import Vegetables.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowSaladTest {
    private Vegetable[] availableVegetables;
    private Salad salad1;
    private Salad salad2;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        salad1 = new Salad();
        salad1.addIngredient(availableVegetables[0], 12);

        salad2 = new Salad();
    }

    @Test
    public void test1() {
        System.setOut(new PrintStream(outContent));

        ShowSalad.displaySaladInfo(null);
        String capturedOutput1 = outContent.toString().trim();
        assertTrue(capturedOutput1.contains("Салат не існує або не переданий."));
    }

    @Test
    public void test2() {
        System.setOut(new PrintStream(outContent));

        ShowSalad.displaySaladInfo(salad1);
        String capturedOutput2 = outContent.toString().trim();
        assertTrue(capturedOutput2.contains("Авокадо"));
    }

    @Test
    public void test3() {
        System.setOut(new PrintStream(outContent));

        ShowSalad.displaySaladInfo(salad2);
        String capturedOutput3 = outContent.toString().trim();
        assertTrue(capturedOutput3.contains("- Салат порожній."));

    }
}
