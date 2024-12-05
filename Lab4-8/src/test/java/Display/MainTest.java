package Display;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test() {
        System.setOut(new PrintStream(outContent));

        String input = "s\ns\n66\nw\nw\n\nenter\nw\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(null);
        String capturedOutput = outContent.toString().trim();

        assertTrue(capturedOutput.contains("Керування: W (вгору), S (вниз), Enter (вибір): "));
    }
}
