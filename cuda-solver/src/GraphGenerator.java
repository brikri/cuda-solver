import java.util.Random;

public class GraphGenerator {
    public static int[][] generateRandomGraph(int n, int maxWeight, double density) {
        Random rand = new Random();
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) graph[i][j] = 0;
                else if (rand.nextDouble() < density)
                    graph[i][j] = rand.nextInt(maxWeight) + 1;
                else
                    graph[i][j] = Integer.MAX_VALUE / 2; // infinity
            }
        }
        return graph;
    }
}
