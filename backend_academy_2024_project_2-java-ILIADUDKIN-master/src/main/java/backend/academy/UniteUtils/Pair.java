package backend.academy.UniteUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс Pair в коде используется в основном при реализации рёбер. Однако существуют и другие способы использования
 */

@Setter @Getter public class Pair<T, U> {

    private T first;
    private U second;
    private int weight;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{"
            +
            "first=" + first
            +
            ", second=" + second
            +
            '}';
    }
}
