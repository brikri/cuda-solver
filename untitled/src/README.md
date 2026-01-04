Install CUDA toolkit and JCuda.

Compile CUDA kernel:

nvcc -ptx kernels/floyd_warshall.cu -o kernels/floyd_warshall.ptx


Include jcuda-11.7.jar in your Java classpath.

Run Main.java to benchmark CPU vs GPU.