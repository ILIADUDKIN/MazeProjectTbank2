package backend.academy.generator;

import backend.academy.UniteUtils.Maze;

/**
* Общий интерфейс генераторов
 */
public interface Generator {
    Maze generate(int height, int width);
}
