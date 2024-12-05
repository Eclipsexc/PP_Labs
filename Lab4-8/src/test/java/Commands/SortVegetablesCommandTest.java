package Commands;

import Vegetables.Vegetable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortVegetablesCommandTest {
    private Vegetable[] availableVegetables;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test1() {
        System.setOut(new PrintStream(outContent));

        String input = "5\n1\n5\n1\nenter\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        SortVegetablesCommand command = new SortVegetablesCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Відсортовані овочі:"));

    }

    @Test
    public void test2() {
        System.setOut(new PrintStream(outContent));

        String input = "5\n2\n5\n1\nenter\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        SortVegetablesCommand command = new SortVegetablesCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Відсортовані овочі:"));

    }

    @Test
    public void test3() {
        System.setOut(new PrintStream(outContent));

        String input = "5\n3\n5\n1\nenter\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        SortVegetablesCommand command = new SortVegetablesCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Відсортовані овочі:"));

    }

    @Test
    public void test4() {
        System.setOut(new PrintStream(outContent));

        String input = "5\n4\n5\n2\nenter\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        SortVegetablesCommand command = new SortVegetablesCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Відсортовані овочі:"));

    }
}
