import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Coordinate;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import backend.academy.solver.SolverUtils;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static backend.academy.generator.GeneratorKruskalCycle.generate;
import static backend.academy.renderer.ConsoleRenderer.PATH_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SolverUtilsTest {

    @Test
    void testGetCoordinate() {
        // Arrange
        int id = 5;

        // Act
        Coordinate coordinate = SolverUtils.getCoordinate(id);

        // Assert
        assertEquals(2, coordinate.row());
        assertEquals(4, coordinate.col());
    }

    @Test
    void testGetCellFromId() {
        // Arrange
        int id = 5;
        Maze maze = generate(5, 5, 0.5);

        // Act
        Cell.Type type = SolverUtils.getCellFromId(id, maze);

        // Assert
        assertEquals(Cell.Type.VERTEX, type);
    }

    @Test
    void testGetLowestDistanceVertex() {
        // Arrange
        LinkedList<Integer> front = new LinkedList<>(Arrays.asList(1, 2));
        int[] f = {5, 2, 4};

        // Act
        Integer lowestVertex = SolverUtils.getLowestDistanceVertex(front, f);

        // Assert
        assertEquals(1, lowestVertex);
    }

    @Test
    void testGetLowestDistanceVertex_EmptyFront() {
        // Arrange
        LinkedList<Integer> front = new LinkedList<>();
        int[] f = {5, 2, 4};

        // Act
        Integer lowestVertex = SolverUtils.getLowestDistanceVertex(front, f);

        // Assert
        assertNull(lowestVertex);
    }


    @Test
    void testGetPath_SearchingEndVertex() {
        // Arrange
        Maze maze = new Maze(5, 5, new Cell[5][5]);
        maze.possibleMoves().add(new Pair<>(0, 1));
        maze.possibleMoves().add(new Pair<>(1, 2));
        maze.possibleMoves().add(new Pair<>(2, 3));
        int endVertex = 3;
        int startVertex = 0;
        int[] predecessors = {0, 0, 1, 2};

        // Act
        List<Pair<Integer, Integer>> path = SolverUtils.getPath(maze, true, endVertex, startVertex, predecessors);

        // Assert
        assertEquals(3, path.size());
        assertEquals(0, path.get(0).first());
        assertEquals(1, path.get(0).second());
        assertEquals(1, path.get(1).first());
        assertEquals(2, path.get(1).second());
        assertEquals(2, path.get(2).first());
        assertEquals(3, path.get(2).second());
    }

    @Test
    void testGetPath_NotSearchingEndVertex() {
        // Arrange
        Maze maze = new Maze(5, 5, new Cell[5][5]);
        int endVertex = 3;
        int startVertex = 0;
        int[] predecessors = {0, 0, 1, 2}; // Simulate predecessors array

        // Act
        List<Pair<Integer, Integer>> path = SolverUtils.getPath(maze, false, endVertex, startVertex, predecessors);

        // Assert
        assertEquals(1, path.size());
        assertEquals(PATH_ERROR, path.getFirst().first());
        assertEquals(PATH_ERROR, path.getFirst().second());
    }
}
