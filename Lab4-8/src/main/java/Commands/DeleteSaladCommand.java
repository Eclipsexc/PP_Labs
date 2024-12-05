package Commands;

import Display.ConsoleMenu;
import FileManager.FileManager;
import Salad.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class DeleteSaladCommand implements Command {
    private final FileManager fileManager;

    public DeleteSaladCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {

                ConsoleMenu.clearConsole();

                new DisplaySaladsListCommand(fileManager, scanner).execute();

                List<Salad> saladsList = fileManager.loadSaladsFromFile();
                if (saladsList.isEmpty()) {
                    System.out.println("Список салатів порожній. Немає нічого для видалення.");
                    return;
                }

                System.out.print("\nВведіть номер салату для видалення або \"q\" для виходу: ");
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("q")) {
                    return;
                }

                int index = Integer.parseInt(input) - 1; // Перетворення введення у число
                if (index < 0 || index >= saladsList.size()) {
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    System.out.println("Натисніть Enter для продовження.");
                    scanner.nextLine();
                    continue;
                }

                saladsList.remove(index);
                fileManager.overwriteSalads(saladsList);
                Logger LOGGER = LoggerFactory.getLogger(DeleteSaladCommand.class);
                LOGGER.info("Салат видалено з файлу.");

                System.out.println("Салат успішно видалено.");
                System.out.println("Натисніть Enter для продовження.");
                scanner.nextLine();
                return;
            }
        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(DeleteSaladCommand.class);
            LOGGER.error("Критична помилка у програмі", e);
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
