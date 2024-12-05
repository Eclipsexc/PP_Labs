package FileManager;

import Salad.Salad;
import Vegetables.Vegetable;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileManagerTest {
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    @Test
    public void test1() {
        System.setErr(new PrintStream(errorStream));

        FileManager fileManager1 = new FileManager(null);

        String capturedOutput = errorStream.toString().trim();
        assertTrue(capturedOutput.contains("Помилка при створенні файлу:"));
    }

    @Test
    public void test2() {
        System.setErr(new PrintStream(errorStream));

        FileManager fileManager1 = new FileManager("test5_3_salads");
        List<Salad> saladsList = fileManager1.loadSaladsFromFile();

        assertEquals(3, saladsList.size());
    }

    @Test
    public void test3() {

        Salad salad = new Salad();
        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        salad.addIngredient(availableVegetables[0], 12);

        File file = new File("test_for_writing");
        file.delete();

        FileManager fileManager1 = new FileManager("test_for_writing");
        fileManager1.saveSalad(salad);

        List<Salad> saladsList = fileManager1.loadSaladsFromFile();

        assertEquals(1, saladsList.size());
    }

    @Test
    public void test4() {

        Salad salad = new Salad();
        Vegetable[] availableVegetables = new Vegetable[]{
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
        };

        List<Salad> saladsList1 = Arrays.asList(salad);

        salad.addIngredient(availableVegetables[0], 12);

        File file = new File("test_for_writing");
        file.delete();

        FileManager fileManager1 = new FileManager("test_for_writing");
        fileManager1.overwriteSalads(saladsList1);

        List<Salad> saladsList2 = fileManager1.loadSaladsFromFile();

        assertEquals(1, saladsList2.size());
    }
}
