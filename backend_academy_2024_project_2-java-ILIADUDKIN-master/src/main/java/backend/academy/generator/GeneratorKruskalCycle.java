package backend.academy.generator;

import backend.academy.UniteUtils.Coordinate;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.Random;
import lombok.experimental.UtilityClass;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.UniteUtils.Utils.isEdgeBetween;
import static backend.academy.UniteUtils.Utils.isNeighbour;
import static backend.academy.UniteUtils.Utils.setRoad;
import static backend.academy.solver.SolverUtils.getCoordinate;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Генератор алгоритма Краскала с циклами
 */

@UtilityClass
public class GeneratorKruskalCycle {

    public static void setCycles(Maze maze, double possibilityRoad) {
        Random rand = new Random();
        Coordinate cord1;
        Coordinate cord2;
        boolean withinBounds;
        boolean neighbourWithoutRoad;
        for (int i = 0; i < graphWidth * graphHeight; i++) {
            for (int j = 0; j < graphHeight * graphWidth; j++) {
                cord1 = getCoordinate(i);
                cord2 = getCoordinate(j);
                // следующая булевая переменная проверяет, что координаты в пределах поля
                withinBounds = cord1.col() < maze.width() && cord1.row() < maze.height()
                    &&
                    cord2.col() < maze.width() && cord2.row() < maze.height();
                // следующая булевая переменная проверяет, что вершины - соседи со стеной между
                neighbourWithoutRoad = isNeighbour(i, j, graphWidth) && !isEdgeBetween(i, j, maze);
                if (withinBounds && neighbourWithoutRoad) {
                    int vertMin = min(i, j);
                    int vertMax = max(i, j);
                    double isSetCycle = rand.nextDouble(1);
                    if (isSetCycle < possibilityRoad) {
                        maze.possibleMoves().add(new Pair<>(vertMin, vertMax));
                        setRoad(i, j, maze);
                    }
                }
            }
        }
    }

    public static Maze generate(int height, int width, double possibilityRoad) {
        Maze maze = new GeneratorKruskal().generate(height, width);
        setCycles(maze, possibilityRoad);
        return maze;
    }
}
