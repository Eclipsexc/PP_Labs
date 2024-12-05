package Display;

import Commands.*;
import FileManager.FileManager;
import Vegetables.Vegetable;
import Salad.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConsoleMenu {
    private  Map<Integer, Command> commands;
    private  List<Salad> saladsList;
    private  Vegetable[] availableVegetables;
    private  FileManager fileManager;
    private  Scanner scanner;

    public ConsoleMenu(List<Salad> saladsList, Vegetable[] availableVegetables, FileManager fileManager, Scanner scanner) {
        this.saladsList = saladsList;
        this.availableVegetables = availableVegetables;
        this.fileManager = fileManager;
        this.scanner = scanner;

        commands = new HashMap<>();
        commands.put(1, new DisplayAvailableVegetablesCommand(availableVegetables, true, scanner));
        commands.put(2, new MakeSaladCommand(availableVegetables, fileManager));
        commands.put(3, new SortVegetablesCommand(Arrays.copyOf(availableVegetables, availableVegetables.length)));
        commands.put(4, new SearchVegetableCommand(availableVegetables));
        commands.put(5, new DisplaySaladsListCommand(fileManager, scanner));
        commands.put(6, new DeleteSaladCommand(fileManager));
        commands.put(7, new AnalyzeNutritionalContentCommand(fileManager));
        commands.put(8, new ExitCommand());
    }

    public void executeCommand(int option) {
        Command command = commands.get(option);
        if (command != null) {
            clearConsole();
            command.execute();
        } else {
            System.out.println("Некоректний вибір. Спробуйте ще раз.");
        }
    }

    public void run() {
        String[] options = {
                "Переглянути доступні овочі",
                "Зробити салат",
                "Сортування овочів",
                "Пошук овоча",
                "Список салатів",
                "Видалити салат",
                "Аналіз поживних речовин",
                "Вийти"
        };
        int selectedIndex = 0;

        Boolean isWorking = true;

        while (isWorking) {
            clearConsole();
            displayMenu(options, selectedIndex);

            System.out.print("Керування: W (вгору), S (вниз), Enter (вибір): ");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    selectedIndex = (selectedIndex > 0) ? selectedIndex - 1 : options.length - 1;
                    break;
                case "s":
                    selectedIndex = (selectedIndex < options.length - 1) ? selectedIndex + 1 : 0;
                    break;
                case "":
                    executeCommand(selectedIndex + 1);
                    if (selectedIndex + 1 == 8) {
                        isWorking = false;
                    }
                    break;
                default:
                    System.out.println("Некоректний ввід. Вкористовуйте W, S або Enter.");
            }
        }
    }

    private void displayMenu(String[] options, int selectedIndex) {
        System.out.println("Оберіть варіант:");
        for (int i = 0; i < options.length; i++) {
            if (i == selectedIndex) {
                System.out.println("--> " + options[i]);
            } else {
                System.out.println("    " + options[i]);
            }
        }
    }

    public static void clearConsole() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        try {
            if (operatingSystem.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(ConsoleMenu.class);
            System.out.println("Не вдалось очистити консоль: " + e.getMessage());
            LOGGER.error("Критична помилка у програмі", e);
        }
    }
}
