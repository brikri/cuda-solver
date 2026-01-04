extern "C"
__global__ void floyd_warshall_kernel(int* dist, int k, int n) {
    int i = blockIdx.y * blockDim.y + threadIdx.y;
    int j = blockIdx.x * blockDim.x + threadIdx.x;

    if (i < n && j < n) {
        int ij = i * n + j;
        int ik = i * n + k;
        int kj = k * n + j;
        int new_dist = dist[ik] + dist[kj];
        if (new_dist < dist[ij])
            dist[ij] = new_dist;
    }
}
