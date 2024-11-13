import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import backend.academy.solver.SolverAStarDijkstra;
import java.util.List;
import org.junit.jupiter.api.Test;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.UniteUtils.Utils.setWalls;
import static backend.academy.solver.SolverAStarDijkstra.SOLVER_TYPE_ASTAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverAStarDijkstraTest {

    @Test
    void testHeuristicFunction() {
        // Arrange
        int id1 = 1;
        int id2 = 5;
        SolverAStarDijkstra solver = new SolverAStarDijkstra();
        graphWidth = 3;
        graphHeight= 3;
        // Act
        int heuristic = solver.heuristicFunction(id1, id2);

        // Assert
        assertEquals(4, heuristic);
    }

    @Test
    void testSolve_Dijkstra() {
        // Arrange
        Maze maze = createTestMaze();
        SolverAStarDijkstra solver = new SolverAStarDijkstra();
        int startVertex = 0;
        int endVertex = 8;

        // Act
        List<Pair<Integer, Integer>> path = solver.solve(maze, startVertex, endVertex, 1);

        // Assert
        assertEquals(4, path.size());
        assertEquals(0, path.get(0).first());
        assertEquals(1, path.get(0).second());
        assertEquals(1, path.get(1).first());
        assertEquals(2, path.get(1).second());
        assertEquals(2, path.get(2).first());
        assertEquals(5, path.get(2).second());
        assertEquals(5, path.get(3).first());
        assertEquals(8, path.get(3).second());
    }

    @Test
    void testSolve_AStar() {
        // Arrange
        Maze maze = createTestMaze();
        SolverAStarDijkstra solver = new SolverAStarDijkstra();
        int startVertex = 0;
        int endVertex = 8;

        // Act
        List<Pair<Integer, Integer>> path = solver.solve(maze, startVertex, endVertex, SOLVER_TYPE_ASTAR);

        // Assert
        assertEquals(4, path.size());
        assertEquals(0, path.get(0).first());
        assertEquals(1, path.get(0).second());
        assertEquals(1, path.get(1).first());
        assertEquals(2, path.get(1).second());
        assertEquals(2, path.get(2).first());
        assertEquals(5, path.get(2).second());
        assertEquals(5, path.get(3).first());
        assertEquals(8, path.get(3).second());
    }

    public static Maze createTestMaze() {

        graphWidth = 3;
        graphHeight = 3;
        Maze maze = new Maze(5, 5, new Cell[5][5]);

        maze.possibleMoves().add(new Pair<>(0, 1));
        maze.possibleMoves().add(new Pair<>(1, 2));
        maze.possibleMoves().add(new Pair<>(1, 4));
        maze.possibleMoves().add(new Pair<>(2, 5));
        maze.possibleMoves().add(new Pair<>(5, 8));
        maze.possibleMoves().add(new Pair<>(6, 7));
        maze.possibleMoves().add(new Pair<>(7, 8));
        maze.possibleMoves().add(new Pair<>(3, 6));
        maze.possibleMoves().add(new Pair<>(4, 5));

        maze.grid()[0][0] = new Cell(0, 0, Cell.Type.VERTEX);
        maze.grid()[0][2] = new Cell(0, 2, Cell.Type.VERTEX);
        maze.grid()[0][4] = new Cell(0, 4, Cell.Type.VERTEX);
        maze.grid()[2][0] = new Cell(2, 0, Cell.Type.VERTEX);
        maze.grid()[2][2] = new Cell(2, 2, Cell.Type.VERTEX);
        maze.grid()[2][4] = new Cell(2, 4, Cell.Type.VERTEX);
        maze.grid()[4][0] = new Cell(4, 0, Cell.Type.VERTEX);
        maze.grid()[4][2] = new Cell(4, 2, Cell.Type.VERTEX);
        maze.grid()[4][4] = new Cell(4, 4, Cell.Type.VERTEX);

        maze.grid()[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze.grid()[0][3] = new Cell(0, 3, Cell.Type.COIN);
        maze.grid()[1][4] = new Cell(1, 4, Cell.Type.COIN);
        maze.grid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);
        maze.grid()[2][3] = new Cell(2, 3, Cell.Type.PASSAGE);
        maze.grid()[3][0] = new Cell(3, 0, Cell.Type.PASSAGE);
        maze.grid()[3][4] = new Cell(3, 4, Cell.Type.PASSAGE);
        maze.grid()[4][1] = new Cell(4, 1, Cell.Type.PASSAGE);
        maze.grid()[4][3] = new Cell(4, 3, Cell.Type.PASSAGE);


        setWalls(maze);

        return maze;
    }
}
