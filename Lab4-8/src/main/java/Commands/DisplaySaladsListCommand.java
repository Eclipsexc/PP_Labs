package Commands;

import Display.ConsoleMenu;
import FileManager.FileManager;
import Salad.ShowSalad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class DisplaySaladsListCommand implements Command {
    private FileManager fileManager;
    private Scanner scanner;

    public DisplaySaladsListCommand(FileManager fileManager, Scanner scanner) {
        this.fileManager = fileManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            ConsoleMenu.clearConsole();
            var saladsList = fileManager.loadSaladsFromFile();

            if (saladsList.isEmpty()) {
                System.out.println("Список салатів порожній.");
            } else {
                System.out.println("Список салатів:");

                for (int i = 0; i < saladsList.size(); i++) {
                    System.out.println("\nСалат #" + (i + 1));
                    ShowSalad.displaySaladInfo(saladsList.get(i));
                }
            }
            System.out.println("\nНатисніть Enter для продовження.");
            scanner.nextLine();
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(DisplaySaladsListCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}