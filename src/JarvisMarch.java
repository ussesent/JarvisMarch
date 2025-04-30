import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JarvisMarch {
    private static int iterationsCount; // Единственное добавленное поле

    /* Метод проверяет, лежит ли точка c права от вектора ab
    Используется векторное произведение, если результат < 0 = точка c справа.
     */
    private static boolean isRight(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) < 0;
    }

    /* Оригинальный алгоритм с добавлением подсчета итераций */
    public static List<Point> findConvexHull(List<Point> points) {
        iterationsCount = 0; // Сброс при каждом вызове

        // Оболочка не может состоять из < 3 точек
        if (points.size() < 3) {
            return new ArrayList<>(points);
        }

        List<Point> hull = new ArrayList<>();
        Point start = points.get(0);

        /* Поиск стартовой точки
        Сначала выбирается самая нижняя, если координаты по y совпадают - берется самая левая
         */
        for (Point p : points) {
            iterationsCount++;
            if (p.y < start.y || (p.y == start.y && p.x < start.x)) {
                start = p;
            }
        }

        Point current = start;
        do {
            hull.add(current); // Добавляем текущую точку в оболочку
            Point next = points.get(0); // Берём первую точку как кандидата

            // Основной цикл алгоритма
            for (Point candidate : points) {
                iterationsCount++; // +1 итерация на каждую проверку
                if (candidate.equals(current)) continue; // Пропускаем текущую точку

                if (isRight(current, next, candidate)) {
                    next = candidate;
                }
                else {
                    // Если точки на одной прямой, берём самую дальнюю
                    int crossProduct = (next.x - current.x) * (candidate.y - current.y)
                            - (next.y - current.y) * (candidate.x - current.x);
                    if (crossProduct == 0) {
                        if (distanceSquared(current, candidate) > distanceSquared(current, next)) {
                            next = candidate; // Точка справа → обновляем кандидата
                        }
                    }
                }
            }

            current = next; // Переходим к следующей точке
        } while (!current.equals(start)); // Пока не замкнём оболочку

        hull.add(start);
        return hull;
    }

    /* Вычисляет квадрат расстояния
    Используется в алгоритме, когда несколько точек лежат на одной прямой с текущим ребром оболочки
     */
    private static double distanceSquared(Point a, Point b) {
        return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
    }

    // Обнуляет счётчик iterationsCount.
    public static void resetIterationsCounter() {
        iterationsCount = 0;
    }

    // Возвращает текущее значение счётчика итераций
    public static int getIterationsCount() {
        return iterationsCount;
    }
}