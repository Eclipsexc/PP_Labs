package Lab3.Actions;

import Lab3.Display.ConsoleMenu;
import Lab3.Droids.BlazeDroid;
import Lab3.Droids.Droid;
import Lab3.Droids.EngineerDroid;
import Lab3.Display.GameMap;
import Lab3.Replaying.PlaybackBattle;
import java.util.Scanner;
import Lab3.Display.DisplayUtils;

public class DroidActions {

    private ConsoleMenu consoleMenu;
    private GameMap gameMap;
    private Scanner scanner;
    private PlaybackBattle playbackBattle;
    private DisplayUtils displayUtils;

    public DroidActions(GameMap gameMap, ConsoleMenu consoleMenu, Scanner scanner, PlaybackBattle playbackBattle) {
        this.gameMap = gameMap;
        this.consoleMenu = consoleMenu;
        this.scanner = scanner;
        this.playbackBattle = playbackBattle;
        this.displayUtils = new DisplayUtils(scanner);
    }

    public void executeCommand(String command, Droid currentPlayerDroid, int[] currentPlayerPosition, boolean ifDuel, Droid computerDroid) {
        switch (command) {
            case "W":
                int stepsUp = moveDroid(currentPlayerDroid, currentPlayerPosition[0], currentPlayerPosition[1], 'W');
                currentPlayerPosition[1] -= stepsUp;
                if (ifDuel) playbackBattle.writeLog("Гравець перемістився W");
                break;
            case "A":
                int stepsLeft = moveDroid(currentPlayerDroid, currentPlayerPosition[0], currentPlayerPosition[1], 'A');
                currentPlayerPosition[0] -= stepsLeft;
                if (ifDuel) playbackBattle.writeLog("Гравець перемістився A");
                break;
            case "S":
                int stepsDown = moveDroid(currentPlayerDroid, currentPlayerPosition[0], currentPlayerPosition[1], 'S');
                currentPlayerPosition[1] += stepsDown;
                if (ifDuel) playbackBattle.writeLog("Гравець перемістився S");
                break;
            case "D":
                int stepsRight = moveDroid(currentPlayerDroid, currentPlayerPosition[0], currentPlayerPosition[1], 'D');
                currentPlayerPosition[0] += stepsRight;
                if (ifDuel) playbackBattle.writeLog("Гравець перемістився D");
                break;
            case "5":
                currentPlayerDroid.ability();
                if (ifDuel) playbackBattle.writeLog("Гравець активував суперздібність");
                break;
            case "0":
                currentPlayerDroid.deactivateAbility();
                if (ifDuel) playbackBattle.writeLog("Гравець деактивував суперздібність");
                break;
            case "4":
                shoot(currentPlayerPosition[0], currentPlayerPosition[1], 'A', currentPlayerDroid.getDamage(), computerDroid);
                if (ifDuel) playbackBattle.writeLog("Гравець вистрілив A");
                break;
            case "6":
                shoot(currentPlayerPosition[0], currentPlayerPosition[1], 'D', currentPlayerDroid.getDamage(), computerDroid);
                if (ifDuel) playbackBattle.writeLog("Гравець вистрілив D");
                break;
            case "8":
                shoot(currentPlayerPosition[0], currentPlayerPosition[1], 'W', currentPlayerDroid.getDamage(), computerDroid);
                if (ifDuel) playbackBattle.writeLog("Гравець вистрілив W");
                break;
            case "2":
                shoot(currentPlayerPosition[0], currentPlayerPosition[1], 'S', currentPlayerDroid.getDamage(), computerDroid);
                if (ifDuel) playbackBattle.writeLog("Гравець вистрілив S");
                break;
            case "Q":
                System.out.println("Вихід з битви.");
                if (ifDuel) playbackBattle.writeLog("Гравець вийшов з битви");
                return;
            default:
                System.out.println("Некоректна команда, спробуйте знову.");
                if (ifDuel) playbackBattle.writeLog("Гравець ввів некоректну команду");
        }
    }
    public int moveDroid(Droid droid, int currentX, int currentY, char direction) {
        int speed = droid.getSpeed();
        int actualSteps = 0;

        for (int step = 0; step < speed; step++) {
            int newX = currentX;
            int newY = currentY;

            switch (direction) {
                case 'W': newY--; break;
                case 'S': newY++; break;
                case 'A': newX--; break;
                case 'D': newX++; break;
            }

            if (newX <= 0 || newX >= gameMap.getWidth() || newY <= 0 || newY >= gameMap.getHeight() || gameMap.getMap()[newY][newX] == '#' || gameMap.getMap()[newY][newX] == '=') {
                System.out.println("Дроїд зіткнувся з перешкодою.");
                break;
            }

            if (gameMap.getMap()[newY][newX] == '*') {
                if (droid.getStars() >= 3) {
                    System.out.println("Дроїд підібрав вже необхідну к-сть зірок.");
                    break;
                } else {
                    droid.addStar();
                }
            }
            if (gameMap.getMap()[newY][newX] == '+') {
                droid.addGems();
                System.out.println("Дроїд підібрав кристал.");
                gameMap.getMap()[newY][newX] = '.';
                gameMap.decrementGems();
            }
            if (gameMap.getMap()[newY][newX] == 'b') {
                if (!(droid.isTypeOf("BlazeDroid"))) {
                    droid.setHealth(droid.getHealth() - 20);
                    System.out.println("Дроїд отримав опік");
                    scanner.nextLine();
                }
            }

            gameMap.getMap()[currentY][currentX] = '.';

            clearDroid(droid);
            updateMapWithDroid(droid, newX, newY);

            displayUtils.clearConsole();
            gameMap.displayMap(droid);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentX = newX;
            currentY = newY;
            actualSteps++;
        }

        displayUtils.clearConsole();
        return actualSteps;
    }

    private void updateMapWithDroid(Droid droid, int x, int y) {
        if (droid.isTypeOf("TankDroid")) {
            gameMap.getMap()[y][x] = 'T';
            if (droid.isAbilityActive()) {
                addShield(x, y);
            }
        } else if (droid.isTypeOf("SpyDroid")) {
            gameMap.getMap()[y][x] = droid.isAbilityActive() ? 's' : 'S';
        } else if (droid.isTypeOf("BlazeDroid")) {
            gameMap.getMap()[y][x] = 'B';
            moveBlazeDroid((BlazeDroid) droid, x, y);
        } else if (droid.isTypeOf("EngineerDroid")) {
            moveEngineerDroid((EngineerDroid) droid, x, y);
        } else {
            gameMap.getMap()[y][x] = 'D';
        }
    }

    public void shoot(int droidX, int droidY, char direction, int damage, Droid enemyDroid) {
        char bulletSymbol = (direction == 'W' || direction == 'S') ? '|' : '-';
        int dx = 0, dy = 0;

        switch (direction) {
            case 'W': dy = -1; break;
            case 'S': dy = 1; break;
            case 'A': dx = -1; break;
            case 'D': dx = 1; break;
        }

        int bulletX = droidX;
        int bulletY = droidY;
        byte hit = 0;

        while (true) {
            bulletX += dx;
            bulletY += dy;

            if (bulletX <= 0 || bulletX >= gameMap.getWidth() || bulletY <= 0 || bulletY >= gameMap.getHeight()) {
                break;
            }

            char cell = gameMap.getMap()[bulletY][bulletX];
            if (cell == '#' || cell == '=') {
                hit = 1;
                break;
            } else if (cell == 'S' || cell == 'T' || cell == 'E' || cell == 'B') {

                if (enemyDroid != null) {
                    if (enemyDroid.isTypeOf("SpyDroid") && enemyDroid.isAbilityActive()) {
                        enemyDroid.setHealth(enemyDroid.getHealth() - (damage / 2));
                    } else {
                        enemyDroid.setHealth(enemyDroid.getHealth() - damage);
                    }
                    System.out.println("\u001B[31mКуля влучила в " + enemyDroid.getName() + " та нанесла " + damage + " ураження!\u001B[0m");
                }
                hit = 2;
                break;
            }

            displayUtils.clearConsole();
            for (int y = 0; y < gameMap.getHeight(); y++) {
                for (int x = 0; x < gameMap.getWidth(); x++) {
                    if (x == bulletX && y == bulletY) {
                        System.out.print("\u001B[31m" + bulletSymbol + "\u001B[0m ");
                    } else {
                        System.out.print(gameMap.getMap()[y][x] + " ");
                    }
                }
                System.out.println();
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        displayUtils.clearConsole();
        gameMap.displayMap(enemyDroid);

        if (hit == 1) {
            System.out.println("\u001B[31mКуля попала в перешкоду!\u001B[0m");
        } else if (hit == 2) {
            System.out.println("\u001B[31mКуля попала в дроїда!\u001B[0m");
        } else {
            System.out.println("\u001B[31mКуля вилетіла за межі карти!\u001B[0m");
        }
        displayUtils.clearConsole();
    }

    private boolean moveBlazeDroid(BlazeDroid blazeDroid, int newX, int newY) {
        if (blazeDroid.isUltimateActive()) {
            blazeDroid.blazeDroidTrail.add(new int[]{newX, newY});

            if (blazeDroid.blazeDroidTrail.size() > BlazeDroid.MAX_TRAIL_LENGTH) {
                int[] oldPos = blazeDroid.blazeDroidTrail.poll();
                gameMap.getMap()[oldPos[1]][oldPos[0]] = '.';
            }

            for (int[] pos : blazeDroid.blazeDroidTrail) {
                gameMap.getMap()[pos[1]][pos[0]] = 'b';
            }
        }
        gameMap.getMap()[newY][newX] = 'B';
        return true;
    }

    private boolean moveEngineerDroid(EngineerDroid engineerDroid, int newX, int newY) {
        if (engineerDroid.isAbilityActive() && engineerDroid.isUltimateUsed() == 0) {

            if (engineerDroid.fail > 0) {
                System.out.println("Не вдалось звести паркан, перевірте чи достатньо місця навколо");
            }
            System.out.println("Суперздатність Інженера активована! Куди розмістити паркан?");
            System.out.println("0 - зверху");
            System.out.println("1 - знизу");
            System.out.println("2 - зліва");
            System.out.println("3 - справа");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    if (isWithinBounds(newX, newY - 1) && isWithinBounds(newX - 1, newY - 1) && isWithinBounds(newX + 1, newY - 1) &&
                            gameMap.getMap()[newY - 1][newX] == '.' && gameMap.getMap()[newY - 1][newX - 1] == '.' && gameMap.getMap()[newY - 1][newX + 1] == '.') {
                        gameMap.getMap()[newY - 1][newX] = '=';
                        gameMap.getMap()[newY - 1][newX - 1] = '=';
                        gameMap.getMap()[newY - 1][newX + 1] = '=';
                        System.out.println("Паркан розміщений зверху.");
                    } else {
                        System.out.println("Недостатньо місця для встановлення паркану.");
                        scanner.nextLine();
                        engineerDroid.fail++;
                        return false;
                    }
                    engineerDroid.ultimateUsed++;
                    engineerDroid.fail = 0;
                    return true;
                case 1:
                    if (isWithinBounds(newX, newY + 1) && isWithinBounds(newX - 1, newY + 1) && isWithinBounds(newX + 1, newY + 1) &&
                            gameMap.getMap()[newY + 1][newX] == '.' && gameMap.getMap()[newY + 1][newX - 1] == '.' && gameMap.getMap()[newY + 1][newX + 1] == '.') {
                        gameMap.getMap()[newY + 1][newX] = '=';
                        gameMap.getMap()[newY + 1][newX - 1] = '=';
                        gameMap.getMap()[newY + 1][newX + 1] = '=';
                        System.out.println("Паркан розміщений знизу.");
                    } else {
                        System.out.println("Недостатньо місця для встановлення паркану.");
                        scanner.nextLine();
                        engineerDroid.fail++;
                        return false;
                    }
                    engineerDroid.ultimateUsed++;
                    engineerDroid.fail = 0;
                    return true;
                case 2:
                    if (isWithinBounds(newX - 1, newY) && isWithinBounds(newX - 1, newY - 1) && isWithinBounds(newX - 1, newY + 1) &&
                            gameMap.getMap()[newY][newX - 1] == '.' && gameMap.getMap()[newY - 1][newX - 1] == '.' && gameMap.getMap()[newY + 1][newX - 1] == '.') {
                        gameMap.getMap()[newY - 1][newX - 1] = '=';
                        gameMap.getMap()[newY][newX - 1] = '=';
                        gameMap.getMap()[newY + 1][newX - 1] = '=';
                        System.out.println("Паркан розміщений зліва.");
                    } else {
                        System.out.println("Недостатньо місця для встановлення паркану.");
                        scanner.nextLine();
                        engineerDroid.fail++;
                        return false;
                    }
                    engineerDroid.ultimateUsed++;
                    engineerDroid.fail = 0;
                    return true;
                case 3:
                    if (isWithinBounds(newX + 1, newY) && isWithinBounds(newX + 1, newY - 1) && isWithinBounds(newX + 1, newY + 1) &&
                            gameMap.getMap()[newY][newX + 1] == '.' && gameMap.getMap()[newY - 1][newX + 1] == '.' && gameMap.getMap()[newY + 1][newX + 1] == '.') {
                        gameMap.getMap()[newY - 1][newX + 1] = '=';
                        gameMap.getMap()[newY][newX + 1] = '=';
                        gameMap.getMap()[newY + 1][newX + 1] = '=';
                        System.out.println("Паркан розміщений справа.");
                    } else {
                        System.out.println("Недостатньо місця для встановлення паркану.");
                        scanner.nextLine();
                        engineerDroid.fail++;
                        return false;
                    }
                    engineerDroid.ultimateUsed++;
                    engineerDroid.fail = 0;
                    return true;
                default:
                    System.out.println("Некоректний вибір.");
                    return false;
            }
        } else {
            if (isWithinBounds(newX, newY) && gameMap.getMap()[newY][newX] == '.') {
                clearDroid(engineerDroid);
                gameMap.getMap()[newY][newX] = 'E';
                return true;
            } else {
                System.out.println("Переміщення неможливе: клітинка зайнята чи недоступна.");
                return false;
            }
        }
    }

    private void addShield(int centerX, int centerY) {
        for (int y = centerY - 1; y <= centerY + 1; y++) {
            for (int x = centerX - 1; x <= centerX + 1; x++) {
                if (isWithinBounds(x, y) && gameMap.getMap()[y][x] == '.') {
                    gameMap.getMap()[y][x] = 't';
                }
            }
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < gameMap.getWidth() && y >= 0 && y < gameMap.getHeight();
    }

    public void addDroid(Droid droid, char[][] map, int x, int y) {
        if (isWithinBounds(map, x, y)) {
            if (droid.isTypeOf("TankDroid")) {
                map[y][x] = 'T';
            } else if (droid.isTypeOf("SpyDroid")) {
                map[y][x] = 'S';
            } else if (droid.isTypeOf("BlazeDroid")) {
                map[y][x] = 'B';
            } else if (droid.isTypeOf("EngineerDroid")) {
                map[y][x] = 'E';
            } else {
                map[y][x] = 'D';
            }
        } else {
            System.out.println("Неможливо добавити дроїда. Координати за межами мапи");
        }
    }

    public void clearDroid(Droid droid) {
        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                char cell = gameMap.getMap()[y][x];

                if (droid.isTypeOf("EngineerDroid") && cell == 'E') {
                    continue;
                }

                if ((droid.isTypeOf("TankDroid") && cell == 'T') ||
                        (droid.isTypeOf("SpyDroid") && (cell == 'S' || cell == 's')) ||
                        (droid.isTypeOf("BlazeDroid") && cell == 'B') ||
                        (droid.isTypeOf("Droid") && cell == 'D') ||
                        (droid.isTypeOf("EngineerDroid") && !droid.isAbilityActive() && cell == '=')) {

                    if (gameMap.getMap()[y][x] != '*') {
                        gameMap.getMap()[y][x] = '.';
                    }
                }

                if (gameMap.getMap()[y][x] == 't') {
                    gameMap.getMap()[y][x] = '.';
                }
            }
        }
    }

    private boolean isWithinBounds(char[][] map, int x, int y) {
        return x >= 0 && x < map[0].length && y >= 0 && y < map.length;
    }
}
