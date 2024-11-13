import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    void testInitializationOfGenerator() {
        // Arrange
        int height = 5;
        int width = 5;

        // Act
        Maze maze = Utils.initializationOfGenerator(height, width);

        // Assert
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
        assertEquals(3, Utils.graphWidth);
        assertEquals(3, Utils.graphHeight);
    }

    @Test
    void testSetVertex() {
        // Arrange
        int height = 5;
        int width = 5;
        Maze maze = new Maze(height, width, new Cell[height][width]);
        int id = 5;

        // Act
        Utils.setVertex(id, maze);

        // Assert
        assertEquals(Cell.Type.VERTEX, maze.grid()[2][4].type());
    }

    @Test
    void testSetRoad_InvalidNeighbour() {
        // Arrange
        int height = 5;
        int width = 5;
        Maze maze = new Maze(height, width, new Cell[height][width]);
        int id1 = 1;
        int id2 = 5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Utils.setRoad(id1, id2, maze));
    }

    @Test
    void testIsNeighbour() {
        // Arrange
        int graphWidth = 3;
        int id1 = 0;
        int id2 = 3;

        // Act
        boolean result = Utils.isNeighbour(id1, id2, graphWidth);

        // Assert
        assertTrue(result);
    }



}
