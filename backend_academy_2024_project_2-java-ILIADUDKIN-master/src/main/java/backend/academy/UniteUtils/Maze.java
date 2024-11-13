package backend.academy.UniteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;

/**
 * Класс самого лабиринта
 */
@Getter
public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;
    ArrayList<Pair<Integer, Integer>> possibleMoves;

    @Override public String toString() {
        return "Maze{"
            +
            "height=" + height
            +
            ", width=" + width
            +
            ", grid=" + Arrays.toString(grid)
            +
            ", possibleMoves=" + possibleMoves
            +
            '}';
    }

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
        possibleMoves = new ArrayList<>();
    }
}
