package backend.academy.renderer;

import backend.academy.UniteUtils.Cell;
import backend.academy.UniteUtils.Coordinate;
import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Реализация интерфейсов под рендеры: один без пути, другой - с путём
 */

public class ConsoleRenderer implements Renderer {

    public static final String BLOCK_BLACK = "⬛"; //проход
    public static final String BLOCK_WHITE = "⬜"; // стена
    public static final String BLOCK_COIN = "\uD83D\uDFE1";
    public static final String BLOCK_SAND = "\uD83C\uDFDC️";
    public static final String BLOCK_OUTER_WALL = "\uD83D\uDD36";
    public static final String BLOCK_PATH = "❌";
    public static final String BLOCK_START = "▶️";
    public static final String BLOCK_END = "✅";
    public static final String BLOCK_VERTEX = "*️⃣";
    public static final int PATH_ERROR = 100;

    /**
     * Cледующий метод принимает в качестве параметро лабиринт и булевскую переменную: какой вывод требуется:
     * с вершинами(показываются синим цветом) или без них.
     * Изначально создаётся верхняя стенка. После, в зависимости от типа клетки она печается на поле
     */
    @Override
    public String render(Maze maze, boolean isViewVertex) {
        StringBuilder sb = new StringBuilder(maze.height() * maze.width());
        sb.repeat(BLOCK_OUTER_WALL, maze.width() + 2).append('\n');

        for (int y = 0; y < maze.height(); y++) {
            sb.append(BLOCK_OUTER_WALL);
            for (int x = 0; x < maze.width(); x++) {
                sb = cellRenderer(isViewVertex, maze, sb, y, x);
            }

            sb.append(BLOCK_OUTER_WALL).append("\n");
        }

        sb.repeat(BLOCK_OUTER_WALL, maze.width() + 2);

        return sb.toString();
    }

    // перевод из пользовательского ввода ячейки в позицию на поле
    Coordinate idToCord(Maze maze, Integer id) {
        int graphWidth = (maze.width() + (maze.width() % 2 == 1 ? 1 : 2)) / 2;
        int y = id / graphWidth * 2;
        int x = id % graphWidth * 2;
        return new Coordinate(y, x);
    }

    /**
     * Метод принимает в качестве параметров лабиринт, а также найденный путь.
     * Мы проходимся по ячейкам в пути, сразу обрабатываем ошибку, когда путь найти не удалось.
     * После этого вычисляются координаты по определенной схеме.
     * Далее в координаты добавляется вершина. Проверкой abs(vertex2 - vertex1) == 1 мы проверяем вершины
     * на горизонтальных соседей. Проверкой abs(vertex2 - vertex1) == graphWidth мы проверяем вершины
     * на вертикальных соседей. В зависимости от этого добавляется клетка между ними.
     */

    ArrayList<Coordinate> fromPathToCoordinates(Maze maze, List<Pair<Integer, Integer>> path) {
        int graphWidth = (maze.width() + (maze.width() % 2 == 1 ? 1 : 2)) / 2;
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (Pair<Integer, Integer> pair : path) {
            Integer vertex1 = pair.first();
            if (vertex1 == PATH_ERROR) {
                return null;
            }
            int y1 = vertex1 / graphWidth * 2;
            int x1 = vertex1 % graphWidth * 2;
            coordinates.add(new Coordinate(y1, x1));

            Integer vertex2 = pair.second();
            int y2 = vertex2 / graphWidth * 2;
            int x2 = vertex2 % graphWidth * 2;
            coordinates.add(new Coordinate(y2, x2));

            int minAbsciss = min(x1, x2);
            int minOrdinate = min(y1, y2);
            if (abs(vertex2 - vertex1) == 1) {
                coordinates.add(new Coordinate(minOrdinate, minAbsciss + 1));
            } else if (abs(vertex2 - vertex1) == graphWidth) {
                coordinates.add(new Coordinate(minOrdinate + 1, minAbsciss));
            }
        }
        return coordinates;
    }

    /**
     * Метод render переопределяется для отображения пути. Подаются на вход лабиринт, путь, стартовая и конечная вершины
     * Создаётся массив координат методом fromPathToCoordinates. Сразу обрабатываем возможность нулевого пути.
     * Бежим по сетке поля: возможен случай, когда мы ничего не добавим в наш StringBuilder. В этом случае
     * нам нужно обезапасить себя. Мы создадим отдельную копию нашей отрисованной сетке. После на основной сетке
     * вызовем метод, рисующий координату пути в случае, если она есть. В случае, когда ничего не будет отрисовано,
     * сработает if (isRenderPath), и мы отрисуем стандартную клетку.
     */

    @Override
    public String render(Maze maze, List<Pair<Integer, Integer>> path, Integer startVertex, Integer endVertex) {
        StringBuilder sb = new StringBuilder(maze.height() * maze.width());
        sb.repeat(BLOCK_OUTER_WALL, maze.width() + 2).append('\n');

        Coordinate start = idToCord(maze, startVertex);
        Coordinate end = idToCord(maze, endVertex);

        ArrayList<Coordinate> coordinates = fromPathToCoordinates(maze, path);
        if (coordinates == null) {
            return "Невозможно построить путь!";
        }
        for (int y = 0; y < maze.height(); y++) {
            sb.append(BLOCK_OUTER_WALL);
            for (int x = 0; x < maze.width(); x++) {
                String sbCopyString = sb.toString();
                sb = pathRenderer(sb, coordinates, start, end, y, x);
                String sbString = sb.toString();
                boolean isRenderPath = sbCopyString.equals(sbString);
                if (isRenderPath) {
                    cellRenderer(false, maze, sb, y, x);
                }
            }

            sb.append(BLOCK_OUTER_WALL).append("\n");
        }

        sb.repeat(BLOCK_OUTER_WALL, maze.width() + 2);

        return sb.toString();
    }

    public StringBuilder pathRenderer(StringBuilder sb, ArrayList<Coordinate> coordinates, Coordinate start,
        Coordinate end, int y, int x) {
        Coordinate cord = new Coordinate(y, x);
        if (cord.equals(start)) {
            sb.append(BLOCK_START);
        } else if (cord.equals(end)) {
            sb.append(BLOCK_END);
        } else if (coordinates.contains(cord)) {
            sb.append(BLOCK_PATH);
        }
        return sb;
    }


    public StringBuilder cellRenderer(boolean isViewVertex, Maze maze,
        StringBuilder sb, int y, int x) {
        if (isViewVertex && maze.grid()[y][x].type() == Cell.Type.VERTEX) {
            sb.append(BLOCK_VERTEX);
        } else if (maze.grid()[y][x].type() == Cell.Type.SAND) {
            sb.append(BLOCK_SAND);
        } else if (maze.grid()[y][x].type() == Cell.Type.VERTEX) {
            sb.append(BLOCK_BLACK);
        } else if (maze.grid()[y][x].type() == Cell.Type.COIN) {
            sb.append(BLOCK_COIN);
        } else if (maze.grid()[y][x].type() == Cell.Type.PASSAGE) {
            sb.append(BLOCK_BLACK);
        } else if (maze.grid()[y][x].type() == Cell.Type.WALL) {
            sb.append(BLOCK_WHITE);
        }
        return sb;
    }
}
