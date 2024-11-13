package backend.academy.renderer;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.List;

/**
 * Общий интерфейс под рендеры
 */
public interface Renderer {
    String render(Maze maze, boolean isViewVertex);

    String render(Maze maze, List<Pair<Integer, Integer>> path, Integer startVertex, Integer endVertex);
}
