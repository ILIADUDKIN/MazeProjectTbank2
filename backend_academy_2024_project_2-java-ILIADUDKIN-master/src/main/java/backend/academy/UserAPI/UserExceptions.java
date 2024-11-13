package backend.academy.UserAPI;

import backend.academy.UniteUtils.Pair;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

/**
 * Исключения пользователя
 */
@UtilityClass
public class UserExceptions {

    public static final int MAZE_TYPE_INPUT_CODE = 1;
    public static final int PATH_CODE = 2;
    public static final int MINIMAL_MAZE_SIZE = 3;
    public static final int MAXIMUM_MAZE_SIZE = 100;
    public static final int PRIM_CHOOSE = 1;
    public static final int KRUSKAL_CHOOSE = 2;
    public static final int CYCLE_MAZE_CHOOSE = 3;
    public static final String ERROR_MESSAGE = "\"Ошибка : \"";
    public static final String USER_INPUT_MESSAGE = "Вы ввели: ";

    public static void possibilityException(double number) {
        if (number < 0 || number > 1) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 0 до 1.");
        }
    }

    public static double getValidatedPossibility(PrintStream out)
        throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        double number;
        while (true) {
            try {
                out.print("Вам предоставляется возможность проконтролировать создание циклов.\n"
                    + "Введите вещественное число от 0 до 1 через запятую: ");
                number = scanner.nextFloat();
                possibilityException(number);
                break;
            } catch (InputMismatchException e) {
                out.println("Ошибка: Введено не число. Возможно, вам стоит обратить внимание на запятую.");
                scanner.nextLine(); // Очистка буфера ввода
            } catch (IllegalArgumentException e) {
                out.println(ERROR_MESSAGE + e.getMessage());
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                out.println("Ошибка : Нет ввода. Введите вещественное число.");
                scanner.nextLine(); // Очистка буфера ввода
            }
        }
        out.println(USER_INPUT_MESSAGE + number);
        scanner.nextLine();
        return number;
    }


    public static int getValidatedIntegerInput(PrintStream out, int code)
        throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int number;
        while (true) {
            try {
                number = codeCheckerException(code, out, scanner);
                break;
            } catch (InputMismatchException e) {
                out.println("Ошибка: Введено не целое число.");
                scanner.nextLine(); // Очистка буфера ввода
            } catch (IllegalArgumentException e) {
                out.println(ERROR_MESSAGE + e.getMessage());
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                out.println("Ошибка: Нет ввода. Пожалуйста, введите целое число.");
                scanner.nextLine(); // Очистка буфера ввода
            }
        }

        out.println(USER_INPUT_MESSAGE + number);
        scanner.nextLine();
        return number;
    }

    public static Pair<Integer, Integer> getValidatedMazeSize(PrintStream out)
        throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int number1;
        int number2;
        while (true) {
            try {
                out.println("Введите высоту "
                    + "и ширину через пробел: ");
                number1 = scanner.nextInt();
                number2 = scanner.nextInt();
                if (number1 < MINIMAL_MAZE_SIZE || number1 > MAXIMUM_MAZE_SIZE) {
                    throw new IllegalArgumentException("Первое число должна быть в диапазоне от 3 до 100.");
                }
                if (number2 < MINIMAL_MAZE_SIZE || number2 > MAXIMUM_MAZE_SIZE) {
                    throw new IllegalArgumentException("Второе число должно быть в диапазоне от 3 до 100.");
                }
                break;
            } catch (InputMismatchException e) {
                out.println("Ошибка: существуют введенные нецелые числа (проверьте тип)");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                out.println(ERROR_MESSAGE + e.getMessage());
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                out.println("Ошибка: Нет ввода. Пожалуйста, введите два целых числа.");
                scanner.nextLine(); // Очистка буфера ввода
            }
        }

        Pair<Integer, Integer> pair = new Pair<>(number1, number2);
        scanner.nextLine();
        return pair;
    }

    public static Pair<Integer, Integer> getValidatedXYInput(PrintStream out, int heightOfMaze, int widthOfMaze)
    throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int number1;
        int number2;
        while (true) {
            try {
                number1 = scanner.nextInt();
                number2 = scanner.nextInt();
                if (number1 < 0 || number1 >= heightOfMaze || number1 % 2 == 1) {
                    throw new IllegalArgumentException("Координаты по y должны быть чётными в пределах поля.");
                }
                if (number2 < 0 || number2 >= widthOfMaze || number2 % 2 == 1) {
                    throw new IllegalArgumentException("Координаты по x должны быть чётными в пределах поля.");
                }
                break;
            } catch (InputMismatchException e) {
                out.println("Ошибка: существуют введенные нецелые числа");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                out.println(ERROR_MESSAGE + e.getMessage());
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                out.println("Ошибка: Нет ввода. Пожалуйста, введите два целых числа: координаты высоты и ширины.");
                scanner.nextLine(); // Очистка буфера ввода
            }
        }

        Pair<Integer, Integer> pair = new Pair<>(number1, number2);
        scanner.nextLine();
        return pair;
    }

    public int codeCheckerException(int code, PrintStream out, Scanner scanner)
        throws InputMismatchException {
        int number = 0;
        switch (code) {
            case MAZE_TYPE_INPUT_CODE:
                out.print("Введите целое число от 1 до 3: ");
                number = scanner.nextInt();
                mazeTypeException(number);
                break;

            case PATH_CODE:
                out.print("Введите 0 или 1: ");
                number = scanner.nextInt();
                mazeSolverTypeException(number);
                break;
            default:
                out.println("Неизвестный код ввода.");
                break;
        }
        return number;
    }


    public static void mazeTypeException(int number) {
        if (number < PRIM_CHOOSE || number > CYCLE_MAZE_CHOOSE) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3.");
        }
    }


    public static void mazeSolverTypeException(int number) {
        if (number < 0 || number > 1) {
            throw new IllegalArgumentException("Число должно быть или 0, или 1");
        }
    }
}
