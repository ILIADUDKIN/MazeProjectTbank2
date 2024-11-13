package backend.academy.solver;

import backend.academy.UniteUtils.Coordinate;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.solver.SolverUtils.getCoordinate;
import static backend.academy.solver.SolverUtils.getMapOfVertexAndNeighbours;
import static backend.academy.solver.SolverUtils.getPath;
import static backend.academy.solver.SolverUtils.setEdgesWeights;
import static java.lang.Math.abs;

/**
* Solver Дейкстры и А*.
* В зависимости от переменной solverType будем получать разные варианты: с эвристикой - А*, без - Дейкстры
 */


public class SolverAStarDijkstra  implements Solver {

    public final static int SOLVER_TYPE_ASTAR = 0;

    public int heuristicFunction(int id1, int id2) {
        Coordinate cord1 = getCoordinate(id1);
        Coordinate cord2 = getCoordinate(id2);

        return abs(cord1.col() - cord2.col()) + abs(cord1.row() - cord2.row());
    }

    @Override
    public List<Pair<Integer, Integer>> solve(Maze maze, Integer startVertex, Integer endVertex, int solverType) {

        setEdgesWeights(maze);

        int[] f = new int[graphWidth * graphHeight];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[startVertex] = 0;

        PriorityQueue<Integer> front = new PriorityQueue<>(Comparator.comparingInt(v -> f[v]));
        HashSet<Integer> visited = new HashSet<>();

        front.add(startVertex);

        int[] predecessors = new int[graphWidth * graphHeight];
        Arrays.fill(predecessors, -1);

        boolean isSearchingEndVertex = false;


        Map<Integer, HashSet<Integer>> mapVertexAndNeighbours =
            getMapOfVertexAndNeighbours(graphWidth, graphHeight, maze);

        while (!front.isEmpty()) {

            Integer vertex = front.poll();
            if (vertex.equals(endVertex)) {
                isSearchingEndVertex = true;
                break;
            }
            visited.add(vertex);
            Set<Integer> neighbours = mapVertexAndNeighbours.get(vertex);

            for (Integer neighbour : neighbours) {
                if (visited.contains(neighbour)) {
                    continue;
                }
                int edgeWeight = getEdgeWeight(vertex, neighbour, maze);
                int tentative = f[vertex] + edgeWeight
                    + (solverType == SOLVER_TYPE_ASTAR ? heuristicFunction(neighbour, endVertex) : 0);
                if (tentative < f[neighbour]) {
                    f[neighbour] = tentative;
                    predecessors[neighbour] = vertex;
                    front.add(neighbour);
                }
            }
        }

        return getPath(maze, isSearchingEndVertex, endVertex, startVertex, predecessors);
    }

    private int getEdgeWeight(int vertex, int neighbour, Maze maze) {
        int vertMin = Math.min(vertex, neighbour);
        int vertMax = Math.max(vertex, neighbour);
        for (Pair<Integer, Integer> pair : maze.possibleMoves()) {
            if (pair.first() == vertMin && pair.second() == vertMax) {
                return pair.weight();
            }
        }
        return Integer.MAX_VALUE;
    }

}

