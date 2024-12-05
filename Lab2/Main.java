package Lab2;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        Car[] cars = new Car[10];
        int carCount = 0;

        cars[carCount++] = new Car(1, "Toyota", 2015, 15000.00, "ABC123");
        cars[carCount++] = new Car(2, "Mitsubishi", 2018, 18000.00, "DEF456");
        cars[carCount++] = new Car(3, "Toyota", 2010, 10000.00, "GHI789");
        cars[carCount++] = new Car(4, "BMW", 2020, 50000.00, "JKL012");
        cars[carCount++] = new Car(5, "Toyota", 2005, 7000.00, "MNO345");
        cars[carCount++] = new Car(6, "BMW", 2018, 35000.00, "PQR678");
        cars[carCount++] = new Car(7, "BMW", 2016, 40000.00, "STU901");
        cars[carCount++] = new Car(8, "Ford", 2022, 25000.00, "VWX234");
        cars[carCount++] = new Car(9, "Audi", 2021, 30000.00, "YZA567");
        cars[carCount++] = new Car(10, "Mitsubishi", 2022, 22000.00, "BCD890");

        userMenu(cars, carCount);
    }

    public static void userMenu(Car[] cars, int carCount) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Вивести автомобілі за моделлю");
            System.out.println("2. Вивести автомобілі за моделлю і кількістю років експлуатації");
            System.out.println("3. Вивести автомобілі за роком випуску і ціною");
            System.out.println("4. Вийти");

            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Доступні моделі: ");
                    printAvailableModels(cars, carCount);
                    System.out.print("Введіть модель: ");
                    String model1 = scanner.nextLine();
                    printCarsByModel(cars, carCount, model1);
                    break;
                case 2:
                    System.out.println("Доступні моделі: ");
                    printAvailableModels(cars, carCount);
                    System.out.print("Введіть модель: ");
                    String model2 = scanner.nextLine();
                    int years = getYears(scanner);
                    printCarsByModelAndYears(cars, carCount, model2, years);
                    break;
                case 3:
                    int year = getYear(scanner, cars, carCount);
                    double minPrice = getMinPrice(cars, carCount);
                    double maxPrice = getMaxPrice(cars, carCount);
                    double price = getPrice(scanner, "мінімальну ціну в діапазоні [" + minPrice + ", " + maxPrice + "]");
                    printCarsByYearAndPrice(cars, carCount, year, price);
                    break;
                case 4:
                    System.out.println("Вихід...");
                    return;
                default:
                    System.out.println("Невірний вибір, спробуйте знову.");
                    break;
            }
        }
    }

    public static void printAvailableModels(Car[] cars, int carCount) {
        Set<String> models = new TreeSet<>();
        for (int i = 0; i < carCount; i++) {
            models.add(cars[i].getModel());
        }
        System.out.println(models);
    }

    public static void printCarsByModel(Car[] cars, int carCount, String model) {
        System.out.println("\nАвтомобілі моделі " + model + ":");
        boolean found = false;
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getModel().equalsIgnoreCase(model)) {
                found = true;
                System.out.println(cars[i]);
            }
        }
        if (!found) {
            System.out.println("Автомобілів моделі " + model + " не знайдено.");
        }
    }

    public static int getYears(Scanner scanner) {
        while (true) {
            System.out.print("Введіть кількість років експлуатації: ");
            int years = scanner.nextInt();
            if (years >= 0) {
                return years;
            } else {
                System.out.println("Кількість років має бути додатнім числом. Спробуйте ще раз.");
            }
        }
    }

    public static void printCarsByModelAndYears(Car[] cars, int carCount, String model, int years) {
        System.out.println("\nАвтомобілі моделі " + model + ", які експлуатуються більше " + years + " років:");
        int currentYear = 2024;
        boolean found = false;
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getModel().equalsIgnoreCase(model) &&
                    (currentYear - cars[i].getYear() >= years)) {
                found = true;
                System.out.println(cars[i]);
            }
        }
        if (!found) {
            System.out.println("Автомобілів моделі " + model + ", які експлуатуються більше " + years + " років, не знайдено.");
        }
    }

    public static int getYear(Scanner scanner, Car[] cars, int carCount) {
        Set<Integer> years = new TreeSet<>();
        for (int i = 0; i < carCount; i++) {
            years.add(cars[i].getYear());
        }
        System.out.println("Доступні роки випуску: " + years);

        int year;
        while (true) {
            System.out.print("Введіть рік випуску: ");
            year = scanner.nextInt();
            scanner.nextLine();
            if (years.contains(year)) {
                break;
            } else {
                System.out.println("Помилка. Виберіть один з доступних років: " + years);
            }
        }
        return year;
    }

    public static double getMinPrice(Car[] cars, int carCount) {
        double minPrice = cars[0].getPrice();
        for (int i = 1; i < carCount; i++) {
            if (cars[i].getPrice() < minPrice) {
                minPrice = cars[i].getPrice();
            }
        }
        return minPrice;
    }

    public static double getMaxPrice(Car[] cars, int carCount) {
        double maxPrice = cars[0].getPrice();
        for (int i = 1; i < carCount; i++) {
            if (cars[i].getPrice() > maxPrice) {
                maxPrice = cars[i].getPrice();
            }
        }
        return maxPrice;
    }

    public static double getPrice(Scanner scanner, String sentence) {
        double price;
        while (true) {
            System.out.print("Введіть " + sentence + ": ");
            price = scanner.nextDouble();
            scanner.nextLine();
            if (price > 0) {
                break;
            } else {
                System.out.println("Ціна має бути додатнім числом. Спробуйте ще раз.");
            }
        }
        return price;
    }

    public static void printCarsByYearAndPrice(Car[] cars, int carCount, int year, double price) {
        System.out.println("\nАвтомобілі року " + year + ", ціна яких більше або дорівнює " + price + ":");
        boolean found = false;
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getYear() == year && cars[i].getPrice() >= price) {
                found = true;
                System.out.println(cars[i]);
            }
        }
        if (!found) {
            System.out.println("Автомобілів року " + year + ", ціна яких більше або дорівнює " + price + ", не знайдено.");
        }
    }
}
