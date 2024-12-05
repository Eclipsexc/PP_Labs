package Commands;

import FileManager.FileManager;
import Vegetables.Vegetable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MakeSaladCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testMakeSaladCommand() {
        System.setOut(new PrintStream(outContent));

        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };
        FileManager fileManager = new FileManager("test4_for_createsalad");

        String input = "enter\n56\nenter\nenter\n1\n12\nenter\nq";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MakeSaladCommand command = new MakeSaladCommand(availableVegetables, fileManager);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Введіть вагу у грамах для "));
    }
}
