package backend.academy.UniteUtils;

import static backend.academy.solver.SolverUtils.COIN_WEIGHT;
import static backend.academy.solver.SolverUtils.PASSAGE_WEIGHT;
import static backend.academy.solver.SolverUtils.SAND_WEIGHT;

/**
 * Класс ячейки лабиринта
 */
public record Cell(int row, int col, Type type) {
    public enum Type { WALL, PASSAGE, COIN, SAND, VERTEX }

    public static int getWeight(Cell.Type type) {
        if (type.equals(Cell.Type.COIN)) {
            return COIN_WEIGHT;
        }
        if (type.equals(Cell.Type.PASSAGE)) {
            return PASSAGE_WEIGHT;
        }
        if (type.equals(Cell.Type.SAND)) {
            return SAND_WEIGHT;
        }
        return 0;
    }
}
