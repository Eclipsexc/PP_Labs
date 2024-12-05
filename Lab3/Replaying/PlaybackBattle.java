package Lab3.Replaying;

import Lab3.Display.ConsoleMenu;
import Lab3.Display.DisplayUtils;
import Lab3.Actions.DroidActions;
import Lab3.Droids.*;
import Lab3.Display.GameMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaybackBattle {

    public final String LOG_FILE_PATH = "C:\\VSprojects\\RoZum1\\Lab3\\Replaying\\Battle_log.txt";
    private ConsoleMenu consoleMenu;
    private DisplayUtils displayUtils;
    private GameMap gameMap;
    private Scanner scanner;

    public PlaybackBattle(Scanner scanner, GameMap gameMap) {
        this.consoleMenu = new ConsoleMenu(scanner);
        this.gameMap = gameMap;
        this.scanner = scanner;
        this.displayUtils = new DisplayUtils(scanner);
    }

    public void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Виникла помилка при записі у файл: " + e.getMessage());
        }
    }

    public void clearLogFile() {
        try (PrintWriter writer = new PrintWriter(LOG_FILE_PATH)) {
            writer.print("");
        } catch (IOException e) {
            System.out.println("Виникла помилка при очищенні файлу: " + e.getMessage());
        }
    }

    public String[] readLogFromFile() {
        ArrayList<String> logEntries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logEntries.toArray(new String[0]);
    }

    public void simulateBattleFromLog(String[] battleLog) {
        loadStarPositionsFromFile(LOG_FILE_PATH);
        Droid playerDroid = null;
        Droid computerDroid = null;

        int[] playerPosition = {2, 2};
        int[] computerPosition = {7, 7};

        char lastPlayerDirection = ' ';
        char lastComputerDirection = ' ';

        DroidActions droidActions = new DroidActions(gameMap, consoleMenu, scanner, this);

        for (String logEntry : battleLog) {
            if (logEntry.startsWith("Гравець обрав дроїда типу:")) {
                String droidName = logEntry.split(": ")[1];
                playerDroid = createDroid(droidName);

            } else if (logEntry.startsWith("Для противника обрано дроїд типу:")) {
                String droidName = logEntry.split(": ")[1];
                computerDroid = createDroid(droidName);

            } else if (logEntry.startsWith("Гравець перемістився")) {
                char direction = logEntry.charAt(logEntry.length() - 1);
                lastPlayerDirection = direction;

                int steps = droidActions.moveDroid(playerDroid, playerPosition[0], playerPosition[1], direction);
                updatePosition(playerPosition, direction, steps);
                System.out.println("Гравець перемістився " + direction);

            } else if (logEntry.startsWith("Противник перемістився")) {
                char direction = logEntry.charAt(logEntry.length() - 1);
                lastComputerDirection = direction;

                int steps = droidActions.moveDroid(computerDroid, computerPosition[0], computerPosition[1], direction);
                updatePosition(computerPosition, direction, steps);
                System.out.println("Комп'ютер пішов " + direction);

            } else if (logEntry.startsWith("Гравець вистрілив")) {
                char direction = logEntry.charAt(logEntry.length() - 1);
                droidActions.shoot(playerPosition[0], playerPosition[1], direction, playerDroid.getDamage(), computerDroid);

            } else if (logEntry.startsWith("Противник вистрілив")) {
                char direction = logEntry.charAt(logEntry.length() - 1);
                droidActions.shoot(computerPosition[0], computerPosition[1], direction, computerDroid.getDamage(), playerDroid);

            } else if (logEntry.startsWith("Гравець активував суперздібність")) {
                playerDroid.ability();
                System.out.println("Гравець активував суперздібність!");

            } else if (logEntry.startsWith("Гравець деактивував суперздібність")) {
                playerDroid.deactivateAbility();
                System.out.println("Гравець деактивував суперздібність!");

            } else if (logEntry.startsWith("Противник переміг")) {
                displayUtils.printColorfulTextUntilKeyPress("Противник переміг!", 500);
                break;
            } else if (logEntry.startsWith("Гравець переміг")) {
                displayUtils.printColorfulTextUntilKeyPress("Гравець переміг!", 500);
                break;
            }
        }
    }

    private void updatePosition(int[] position, char direction, int steps) {
        switch (direction) {
            case 'W':
                position[1] -= steps;
                break;
            case 'A':
                position[0] -= steps;
                break;
            case 'S':
                position[1] += steps;
                break;
            case 'D':
                position[0] += steps;
                break;
        }
    }

    private Droid createDroid(String droidName) {
        switch (droidName) {
            case "BlazeDroid":
                return new BlazeDroid("BlazeDroid");
            case "SpyDroid":
                return new SpyDroid("SpyDroid");
            case "TankDroid":
                return new TankDroid("TankDroid");
            case "EngineerDroid":
                return new EngineerDroid("EngineerDroid");
            default:
                throw new IllegalArgumentException("Unknown droid type: " + droidName);
        }
    }

    public void saveStarPositionsToFile(List<int[]> starPositions, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int[] star : starPositions) {
                writer.write(star[0] + "," + star[1]);
                writer.newLine();
            }
            System.out.println("Зірки збережено у файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Помилка під час збереження зірок: " + e.getMessage());
        }
    }

    public void loadStarPositionsFromFile(String fileName) {
        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                if (gameMap.getMap()[y][x] == '*') {
                    gameMap.getMap()[y][x] = '.';
                }
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(",")) {
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);

                    if (x >= 0 && x < gameMap.getWidth() && y >= 0 && y < gameMap.getHeight()) {
                        gameMap.getMap()[y][x] = '*';
                    }
                }
            }
            System.out.println("Зірки завантажено з файлу: " + fileName);
        } catch (IOException e) {
            System.err.println("Помилка під час завантаження зірок: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Помилка формату числа: " + e.getMessage());
        }
    }
}
