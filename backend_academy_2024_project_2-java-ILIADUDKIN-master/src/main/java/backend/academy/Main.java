package backend.academy;

import backend.academy.UniteUtils.Maze;
import backend.academy.UniteUtils.Pair;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;
import static backend.academy.UserAPI.UserInteraction.choiceOfMaze;
import static backend.academy.UserAPI.UserInteraction.getPath;
import static backend.academy.UserAPI.UserInteraction.inputSizeOfMaze;
import static backend.academy.UserAPI.UserInteraction.inputStartAndEndVertex;
import static backend.academy.UserAPI.UserInteraction.startInteraction;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        PrintStream out = System.out;
        startInteraction(out);
        Pair<Integer, Integer> sizeOfMaze = inputSizeOfMaze(out);
        final int height = sizeOfMaze.first();
        final int width = sizeOfMaze.second();
        Maze maze = choiceOfMaze(out, height, width);
        Pair<Integer, Integer> startEnd = inputStartAndEndVertex(out, maze);
        if (startEnd != null) {
            int startVertex = startEnd.first();
            int endVertex = startEnd.second();
            getPath(startVertex, endVertex, maze, out);
        }
    }
}
