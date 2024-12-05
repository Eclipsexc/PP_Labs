package Lab3.Display;

import Lab3.Modes.DroidBattle;
import Lab3.Droids.*;
import Lab3.Replaying.PlaybackBattle;
import java.util.Scanner;

public class ConsoleMenu {
    private Scanner scanner;
    private DisplayUtils displayUtils;

    public ConsoleMenu(Scanner scanner) {
        this.scanner = scanner;
        this.displayUtils = new DisplayUtils(scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleMenu menu = new ConsoleMenu(scanner);
        menu.runMenu();
    }

    public void runMenu() {
        String input;
        int selectedIndex = 0;
        displayUtils.printColorfulTextUntilKeyPress("Битва Дроїдів!", 500);
        displayUtils.clearConsole();

        String[] options = {
                "Запустити Дуель (1 на 1)",
                "Запустити \"Захоплення кристалів\"",
                "Відтворити останню дуель",
                "Вихід"
        };

        do {
            displayUtils.clearConsole();
            displayMenu(options, selectedIndex);

            System.out.print("Натисніть 'w' (вверх), 's' (вниз), 'Enter' (вибір): ");
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex > 0) {
                        selectedIndex--;
                    }
                    break;
                case "s":
                    if (selectedIndex < options.length - 1) {
                        selectedIndex++;
                    }
                    break;
                case "":
                    handleSelection(selectedIndex);
                    break;
                default:
                    System.out.println("Некоректний вибір, спробуйте ще раз.");
            }
        } while (true);
    }

    private void handleSelection(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                DroidBattle droidBattleOneOnOne = new DroidBattle(scanner, 1);
                droidBattleOneOnOne.startOneOnOneBattle();
                break;
            case 1:
                DroidBattle droidBattleTwoOnTwo = new DroidBattle(scanner, 2);
                droidBattleTwoOnTwo.startTwoOnTwoBattle();
                break;
            case 2:
                GameMap gameMap = new GameMap(10, 10, 1);
                PlaybackBattle playbackBattle = new PlaybackBattle(scanner, gameMap);
                String[] battleLog = playbackBattle.readLogFromFile();
                playbackBattle.simulateBattleFromLog(battleLog);
                break;
            default:
                System.out.println("Вихід із програми");
                System.exit(0);
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

    public Droid selectDroidWithInfo() {
        Droid[] availableDroids = {
                new TankDroid("TankDroid"),
                new SpyDroid("SpyDroid"),
                new BlazeDroid("BlazeDroid"),
                new EngineerDroid("EngineerDroid")
        };

        int currentIndex = 0;
        while (true) {
            displayUtils.clearConsole();
            displayUtils.displayDroidInfo(availableDroids[currentIndex]);

            System.out.println("\nНатисніть \"w\" для перегляду попереднього дроїда, \"s\" для наступного дроїда, \"Enter\" для підтвердження вибору.");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (currentIndex > 0) {
                        currentIndex--;
                    }
                    break;
                case "s":
                    if (currentIndex < availableDroids.length - 1) {
                        currentIndex++;
                    }
                    break;
                case "":
                    return availableDroids[currentIndex];
                default:
                    System.out.println("Некоректний вибір, спробуйте знову.");
            }
        }
    }
}
