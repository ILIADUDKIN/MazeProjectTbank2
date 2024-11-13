package backend.academy.UserAPI;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import backend.academy.generator.GeneratorKruskal;
import backend.academy.generator.GeneratorKruskalCycle;
import backend.academy.generator.GeneratorPrim;
import backend.academy.renderer.ConsoleRenderer;
import backend.academy.solver.SolverAStarDijkstra;
import java.io.PrintStream;
import java.util.List;
import lombok.experimental.UtilityClass;
import static backend.academy.UniteUtils.Utils.getID;
import static backend.academy.UserAPI.UserExceptions.CYCLE_MAZE_CHOOSE;
import static backend.academy.UserAPI.UserExceptions.KRUSKAL_CHOOSE;
import static backend.academy.UserAPI.UserExceptions.MAZE_TYPE_INPUT_CODE;
import static backend.academy.UserAPI.UserExceptions.PATH_CODE;
import static backend.academy.UserAPI.UserExceptions.PRIM_CHOOSE;
import static backend.academy.UserAPI.UserExceptions.getValidatedIntegerInput;
import static backend.academy.UserAPI.UserExceptions.getValidatedMazeSize;
import static backend.academy.UserAPI.UserExceptions.getValidatedPossibility;
import static backend.academy.UserAPI.UserExceptions.getValidatedXYInput;

/**
 * Взаимодействие с пользователем
 * */

@UtilityClass
public class UserInteraction {


    public static void startInteraction(PrintStream out) {
        out.println("""
            Добрый день! \uD83D\uDE4B\s
            Приветствуем вас в приложении лабиринты!\s
            Здесь вы сможете сгенерировать лабиринт 2 видов: идеальный и неидеальный
            Идеальный лабиринт реализуется с помощью двух алгоритмов - Прима и Краскала.\
             Неидеальный лабиринт был сделан с помощью алгоритма Краскала, где убирается часть стен.
            Кроме задания лабиринтов в этом приложении вам доступны и поиск путей в них. \s
            Для этого были реализованы Алгоритмы А* (С манхетоновской эвристикой) и алгоритм Дейкстры (тот же А* \
            с нулевой эвристикой).
            Из интересного: \

            в лабиринте есть проходы: ⬛  \

            стены: ⬜ \

            монетки (имеют приоритет при прохождении лабиринта): \uD83D\uDFE1 \

            пустыня (замедляют путь по лабиринту):\uD83C\uDFDC️\

            """);
    }

    public static Pair<Integer, Integer> inputSizeOfMaze(PrintStream out) {
        out.println("----------------------------- \nВвод высоты и ширины лабиринта.");
        return getValidatedMazeSize(out);
    }

    public static Maze choiceOfMaze(PrintStream out, int height, int width) {
        out.println("Вы ввели лабиринт размерами: " + height + " x " + width
        + "\n" + "Теперь надо выбрать способ генерации лабиринта.");

        out.println("""
            1 - Идеальный лабиринт алгоритмом Прима
            2 - Идеальный лабиринт алгоритмом Краскала
            3 - Неидеальный лабиринт""");
        int choice = getValidatedIntegerInput(out, MAZE_TYPE_INPUT_CODE);
        return mazeOutput(out, choice, height, width);
    }

    public static Maze mazeOutput(PrintStream out, int choice, int height, int width) {
        Maze maze;
        switch (choice) {
            case PRIM_CHOOSE:
                out.println("Вы выбрали идеальный лабиринт алгоритмом Прима. ");
                maze = new GeneratorPrim().generate(height, width);
                out.println(new ConsoleRenderer().render(maze, false));
                return maze;
            case KRUSKAL_CHOOSE:
                out.println("Вы выбрали идеальный лабиринт алгоритмом Краскала. ");
                maze = new GeneratorKruskal().generate(height, width);
                out.println(new ConsoleRenderer().render(maze, false));
                return maze;
            case CYCLE_MAZE_CHOOSE:
                out.println("Вы выбрали неидеальный лабиринт. ");
                double posCycle = getValidatedPossibility(out);
                maze = GeneratorKruskalCycle.generate(height, width, posCycle);
                out.println(new ConsoleRenderer().render(maze, false));
                return maze;
            default:
                return null;
        }
    }

    public static boolean isEqual(int id1, int id2) {
        return id1 == id2;
    }

    public static Pair<Integer, Integer> inputStartAndEndVertex(PrintStream out, Maze maze) {
        out.println(
            "Теперь вы можете построить путь из одной вершины в другую. Для этого показываем "
                +
                "лабиринт со всеми возможными вершинами: \n"
                +
                new ConsoleRenderer().render(maze, true)
        );
        out.println("Вы можете проложить путь от одной из синих вершин в другую. "
            +
            "Важно: координаты пронумерованы с нуля! ");
        out.print("Введите координаты первой вершины через пробел: сначала y, потом x: ");
        Pair<Integer, Integer> cordOfFirstVertex = getValidatedXYInput(out, maze.height(), maze.width());
        out.print("Введите координаты второй вершины через пробел: сначала y, потом x: ");
        Pair<Integer, Integer> cordOfSecondVertex = getValidatedXYInput(out, maze.height(), maze.width());

        int id1 = getID(cordOfFirstVertex.first(), cordOfFirstVertex.second());
        int id2 = getID(cordOfSecondVertex.first(), cordOfSecondVertex.second());

        if (!isEqual(id1, id2)) {
            return new Pair<>(id1, id2);
        } else {
            out.println("Вершины индентичны");
            return null;
        }
    }

    public static void getPath(int id1, int id2, Maze maze, PrintStream out) {
        out.println("""
                Теперь вы можете построить путь из одной вершины в другую!\s
                0 - А*
                1 - Дейкстра
                """
            );
        int choice = getValidatedIntegerInput(out, PATH_CODE);
        List<Pair<Integer, Integer>> path = new SolverAStarDijkstra().solve(maze, id1, id2, choice);
        var s = new ConsoleRenderer().render(maze, path, id1, id2);
        out.println(s);
    }


}
