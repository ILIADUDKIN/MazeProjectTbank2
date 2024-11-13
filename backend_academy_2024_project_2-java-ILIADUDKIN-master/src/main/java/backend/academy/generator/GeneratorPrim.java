package backend.academy.generator;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import static backend.academy.UniteUtils.Utils.addEdgeConditions;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.UniteUtils.Utils.initializationOfGenerator;
import static backend.academy.UniteUtils.Utils.isNeighbour;
import static backend.academy.UniteUtils.Utils.setRoad;
import static backend.academy.UniteUtils.Utils.setVertex;
import static backend.academy.UniteUtils.Utils.setWalls;

/**
 * Генератор алгоритмом Прима
 */

public class GeneratorPrim implements Generator {

    @Override
    public Maze generate(int height, int width) {
        // инициализация
        Maze maze = initializationOfGenerator(height, width);

        // Задание начальных фронта
        Set<Integer> front = new HashSet<>();
        Set<Integer> visited = new HashSet<>();

        // Получаем начальную вершинку
        Random rand = new Random();
        int randomCellVertexId = rand.nextInt(graphWidth * graphHeight - 1);
        setVertex(randomCellVertexId, maze);
        front.add(randomCellVertexId);

        // Алгоритм Прима (упрощённый)
        while (!front.isEmpty()) {
            int randomCell = front.stream()
                .skip(rand.nextInt(front.size()))
                .findFirst()
                .orElseThrow();

            boolean isSearch = false;
            Pair<Integer, Integer> givenPair;
            for (int i = 0; i < graphWidth * graphHeight; i++) {
                if (isNeighbour(randomCell, i, graphWidth) && !front.contains(i) && !visited.contains(i)) {
                    setVertex(i, maze);
                    setRoad(randomCell, i, maze);
                    front.add(i);
                    isSearch = true;
                    if (i < randomCell) {
                        givenPair = new Pair<>(i, randomCell);
                    } else {
                        givenPair = new Pair<>(randomCell, i);
                    }

                    addEdgeConditions(maze, randomCell, i, givenPair);
                }
            }

            if (!isSearch) {
                front.remove(randomCell);
                visited.add(randomCell);
            }
        }
        setWalls(maze);
        return maze;
    }


}
