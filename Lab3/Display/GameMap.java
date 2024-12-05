package Lab3.Display;

import Lab3.Droids.Droid;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class GameMap {
    private char[][] map;
    private int width;
    private int height;
    private int maxStars = 6;
    private int currentStars = 0;
    private int gemsCounter = 0;
    private List<int[]> starPositions = new ArrayList<>();
    private Random random = new Random();
    private ConsoleMenu consoleMenu;
    private Scanner scanner;

    public GameMap(int width, int height, int MapType) {
        this.width = width;
        this.height = height;
        this.map = new char[height][width];
        this.scanner = new Scanner(System.in);
        this.consoleMenu = new ConsoleMenu(scanner);
        initializeMap(MapType);
        if( MapType == 1 ) { spawnStars(); }
    }
    private void initializeMap(int mapType) {
        if (mapType == 1) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                        map[y][x] = '#';
                    } else {
                        map[y][x] = '.';
                    }
                }
            }
        } else if (mapType == 2) {
            char[][] sketch = {
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                    {'#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '#'},
                    {'#', '.', '.', '#', '.', '.', '#', '.', '.', '.', '.', '#'},
                    {'#', '#', '#', '#', '.', '.', '#', '.', '.', '.', '.', '#'},
                    {'#', '#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '#'},
                    {'#', '.', '.', '#', '#', '.', '.', '.', '.', '.', '#', '#'},
                    {'#', '.', '.', '.', '#', '.', '.', '.', '#', '#', '#', '#'},
                    {'#', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '#'},
                    {'#', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '#'},
                    {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
            };
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    map[y][x] = sketch[y][x];
                }
            }
        }
    }
    public int getHeight() { return height; }
    public int getWidth() { return width; }

    public char[][] getMap() { return map; }

    public List<int[]> getStarPositions() { return starPositions; }

    private void spawnStars() {
        while (currentStars < maxStars) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;

            if (map[y][x] == '.') {
                map[y][x] = '*';
                starPositions.add(new int[]{x, y});
                currentStars++;
            }
        }
    }

    public void spawnCrystals(int count) {
        Random random = new Random();
        int spawned = 0;

        while (spawned < count) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (map[y][x] == '.') {
                map[y][x] = '+';
                spawned++;
            }
        }
        gemsCounter = spawned;
    }
    public int getCrystalCount() { return gemsCounter; }
    public void decrementGems() { gemsCounter-- ;}

    public void displayMap(Droid currentPlayerDroid) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char cell = map[y][x];

                switch (cell) {
                    case 't':
                        System.out.print("\u001B[34m" + cell + "\u001B[0m ");
                        break;
                    case 'S':
                        if (currentPlayerDroid.isTypeOf("SpyDroid") && currentPlayerDroid.isCurrentPlayer()) {
                            System.out.print("\u001B[33m" + cell + "\u001B[0m ");
                        } else {
                            System.out.print("\u001B[0m" + cell + "\u001B[0m ");
                        }
                        break;
                    case 's':
                        System.out.print("\u001B[32m" + cell + "\u001B[0m ");
                        break;
                    case 'b':
                        System.out.print("\u001B[31m" + cell + "\u001B[0m ");
                        break;
                    case 'E':
                        if (currentPlayerDroid.isTypeOf("EngineerDroid") && currentPlayerDroid.isCurrentPlayer()) {
                            System.out.print("\u001B[33m" + cell + "\u001B[0m ");
                        } else {
                            System.out.print("\u001B[0m" + cell + "\u001B[0m ");
                        }
                        break;
                    case '=':
                        System.out.print("\u001B[33m" + cell + "\u001B[0m ");
                        break;
                    case 'B':
                        if (currentPlayerDroid.isTypeOf("BlazeDroid") && currentPlayerDroid.isCurrentPlayer()) {
                            System.out.print("\u001B[33m" + cell + "\u001B[0m ");
                        } else {
                            System.out.print("\u001B[0m" + cell + "\u001B[0m ");
                        }
                        break;
                    case 'T':
                        if (currentPlayerDroid.isTypeOf("TankDroid") && currentPlayerDroid.isCurrentPlayer()) {
                            System.out.print("\u001B[33m" + cell + "\u001B[0m ");
                        } else {
                            System.out.print("\u001B[0m" + cell + "\u001B[0m ");
                        }
                        break;
                    case '*':
                        System.out.print("\u001B[36m" + cell + "\u001B[0m ");
                        break;
                    case '#':
                        System.out.print("\u001B[35m" + cell + "\u001B[0m ");
                        break;
                    case '|':
                        System.out.print("\u001B[31m" + cell + "\u001B[0m ");
                        break;
                    case '-':
                        System.out.print("\u001B[31m" + cell + "\u001B[0m ");
                        break;
                    case '+':
                        System.out.print("\u001B[38;5;10m" + cell + "\u001B[0m ");
                        break;
                    default:
                        System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }

}
