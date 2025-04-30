import java.awt.Point;
import java.io.IOException;
import java.util.List;

public class Main {
    /* RUNS_PER_DATASET - количество запусков теста для каждого набора данных
    (для получения усредненных результатов) */
    private static final int RUNS_PER_DATASET = 20;

    public static void main(String[] args) {
        try {
            List<List<Point>> datasets = TestDataReader.readTestData("test_data.txt");

            System.out.println("Размер - время - итерации - точки в оболочке");

            for (List<Point> points : datasets) {
                long totalTime = 0;
                long totalIterations = 0;
                int size = points.size();
                int hullPoints = 0;

                /* 20 запусков для каждого набора (для усреднения результатов)
                Сброс счетчика итераций перед каждым запуском */
                for (int run = 0; run < RUNS_PER_DATASET; run++) {
                    JarvisMarch.resetIterationsCounter();

                    long startTime = System.nanoTime();
                    List<Point> hull = JarvisMarch.findConvexHull(points);
                    long endTime = System.nanoTime();
                    // Конец измененного блока

                    totalTime += (endTime - startTime);
                    totalIterations += JarvisMarch.getIterationsCount();
                    hullPoints = hull.size() - 1;
                }

                //Среднее время в миллисекундах
                double avgTimeMs = totalTime / (RUNS_PER_DATASET * 1_000_000.0);
                //Среднее количество итераций
                long avgIterations = totalIterations / RUNS_PER_DATASET;

                System.out.println(size + " " + avgTimeMs + " " + avgIterations + " " + hullPoints);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
}