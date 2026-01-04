import jcuda.*;
import jcuda.driver.*;

import static jcuda.driver.JCudaDriver.*;

public class FloydWarshallGPU {

    static {
        JCudaDriver.setExceptionsEnabled(true);
        cuInit(0);
    }

    public static int[][] floydWarshall(int[][] graph) {
        int n = graph.length;
        int size = n * n;

        // Flatten 2D graph to 1D
        int[] flatGraph = new int[size];
        for (int i = 0; i < n; i++)
            System.arraycopy(graph[i], 0, flatGraph, i * n, n);

        // Allocate device memory
        CUdeviceptr dGraph = new CUdeviceptr();
        cuMemAlloc(dGraph, size * Sizeof.INT);
        cuMemcpyHtoD(dGraph, Pointer.to(flatGraph), size * Sizeof.INT);

        // Load module
        CUmodule module = new CUmodule();
        cuModuleLoad(module, "kernels/floyd_warshall.ptx");
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, "floyd_warshall_kernel");

        // Launch kernel for each k
        for (int k = 0; k < n; k++) {
            Pointer kernelParameters = Pointer.to(
                    Pointer.to(dGraph),
                    Pointer.to(new int[]{k}),
                    Pointer.to(new int[]{n})
            );
            int blockSize = 16;
            int gridSize = (n + blockSize - 1) / blockSize;
            cuLaunchKernel(function,
                    gridSize, gridSize, 1, // Grid
                    blockSize, blockSize, 1, // Block
                    0, null,
                    kernelParameters, null
            );
            cuCtxSynchronize();
        }

        // Copy result back
        cuMemcpyDtoH(Pointer.to(flatGraph), dGraph, size * Sizeof.INT);
        cuMemFree(dGraph);

        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(flatGraph, i * n, result[i], 0, n);
        return result;
    }
}
