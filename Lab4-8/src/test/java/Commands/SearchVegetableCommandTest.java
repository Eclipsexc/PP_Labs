package Commands;

import FileManager.FileManager;
import Vegetables.Vegetable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchVegetableCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testMakeSaladCommand1() {
        System.setOut(new PrintStream(outContent));

        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        String input = "12\nenter\n1\nNOT_VITAMINE\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        SearchVegetableCommand command = new SearchVegetableCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Доступні вітаміни: "));
    }

    @Test
    public void testMakeSaladCommand3() {
        System.setOut(new PrintStream(outContent));

        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        String input = "12\nenter\n1\nC\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        SearchVegetableCommand command = new SearchVegetableCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Доступні вітаміни: "));
    }

    @Test
    public void testMakeSaladCommand2() {
        System.setOut(new PrintStream(outContent));

        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        String input = "2\nсолодкий\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        SearchVegetableCommand command = new SearchVegetableCommand(availableVegetables);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Доступні смаки: "));
    }
}
