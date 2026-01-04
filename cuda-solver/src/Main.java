public class Main {
    public static void main(String[] args) {
        int n = 1000;
        int maxWeight = 100;
        double density = 0.1;

        System.out.println("Generating graph...");
        int[][] graph = GraphGenerator.generateRandomGraph(n, maxWeight, density);

        System.out.println("Running CPU Floyd-Warshall...");
        long t1 = System.currentTimeMillis();
        int[][] cpuResult = FloydWarshallCPU.floydWarshall(graph);
        long t2 = System.currentTimeMillis();
        System.out.println("CPU time: " + (t2 - t1) + " ms");

        System.out.println("Running GPU Floyd-Warshall...");
        long t3 = System.currentTimeMillis();
        int[][] gpuResult = FloydWarshallGPU.floydWarshall(graph);
        long t4 = System.currentTimeMillis();
        System.out.println("GPU time: " + (t4 - t3) + " ms");

        boolean correct = true;
        for (int i = 0; i < n && correct; i++)
            for (int j = 0; j < n; j++)
                if (cpuResult[i][j] != gpuResult[i][j])
                    correct = false;
        System.out.println("Results match: " + correct);
    }
}
