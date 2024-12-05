package Display;

import FileManager.FileManager;
import Salad.*;
import Vegetables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import org.slf4j.*;

public class Main {

    public static void main(String[] args) {
        Logger LOGGER = LoggerFactory.getLogger(Main.class);

        try {
            throw new Exception("tst");
        } catch (Exception e) {
            LOGGER.error("Test email", new RuntimeException("SMTP Test Exception"));
        }
        LOGGER.info("Початок програми");
        List<Salad> saladsList = new ArrayList<>();
        Vegetable[] availableVegetables = {
                new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий"),
                new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий"),
                new Cabbage("Брюсельська капуста", 34, Arrays.asList("C", "K", "B6"), 4.0, 3.5, 0.5, "терпкий", "великий"),
                new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий"),
                new Cabbage("Цвітна капуста", 21, Arrays.asList("C", "K", "B6"), 2.4, 2.3, 0.3, "гіркуватий", "середній"),
                new Pepper("Перець Чілі", 40, Arrays.asList("C", "A", "B6"), 1.9, 9.0, 0.4, "гострий", 9),
                new Pepper("Перець Халапеньйо", 29, Arrays.asList("C", "A", "B6", "E"), 0.9, 6.5, 0.4, "гострий", 6),
                new Cabbage("Пекінська капуста", 12, Arrays.asList("A", "C", "K"), 1.2, 3.2, 0.2, "гіркуватий", "малий"),
                new Vegetable("Кукурудза", 97, Arrays.asList("C", "B1", "B5", "B9"), 3.0, 18.2, 1.2, "солодкий"),
                new Vegetable("Огірок", 15, Arrays.asList("C", "K"), 0.7, 3.0, 0.1, "водянистий"),
                new Vegetable("Цибуля", 24, Arrays.asList("C", "B6"), 2.2, 5.7, 0.3, "кислий"),
                new Vegetable("Редиска", 14, Arrays.asList("C", "B6"), 1.0, 4.4, 0.2, "гострий")
        };

        FileManager fileManager = new FileManager("salads");

        Scanner scanner = new Scanner(System.in);
        ConsoleMenu menu = new ConsoleMenu(saladsList, availableVegetables, fileManager, scanner);
        menu.run();

        LOGGER.info("Кінець програми");
    }
}