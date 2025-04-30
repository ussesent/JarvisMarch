import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestDataGenerator {
    private static final Random random = new Random();
    private static final int MIN_POINTS = 100;
    private static final int MAX_POINTS = 10000;
    private static final int LINES_COUNT = 55;

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("test_data.txt")) {
            for (int i = 0; i < LINES_COUNT; i++) {
                int pointsCount = MIN_POINTS + random.nextInt(MAX_POINTS - MIN_POINTS + 1);
                writer.write(generatePointsLine(pointsCount) + "\n");
            }
            System.out.println("Файл test_data.txt создан. Путь: " +
                    new java.io.File("test_data.txt").getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static String generatePointsLine(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(10000);
            int y = random.nextInt(10000);
            sb.append(x).append(",").append(y);
            if (i != count - 1) sb.append(";"); // Точки разделены ";"
        }
        return sb.toString();
    }
}

