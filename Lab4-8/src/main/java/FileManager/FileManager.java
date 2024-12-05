package FileManager;

import Display.Main;
import Vegetables.*;
import Vegetables.Pepper;
import Vegetables.Cabbage;
import Salad.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileManager {
    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка при створенні файлу: " + e.getMessage());
        }
    }

    public List<Salad> loadSaladsFromFile() {
        List<Salad> saladsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Salad salad = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("Салат:")) {
                    if (salad != null && !salad.getIngredients().isEmpty()) {
                        saladsList.add(salad);
                    }
                    salad = new Salad();
                } else if (salad != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String vegetableName = parts[0].trim();
                        double weight = Double.parseDouble(parts[1].trim());
                        Vegetable vegetable = createVegetable(vegetableName);
                        salad.addIngredient(vegetable, weight);
                    }
                }
            }
            if (salad != null && !salad.getIngredients().isEmpty()) {
                saladsList.add(salad);
            }
        } catch (IOException e) {
            Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка читання з файлу: " + e.getMessage());
        }
        return saladsList;
    }

    public void saveSalad(Salad salad) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Салат:");
            List<Map.Entry<Vegetable, Double>> ingredients = new ArrayList<>(salad.getIngredients().entrySet());
            for (Map.Entry<Vegetable, Double> entry : ingredients) {
                writer.println(entry.getKey().getName() + ": " + entry.getValue());
            }
            writer.println();
        } catch (IOException e) {
            Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка запису у файл: " + e.getMessage());
        }
    }

        public void overwriteSalads(List<Salad> saladsList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Salad salad : saladsList) {
                writer.println("Салат:");
                List<Map.Entry<Vegetable, Double>> ingredients = new ArrayList<>(salad.getIngredients().entrySet());
                for (Map.Entry<Vegetable, Double> entry : ingredients) {
                    writer.println(entry.getKey().getName() + ": " + entry.getValue());
                }
                writer.println();
            }
        } catch (IOException e) {
            Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка перезапису файлу: " + e.getMessage());
        }
    }

    private Vegetable createVegetable(String name) {
        return switch (name.toLowerCase()) {
            case "авокадо" -> new Vegetable("Авокадо", 160, Arrays.asList("C", "E", "K", "B6"), 2.0, 9.0, 15.0, "кремовий");
            case "буряк" -> new Vegetable("Буряк", 37, Arrays.asList("C", "B9"), 1.5, 7.6, 0.1, "солодкий");
            case "брюсельська капуста" -> new Cabbage("Брюсельська капуста", 34, Arrays.asList("C", "K", "B6"), 4.0, 3.5, 0.5, "терпкий", "великий");
            case "морква" -> new Vegetable("Морква", 41, Arrays.asList("A", "K", "C", "B6"), 0.9, 9.6, 0.2, "солодкий");
            case "цвітна капуста" -> new Cabbage("Цвітна капуста", 21, Arrays.asList("C", "K", "B6"), 2.4, 2.3, 0.3, "гіркуватий", "середній");
            case "перець чілі" -> new Pepper("Перець Чілі", 40, Arrays.asList("C", "A", "B6"), 1.9, 9.0, 0.4, "гострий", 9);
            case "перець халапеньйо" -> new Pepper("Перець Халапеньйо", 29, Arrays.asList("C", "A", "B6", "E"), 0.9, 6.5, 0.4, "гострий", 6);
            case "пекінська капуста" -> new Cabbage("Пекінська капуста", 12, Arrays.asList("A", "C", "K"), 1.2, 3.2, 0.2, "гіркуватий", "малий");
            case "кукурудза" -> new Vegetable("Кукурудза", 97, Arrays.asList("C", "B1", "B5", "B9"), 3.0, 18.2, 1.2, "солодкий");
            case "огірок" -> new Vegetable("Огірок", 15, Arrays.asList("C", "K"), 0.7, 3.0, 0.1, "водянистий");
            case "цибуля" -> new Vegetable("Цибуля", 24, Arrays.asList("C", "B6"), 2.2, 5.7, 0.3, "кислий");
            case "редиска" -> new Vegetable("Редиска", 14, Arrays.asList("C", "B6"), 1.0, 4.4, 0.2, "гострий");
            default -> null;
        };
    }
}
