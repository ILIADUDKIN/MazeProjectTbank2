package backend.academy.solver;

import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Coordinate;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import static backend.academy.UniteUtils.Cell.getWeight;
import static backend.academy.UniteUtils.Utils.getRoad;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.UniteUtils.Utils.isEdgeBetween;
import static backend.academy.UniteUtils.Utils.isNeighbour;
import static backend.academy.renderer.ConsoleRenderer.PATH_ERROR;

/**
* Утилиты, которые используются в солвере Дейкстры и А*
 */
@UtilityClass
public class SolverUtils {

    public static final int COIN_WEIGHT = 1;
    public static final int SAND_WEIGHT = 3;
    public static final int PASSAGE_WEIGHT = 2;

    public static Coordinate getCoordinate(int id) {
        int y = id / graphWidth * 2;
        int x = id % graphWidth * 2;
        return new Coordinate(y, x);
    }


    public static Cell.Type getCellFromId(int id, Maze maze) {
        int y = id / graphWidth * 2;
        int x = id % graphWidth * 2;
        return maze.grid()[y][x].type();
    }

    public static void setEdgesWeights(Maze maze) {
        for (Pair<Integer, Integer> pair : maze.possibleMoves()) {
            int start = pair.first();
            int end = pair.second();
            Cell.Type typeRoad = getRoad(start, end, maze).type();
            pair.weight(getWeight(typeRoad));
        }
    }

    public static Integer getLowestDistanceVertex(LinkedList<Integer> front, int[] f) {
        return front.stream()
            .min(Comparator.comparingInt(v -> f[v]))
            .orElse(null);
    }


    // следующий метод заполняет мапу между вершинами и соседями
    public static Map<Integer, HashSet<Integer>>
    getMapOfVertexAndNeighbours(int graphWidth, int graphHeight, Maze maze) {
        Map<Integer, HashSet<Integer>> mapVertexNeighbours = new HashMap<>();
        for (int vertexId = 0; vertexId < graphHeight * graphWidth; vertexId++) {
            HashSet<Integer> neighbours = new HashSet<>();
            for (int i = 0; i < graphHeight * graphWidth; i++) {
                if (isNeighbour(vertexId, i, graphWidth) && isEdgeBetween(vertexId, i, maze)) {
                    neighbours.add(i);
                }
            }
            mapVertexNeighbours.put(vertexId, neighbours);
        }
        return mapVertexNeighbours;
    }


    public static List<Pair<Integer, Integer>> getPath(Maze maze, boolean isSearchingEndVertex, int endVertex,
        int startVertex, int[] predecessors) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        if (isSearchingEndVertex) {
            int currentVertex = endVertex;
            while (currentVertex != startVertex) {
                int previousVertex = predecessors[currentVertex];
                for (Pair<Integer, Integer> pair : maze.possibleMoves()) {
                    if ((pair.first() == previousVertex && pair.second() == currentVertex)
                        || (pair.second() == previousVertex && pair.first() == currentVertex)) {
                        path.add(pair);
                        break;
                    }
                }
                currentVertex = previousVertex;
            }
            Collections.reverse(path);
        } else {
            path.add(new Pair<>(PATH_ERROR, PATH_ERROR));
        }
        return path;
    }
}
