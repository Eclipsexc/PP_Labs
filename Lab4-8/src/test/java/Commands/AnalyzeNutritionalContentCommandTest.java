package Commands;

import FileManager.FileManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzeNutritionalContentCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test1() {
        System.setOut(new PrintStream(outContent));

        String input = "enter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        FileManager fileManager1 = new FileManager("test2_empty");
        AnalyzeNutritionalContentCommand command = new AnalyzeNutritionalContentCommand(fileManager1);
        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Список салатів порожній."));

    }

    @Test
    public void test2() {
        System.setOut(new PrintStream(outContent));

        FileManager fileManager1 = new FileManager("salads");
        AnalyzeNutritionalContentCommand command = new AnalyzeNutritionalContentCommand(fileManager1);

        String input = "enter\n50\n1\nenter";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        command.execute();

        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Некоректний вибір. Введіть номер салату ще раз."));
        assertTrue(capturedOutput.contains("Калорійність: "));
        assertTrue(capturedOutput.contains("Вітаміни, що містяться у салаті: "));
    }
}
