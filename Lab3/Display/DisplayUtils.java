package Lab3.Display;
import Lab3.Droids.Droid;
import java.util.Scanner;

public class DisplayUtils {

    public final String RESET = "\u001B[0m";
    public final String PURPLE = "\u001B[35m";
    public final String GREEN = "\u001B[32m";
    public final String RED = "\u001B[31m";
    public final String BLUE = "\u001B[34m";
    public final String YELLOW = "\u001B[33m";
    public final String CYAN = "\u001B[36m";

    public final String[] COLORS = { RED, GREEN, YELLOW, BLUE, PURPLE, CYAN };

    private Scanner scanner;

    public DisplayUtils(Scanner scanner) { this.scanner = scanner;}

    public void displayDroidInfo(Droid droid) {
        System.out.println(PURPLE + "======== " + RESET + droid.getName() + " " + PURPLE + "========" + RESET);
        System.out.println(GREEN + "Рівень здоров'я: " + RESET + droid.getHealth());
        System.out.println(RED + "Ураження: " + RESET + droid.getDamage());
        System.out.println(BLUE + "Миттєва швидкість: " + RESET + droid.getSpeed());
        System.out.println(YELLOW + "Суперздібність: " + RESET + droid.getAbilityName());
        System.out.println(CYAN + "Опис: " + RESET + "\n" + droid.getDescription());
    }

    public void printColorfulTextUntilKeyPress(String text, int delay) {
        Thread animationThread = new Thread(() -> {
            int colorIndex = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    clearConsole();
                    System.out.println(COLORS[colorIndex] + "\t\t\t" + text + RESET);
                    Thread.sleep(delay);
                    colorIndex = (colorIndex + 1) % COLORS.length;
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        animationThread.start();
        scanner.nextLine();
        animationThread.interrupt();
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayStatus(Droid[] playerDroids, Droid[] computerDroids) {
        int totalPlayerGems = playerDroids[0].getGems() + playerDroids[1].getGems();
        int totalComputerGems = computerDroids[0].getGems() + computerDroids[1].getGems();

        System.out.println("\u001B[36m=============================================\u001B[0m");
        System.out.println("\u001B[36m|              СТАТУС ГРАВЦІВ               |\u001B[0m");
        System.out.println("\u001B[36m=============================================\u001B[0m");
        for (Droid playerDroid : playerDroids) {
            System.out.printf("| \u001B[32m%-20s\u001B[0m | Здоров'я: \u001B[31m%-4d\u001B[0m | Кристали: \u001B[33m%-2d\u001B[0m |\n",
                    playerDroid.getName(), playerDroid.getHealth(), playerDroid.getGems());
        }
        System.out.println("\u001B[36m=============================================\u001B[0m");
        System.out.printf("| \u001B[32m%-27s\u001B[0m | %10s: \u001B[33m%-2d\u001B[0m |\n", "Загальні кристали (Гравці)", "Total", totalPlayerGems);
        System.out.println("\u001B[36m=============================================\u001B[0m");

        System.out.println("\u001B[31m|            СТАТУС ПРОТИВНИКІВ             |\u001B[0m");
        System.out.println("\u001B[31m=============================================\u001B[0m");
        for (Droid computerDroid : computerDroids) {
            System.out.printf("| \u001B[35m%-20s\u001B[0m | Здоров'я: \u001B[31m%-4d\u001B[0m | Кристали: \u001B[33m%-2d\u001B[0m |\n",
                    computerDroid.getName(), computerDroid.getHealth(), computerDroid.getGems());
        }
        System.out.println("\u001B[31m=============================================\u001B[0m");
        System.out.printf("| \u001B[35m%-27s\u001B[0m | %10s: \u001B[33m%-2d\u001B[0m |\n", "Загальні кристали (Комп'ютер)", "Total", totalComputerGems);
        System.out.println("\u001B[31m=============================================\u001B[0m");
    }
}
