package backend.academy.solver;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.List;

/**
* Общий интерфейс под солверы
 */
public interface Solver {

     List<Pair<Integer, Integer>> solve(Maze maze, Integer startVertex, Integer endVertex, int solverType);
}
