package backend.academy.generator;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import static backend.academy.UniteUtils.Utils.addEdgeConditions;
import static backend.academy.UniteUtils.Utils.graphHeight;
import static backend.academy.UniteUtils.Utils.graphWidth;
import static backend.academy.UniteUtils.Utils.initializationOfGenerator;
import static backend.academy.UniteUtils.Utils.isNeighbour;
import static backend.academy.UniteUtils.Utils.setRoad;
import static backend.academy.UniteUtils.Utils.setVertex;
import static backend.academy.UniteUtils.Utils.setWalls;

/**
 * Генератор алгоритмом Краскала
 */

public class GeneratorKruskal implements Generator {

    @Override
    public Maze generate(int height, int width) {

        Maze maze = initializationOfGenerator(height, width);

        // Начальная инициализация мапы: вершине - множество
        HashMap<Integer, Integer> uniqueSetForCells = new HashMap<>();
        for (int i = 0; i < graphHeight * graphWidth; i++) {
            uniqueSetForCells.put(i, i);
        }

        // Начальная инициализация пройденных рёбер
        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < graphHeight * graphWidth; i++) {
            for (int j = i + 1; j < graphWidth * graphHeight; j++) {
                if (isNeighbour(i, j, graphWidth)) {
                    Pair<Integer, Integer> e = new Pair<>(i, j);
                    pairs.add(e);
                }
            }
        }

        while (!pairs.isEmpty()) {
            Random rand = new Random();
            int indexOfRemovingEdge = (pairs.size() != 1) ? rand.nextInt(pairs.size()) : 0;
            Pair<Integer, Integer> givenPair = pairs.remove(indexOfRemovingEdge);
            int start = givenPair.first();
            int end = givenPair.second();
            if (!uniqueSetForCells.get(start).equals(uniqueSetForCells.get(end))) {

                setVertex(start, maze);
                setVertex(end, maze);
                setRoad(start, end, maze);

                addEdgeConditions(maze, start, end, givenPair);

                // Дальше сделаем их в одно множество
                int newSet = uniqueSetForCells.get(end);
                for (Integer i = 0; i < graphHeight * graphWidth; i++) {
                    if (uniqueSetForCells.get(i).compareTo(newSet) == 0) {
                        uniqueSetForCells.put(i, uniqueSetForCells.get(start));
                    }
                }
                uniqueSetForCells.put(end, uniqueSetForCells.get(start));
            }
        }

        setWalls(maze);
        return maze;
    }
}
