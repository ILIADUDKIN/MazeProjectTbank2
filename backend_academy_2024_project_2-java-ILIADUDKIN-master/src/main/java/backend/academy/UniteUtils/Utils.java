package backend.academy.UniteUtils;

import java.util.Random;
import lombok.experimental.UtilityClass;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Общие утилиты
 */

@UtilityClass
public class Utils {

    static public int graphWidth;
    static public int graphHeight;
    static public final double COIN_POSSIBILITY = 0.1;


    static public Maze initializationOfGenerator(int height, int width) {
        Cell[][] cells = new Cell[height][width];
        Maze maze = new Maze(height, width, cells);

        graphWidth = (maze.width() + (maze.width() % 2 == 1 ? 1 : 2)) / 2;
        graphHeight = (maze.height() + (maze.height() % 2 == 1 ? 1 : 2)) / 2;
        return maze;
    }

    public static Cell.Type setRoadType() {
        Random rand = new Random();
        float distribution = rand.nextFloat(1);
        if (distribution < COIN_POSSIBILITY) {
            return Cell.Type.COIN;
        } else if (distribution < 1 - COIN_POSSIBILITY && distribution > COIN_POSSIBILITY) {
            return Cell.Type.PASSAGE;
        } else {
            return Cell.Type.SAND;
        }
    }

    public static void setVertex(int id, Maze maze) {
        int y = id / graphWidth * 2;
        int x = id % graphWidth * 2;

        if (x < maze.width() && y < maze.height()) {
            maze.grid()[y][x] = new Cell(y, x, Cell.Type.VERTEX);
        }
    }

    public static Coordinate getRoadCoordinate(int id1, int id2) {
        int idMax = max(id1, id2);
        int y = idMax / graphWidth * 2;
        int x = idMax % graphWidth * 2;

        if (abs(id1 - id2) == graphWidth) {
            y--;
        } else {
            x--;
        }
        return new Coordinate(y, x);
    }

    public static void setRoad(int id1, int id2, Maze maze) {
        if (!isNeighbour(id1, id2, graphWidth)) {
            throw new IllegalArgumentException("No neighbour id1 and id2");
        }

        int y = getRoadCoordinate(id1, id2).row();
        int x = getRoadCoordinate(id1, id2).col();

        if (x < maze.width() && y < maze.height()) {
            maze.grid()[y][x] = new Cell(y, x, setRoadType());
        }
    }

    public static Cell getRoad(int id1, int id2, Maze maze) {

        int y = getRoadCoordinate(id1, id2).row();
        int x = getRoadCoordinate(id1, id2).col();

        return maze.grid()[y][x];
    }

    public static boolean isNeighbour(int id1, int id2, int graphWidth) {
        return (abs(id1 - id2) == 1 && id1 / graphWidth == id2 / graphWidth)
            || abs(id1 - id2) == graphWidth;
    }

    public static boolean isEdgeBetween(int id1, int id2, Maze maze) {
        int idMin = min(id1, id2);
        int idMax = max(id1, id2);
        for (Pair<Integer, Integer> pair : maze.possibleMoves()) {
            if (pair.first() == idMin && pair.second() == idMax) {
                return true;
            }
        }
        return false;
    }

    public static int getID(int y, int x) {
        return (y / 2 * graphWidth) + x / 2;
    }

    public static void addEdgeConditions(Maze maze, int randomCell, int i, Pair<Integer, Integer> givenPair) {
        if (maze.height() % 2 == 0 && maze.width() % 2 == 0) {
            if (randomCell / graphWidth != graphHeight - 1 && randomCell % graphWidth != graphWidth - 1) {
                if (i / graphWidth != graphHeight - 1 && i % graphWidth != graphWidth - 1) {
                    maze.possibleMoves().add(givenPair);
                }
            }
        } else if (maze.height() % 2 == 0) {
            if (randomCell / graphWidth != graphHeight - 1 && i / graphWidth != graphHeight - 1) {
                maze.possibleMoves().add(givenPair);
            }
        } else if (maze.width() % 2 == 0) {
            if (randomCell % graphWidth != graphWidth - 1 && i % graphWidth != graphWidth - 1) {
                maze.possibleMoves().add(givenPair);
            }
        } else {
            maze.possibleMoves().add(givenPair);
        }
    }

    public static void setWalls(Maze maze) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                if (maze.grid()[y][x] == null) {
                    maze.grid()[y][x] = new Cell(y, x, Cell.Type.WALL);
                }
            }
        }
    }
}
