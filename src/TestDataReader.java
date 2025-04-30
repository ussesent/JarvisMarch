import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataReader {
    public static List<List<Point>> readTestData(String filename) throws IOException {
        List<List<Point>> datasets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                datasets.add(parsePoints(line));
            }
        }
        return datasets;
    }

    private static List<Point> parsePoints(String line) {
        List<Point> points = new ArrayList<>();
        String[] pairs = line.split(";");
        for (String pair : pairs) {
            String[] coords = pair.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            points.add(new Point(x, y));
        }
        return points;
    }
}