package Lab1;

import java.util.Scanner;

/**
 * Клас Lab1.LucasNumber представляє число Люка з його значенням та індексом у послідовності.
 */
class LucasNumber {
    private long value; // Значення числа Люка
    private int index; // Порядковий номер числа у послідовності

    /**
     * Конструктор для ініціалізації числа Люка.
     * @param index індекс числа у послідовності.
     * @param value значення числа Люка.
     */
    public LucasNumber(int index, long value) {
        this.index = index;
        this.value = value;
    }

    /**
     * Отримує значення числа Люка.
     * @return значення числа.
     */
    public long getValue() {
        return value;
    }

    /**
     * Отримує індекс числа Люка у послідовності.
     * @return індекс числа.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Перевіряє, чи є число кубом цілого числа.
     * @return true, якщо число є кубом, false в іншому випадку.
     */
    public boolean isCube() {
        double cubeRoot = Math.cbrt(value);
        return cubeRoot == Math.round(cubeRoot);
    }
}
/**
 * Головний клас програми Lab1.PP1.
 * Програма генерує числа Люка до заданого N і перевіряє, чи є серед них числа, які є кубами.
 */
public class PP1 {

    /**
     * Генерує послідовність чисел Люка до N.
     * @param N кількість чисел для генерації.
     * @return масив об'єктів Lab1.LucasNumber, що представляють послідовність чисел Люка.
     */
    public static LucasNumber[] generateLucasNumbers(byte N) {
        LucasNumber[] lucasNumbers = new LucasNumber[N];
        lucasNumbers[0] = new LucasNumber(1, 1); // Перше число Люка
        lucasNumbers[1] = new LucasNumber(2, 2); // Друге число Люка

        // Генерація наступних чисел Люка
        for (int i = 2; i < N; i++) {
            long value = lucasNumbers[i - 1].getValue() + lucasNumbers[i - 2].getValue();
            lucasNumbers[i] = new LucasNumber(i + 1, value);
        }

        return lucasNumbers;
    }

    /**
     * Головний метод програми.
     * Здійснює введення кількості чисел N, генерує послідовність чисел Люка та виводить числа, які є кубами.
     * @param args аргументи командного рядка.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        byte N;

        // Введення кількості чисел N
        while (true) {
            System.out.print("Введіть кількість чисел N (2-127): ");
            if (scanner.hasNextByte()) {
                N = scanner.nextByte();
                if (N > 1 && N <= 127) {
                    break; // Коректне значення
                } else {
                    System.out.println("Некоректне значення. Будь ласка, введіть число від 2 до 127.");
                }
            } else {
                System.out.println("Неправильний формат. Введіть ціле число.");
                scanner.next(); // Очищення введення
            }
        }

        // Генерація чисел Люка
        LucasNumber[] array_lucasNumbers = generateLucasNumbers(N);

        // Виведення чисел Люка
        System.out.println("Числа Люка:");

        for (int i = 0; i < array_lucasNumbers.length; i++) {
            LucasNumber lucas = array_lucasNumbers[i];
            System.out.println("Число №" + lucas.getIndex() + ": " + lucas.getValue());
        }

        // Пошук та виведення чисел, які є кубами
        System.out.println("Числа Люка, які є кубами:");
        boolean foundCube = false;

        for (int i = 0; i < array_lucasNumbers.length; i++) {
            LucasNumber num = array_lucasNumbers[i];
            if (num.isCube()) {
                System.out.println("Число №" + num.getIndex() + " (значення: " + num.getValue() + ") є кубом числа " + Math.round(Math.cbrt(num.getValue())));
                foundCube = true;
            }
        }

        // Повідомлення, якщо серед чисел немає кубів
        if (!foundCube) {
            System.out.println("Серед чисел Люка немає кубів.");
        }
    }
}
