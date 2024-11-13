import backend.academy.UniteUtils.Maze;
import backend.academy.generator.GeneratorKruskal;
import backend.academy.generator.GeneratorPrim;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.generator.GeneratorKruskalCycle.generate;
import static backend.academy.solver.SolverUtils.getCoordinate;
import static backend.academy.solver.SolverUtils.getMapOfVertexAndNeighbours;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratorTest {


    @Test
    public void testPrimMazeSize() {
        // Arrange
        int height = 10;
        int width = 10;
        GeneratorPrim generator = new GeneratorPrim();

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    public void testPrimMazeConnectivity() {
        // Arrange
        int height = 3;
        int width = 3;
        GeneratorPrim generator = new GeneratorPrim();

        // Act
        Maze maze = generator.generate(height, width);
        boolean isConnected = checkConnectivity(maze);

        // Assert
        assertTrue(isConnected);
    }

    @Test
    public void testKruskalMazeSize() {
        // Arrange
        int height = 10;
        int width = 10;
        GeneratorKruskal generator = new GeneratorKruskal();

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    public void testKruskalMazeConnectivity() {
        // Arrange
        int height = 3;
        int width = 3;
        GeneratorKruskal generator = new GeneratorKruskal();

        // Act
        Maze maze = generator.generate(height, width);
        boolean isConnected = checkConnectivity(maze);

        // Assert
        assertTrue(isConnected);
    }

    @Test
    public void testKruskalCycleMazeSize() {
        // Arrange
        int height = 10;
        int width = 10;

        // Act
        Maze maze = generate(height, width, 0.5);

        // Assert
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    public void testKruskalCycleMazeConnectivity() {
        // Arrange
        int height = 3;
        int width = 3;

        // Act
        Maze maze = generate(height, width, 0.5);
        boolean isConnected = checkConnectivity(maze);

        // Assert
        assertTrue(isConnected);
    }


    private boolean checkConnectivity(Maze maze) {
        Map<Integer, HashSet<Integer>> mapVertex = getMapOfVertexAndNeighbours(graphWidth, graphHeight, maze);
        boolean[] visited = new boolean[graphWidth * graphHeight];
        dfs(0,visited, maze, mapVertex);
        for (int i = 0; i < graphWidth * graphHeight; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int vertex, boolean[] visited,
        Maze maze, Map<Integer, HashSet<Integer>> mapVertex) {
        int y = getCoordinate(vertex).row();
        int x = getCoordinate(vertex).col();

        if (x < 0 || y < 0 || x >= maze.height() || y >= maze.width() || visited[vertex]) {
            return;
        }
        visited[vertex] = true;
        HashSet<Integer> neighbours = mapVertex.get(vertex);
        for (Integer neighbour : neighbours) {
            dfs(neighbour, visited, maze, mapVertex);
        }
    }


}
