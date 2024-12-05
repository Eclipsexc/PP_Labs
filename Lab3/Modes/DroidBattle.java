package Lab3.Modes;

import Lab3.Actions.ComputerAction;
import Lab3.Actions.DroidActions;
import Lab3.Display.ConsoleMenu;
import Lab3.Display.DisplayUtils;
import Lab3.Display.GameMap;
import Lab3.Droids.Droid;
import Lab3.Replaying.PlaybackBattle;

import java.util.List;
import java.util.Scanner;

public class DroidBattle {
    private GameMap gameMap;
    private PlaybackBattle playbackBattle;
    private ConsoleMenu consoleMenu;
    private Scanner scanner;
    private DroidActions droidActions;
    private ComputerAction computerAction;
    private DisplayUtils displayUtils;

    public DroidBattle(Scanner scanner, int mapType) {
        this.scanner = scanner;
        if (mapType == 1) {
            this.gameMap = new GameMap(10, 10, 1);
        } else if (mapType == 2) {
            this.gameMap = new GameMap(12, 12, 2);
        }
        this.playbackBattle = new PlaybackBattle(scanner, gameMap);
        this.consoleMenu = new ConsoleMenu(scanner);
        this.droidActions = new DroidActions(gameMap, consoleMenu, scanner, playbackBattle);
        this.computerAction = new ComputerAction(droidActions, playbackBattle);
        this.displayUtils = new DisplayUtils(scanner);
    }

    public void startOneOnOneBattle() {
        playbackBattle.clearLogFile();
        playbackBattle.saveStarPositionsToFile(gameMap.getStarPositions(), playbackBattle.LOG_FILE_PATH);
        displayUtils.clearConsole();

        System.out.println("Оберіть свого дроїда для битви:");
        scanner.nextLine();
        Droid playerDroid = consoleMenu.selectDroidWithInfo();
        playbackBattle.writeLog("Гравець обрав дроїда типу: " + playerDroid.getName());

        displayUtils.clearConsole();
        Droid computerDroid = null;
        while (computerDroid == null) {
            displayUtils.clearConsole();
            System.out.println("Тепер оберіть дроїда-опонента:");
            scanner.nextLine();
            computerDroid = consoleMenu.selectDroidWithInfo();
            if (computerDroid.getName().equals(playerDroid.getName())) {
                System.out.println("Цей дроїд вже обраний для гравця. Спробуйте обрати іншого.");
                scanner.nextLine();
                computerDroid = null;
            }
        }
        playbackBattle.writeLog("Для противника обрано дроїд типу: " + computerDroid.getName());
        displayUtils.clearConsole();

        Droid[] droids = {playerDroid, computerDroid};

        int[] playerPosition = {2, 2};
        int[] computerPosition = {7, 7};
        droidActions.addDroid(playerDroid, gameMap.getMap(), playerPosition[0], playerPosition[1]);
        droidActions.addDroid(computerDroid, gameMap.getMap(), computerPosition[0], computerPosition[1]);
        gameMap.displayMap(playerDroid);

        List<int[]> starPositions = gameMap.getStarPositions();
        char lastDirection = ' ';
        String command;

        while (true) {
            playerDroid.displayFighterInfo();
            computerDroid.displayFighterInfo();

            if (!playerDroid.isAlive()) {
                playbackBattle.writeLog("Противник переміг.");
                displayUtils.printColorfulTextUntilKeyPress("Противник переміг!", 500);
                displayUtils.clearConsole();
                break;
            }
            if (!computerDroid.isAlive()) {
                playbackBattle.writeLog("Гравець переміг.");
                displayUtils.printColorfulTextUntilKeyPress("Гравець переміг!", 500);
                displayUtils.clearConsole();
                break;
            }

            System.out.print("Введіть команду (W/A/S/D для переміщення, 5 для ульти, 0 для відключення ульти, 2/4/6/8 для пострілу, Q для виходу): ");
            command = scanner.nextLine().toUpperCase();

            droidActions.executeCommand(command, playerDroid, playerPosition, true, computerDroid);

            displayUtils.clearConsole();
            computerAction.computerAIAction(computerDroid, computerPosition, starPositions, lastDirection, playerPosition, playerDroid);
            gameMap.displayMap(playerDroid);
        }
    }

    public void startTwoOnTwoBattle() {
        gameMap.spawnCrystals(5);
        displayUtils.clearConsole();

        System.out.println("Створення власної команди");
        scanner.nextLine();
        displayUtils.clearConsole();

        Droid[] playerDroids = new Droid[2];
        for (int i = 0; i < 2; i++) {
            System.out.println("Оберіть дроїда вашої команди для гравця " + (i + 1) + ":");
            Droid selectedDroid = consoleMenu.selectDroidWithInfo();

            while (isDroidSelected(playerDroids, selectedDroid)) {
                System.out.println("Цей дроїд вже обраний. Спробуйте обрати іншого.");
                scanner.nextLine();
                selectedDroid = consoleMenu.selectDroidWithInfo();
            }

            playerDroids[i] = selectedDroid;
            for (int j = 0; j < 3; j++) {
                playerDroids[i].addStar();
            }
        }

        System.out.println("Формування команди противника");
        scanner.nextLine();
        displayUtils.clearConsole();

        Droid[] computerDroids = new Droid[2];
        for (int i = 0; i < 2; i++) {
            displayUtils.clearConsole();
            System.out.println("Тепер оберіть дроїда-опонента " + (i + 1) + ":");
            Droid selectedDroid = consoleMenu.selectDroidWithInfo();

            while (isDroidSelected(playerDroids, selectedDroid) || isDroidSelected(computerDroids, selectedDroid)) {
                System.out.println("Цей дроїд вже обраний. Спробуйте обрати іншого.");
                scanner.nextLine();
                selectedDroid = consoleMenu.selectDroidWithInfo();
            }

            computerDroids[i] = selectedDroid;
            for (int j = 0; j < 3; j++) {
                computerDroids[i].addStar();
            }
        }

        int[][] playerPositions = {{3, 1}, {3, 2}};
        int[][] computerPositions = {{8, 6}, {8, 7}};

        for (int i = 0; i < 2; i++) {
            droidActions.addDroid(playerDroids[i], gameMap.getMap(), playerPositions[i][0], playerPositions[i][1]);
            droidActions.addDroid(computerDroids[i], gameMap.getMap(), computerPositions[i][0], computerPositions[i][1]);
        }

        List<int[]> starPositions = gameMap.getStarPositions();
        char lastDirection = ' ';

        while (true) {
            boolean playerAlive = true;
            boolean computerAlive = true;

            int playerAliveCount = 0;
            for (Droid playerDroid : playerDroids) {
                if (playerDroid.isAlive()) {
                    playerAliveCount++;
                }
            }

            int computerAliveCount = 0;
            for (Droid computerDroid : computerDroids) {
                if (computerDroid.isAlive()) {
                    computerAliveCount++;
                }
            }

            if (playerAliveCount == 0) {
                playerAlive = false;
            }
            if (computerAliveCount == 0) {
                computerAlive = false;
            }

            if (!playerAlive || (computerDroids[0].getGems() + computerDroids[1].getGems() == 10)) {
                displayUtils.printColorfulTextUntilKeyPress("Команда противників перемогла!", 500);
                scanner.nextLine();
                break;
            }
            if (!computerAlive || (playerDroids[0].getGems() + playerDroids[1].getGems() == 2)) {
                displayUtils.printColorfulTextUntilKeyPress("Ваша команда перемогла!", 500);
                scanner.nextLine();
                break;
            }

            displayUtils.clearConsole();

            for (int i = 0; i < 2; i++) {
                playerDroids[i].setCurrentPlayer(true);
                Droid currentPlayerDroid = playerDroids[i];
                int[] currentPlayerPosition = playerPositions[i];


                displayUtils.displayStatus(playerDroids, computerDroids);

                gameMap.displayMap(currentPlayerDroid);

                System.out.print("Гравець " + (i + 1) + " (" + currentPlayerDroid.getName() + "), введіть команду (W/A/S/D для переміщення, 5 для ульти, 0 для відключення ульти, 2/4/6/8 для пострілу, Q для виходу): ");
                String command = scanner.nextLine().toUpperCase();
                droidActions.executeCommand(command, currentPlayerDroid, currentPlayerPosition, false, computerDroids[1 - i]);

                displayUtils.clearConsole();

                if (gameMap.getCrystalCount() == 0) {
                    gameMap.spawnCrystals(5);
                }

                for (int j = 0; j < computerDroids.length; j++) {
                    computerAction.computerAIAction(computerDroids[j], computerPositions[j], starPositions, lastDirection, currentPlayerPosition, currentPlayerDroid);
                }

                playerDroids[i].setCurrentPlayer(false);
            }
        }
    }

    private boolean isDroidSelected(Droid[] droids, Droid selectedDroid) {
        for (Droid droid : droids) {
            if (droid != null && droid.getName().equals(selectedDroid.getName())) {
                return true;
            }
        }
        return false;
    }
}
