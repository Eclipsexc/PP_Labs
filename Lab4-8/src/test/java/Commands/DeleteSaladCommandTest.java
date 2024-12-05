package Commands;

import FileManager.FileManager;
import Salad.Salad;
import Vegetables.Vegetable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteSaladCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test1() {
        System.setOut(new PrintStream(outContent));

        String input = "enter\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        FileManager fileManager1 = new FileManager("test2_empty");
        DeleteSaladCommand command = new DeleteSaladCommand(fileManager1);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Список салатів порожній. Немає нічого для видалення."));

    }

    @Test
    public void test2() {
        System.setOut(new PrintStream(outContent));

        String input = "enter\n10\nenter\nenter\n1\nq";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Salad salad = new Salad();
        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        salad.addIngredient(availableVegetables[0], 12);

        FileManager fileManager2 = new FileManager("test3_for_overwriting");
        fileManager2.saveSalad(salad);

        DeleteSaladCommand command = new DeleteSaladCommand(fileManager2);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Салат успішно видалено."));

    }

}
