public class FloydWarshallCPU {
    public static int[][] floydWarshall(int[][] dist) {
        int n = dist.length;
        int[][] d = new int[n][n];

        // Copy original graph
        for (int i = 0; i < n; i++)
            System.arraycopy(dist[i], 0, d[i], 0, n);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (d[i][k] + d[k][j] < d[i][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }
        return d;
    }
}
