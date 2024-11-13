import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import backend.academy.renderer.ConsoleRenderer;
import backend.academy.solver.SolverAStarDijkstra;
import java.util.List;
import org.junit.jupiter.api.Test;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_BLACK;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_COIN;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_END;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_OUTER_WALL;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_PATH;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_START;
import static backend.academy.renderer.ConsoleRenderer.BLOCK_WHITE;
import static backend.academy.solver.SolverAStarDijkstra.SOLVER_TYPE_ASTAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenderTest {

    @Test
    public void testRender() {
        // Arrange
        Maze maze = SolverAStarDijkstraTest.createTestMaze();

        // Act
        var s = new ConsoleRenderer().render(maze, false);

        // Assert
        String expectedMaze =
            BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL
                + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + "\n"
            + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_COIN + BLOCK_BLACK + BLOCK_OUTER_WALL
                + "\n"
            + BLOCK_OUTER_WALL + BLOCK_WHITE + BLOCK_WHITE + BLOCK_BLACK + BLOCK_WHITE + BLOCK_COIN + BLOCK_OUTER_WALL
                + "\n"
            + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_WHITE + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_OUTER_WALL
                + "\n"
            + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_WHITE + BLOCK_WHITE + BLOCK_WHITE + BLOCK_BLACK + BLOCK_OUTER_WALL
                + "\n"
            + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_OUTER_WALL
            + "\n"
                + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL
            + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL;
        assertEquals(expectedMaze, s);
    }


    @Test
    public void testRenderPath() {
        // Arrange
        Maze maze = SolverAStarDijkstraTest.createTestMaze();
        SolverAStarDijkstra solver = new SolverAStarDijkstra();
        int startVertex = 0;
        int endVertex = 8;
        List<Pair<Integer, Integer>> path = solver.solve(maze, startVertex, endVertex, SOLVER_TYPE_ASTAR);

        // Act
        var s = new ConsoleRenderer().render(maze, path, startVertex, endVertex);

        // Assert
        String expectedMaze =
            BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL
                + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + "\n"
                + BLOCK_OUTER_WALL + BLOCK_START + BLOCK_PATH + BLOCK_PATH + BLOCK_PATH + BLOCK_PATH
                + BLOCK_OUTER_WALL
                + "\n"
                + BLOCK_OUTER_WALL + BLOCK_WHITE + BLOCK_WHITE + BLOCK_BLACK + BLOCK_WHITE + BLOCK_PATH
                +
                BLOCK_OUTER_WALL
                + "\n"
                + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_WHITE + BLOCK_BLACK + BLOCK_BLACK + BLOCK_PATH
                +
                BLOCK_OUTER_WALL
                + "\n"
                + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_WHITE + BLOCK_WHITE + BLOCK_WHITE + BLOCK_PATH
                +
                BLOCK_OUTER_WALL
                + "\n"
                + BLOCK_OUTER_WALL + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_BLACK + BLOCK_END
                + BLOCK_OUTER_WALL
                + "\n"
                + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL
                + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL + BLOCK_OUTER_WALL;
        assertEquals(expectedMaze, s);
    }

    @Test
    public void testNoPath() {
        // Arrange
        Maze maze = SolverAStarDijkstraTest.createTestMaze();
        SolverAStarDijkstra solver = new SolverAStarDijkstra();
        int startVertex = 0;
        int endVertex = 100;
        List<Pair<Integer, Integer>> path = solver.solve(maze, startVertex, endVertex, SOLVER_TYPE_ASTAR);

        // Act
        var s = new ConsoleRenderer().render(maze, path, startVertex, endVertex);

        // Assert
        String expectedMaze = "Невозможно построить путь!";
        assertEquals(expectedMaze, s);
    }

    @Test
    public void testEmptyMaze() {
        // Arrange
        int height = 0;
        int width = 0;
        Cell[][] grid = new Cell[height][width];
        Maze maze = new Maze(height, width, grid);

        // Act
        var s = new ConsoleRenderer().render(maze, false);

        // Assert
        String expectedMaze = "\uD83D\uDD36\uD83D\uDD36\n" +
            "\uD83D\uDD36\uD83D\uDD36";
        assertEquals(expectedMaze, s);
    }

}
