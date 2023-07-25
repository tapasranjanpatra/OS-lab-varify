import java.util.*;

public class SSTFDiskScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of disk requests: ");
        int n = sc.nextInt();

        System.out.print("Enter the current head position: ");
        int head = sc.nextInt();

        System.out.println("Enter the disk request queue:");
        int[] requests = new int[n];
        for (int i = 0; i < n; i++) {
            requests[i] = sc.nextInt();
        }

        int totalSeekTime = calculateSeekTime(requests, head);
        double throughput = (double) n / totalSeekTime; // Calculate throughput

        System.out.println("Total seek time: " + totalSeekTime);
        System.out.println("Throughput: " + throughput);
        sc.close();
    }

    public static int calculateSeekTime(int[] requests, int head) {
        int n = requests.length;
        int seekTime = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            int closestRequestIndex = findClosestRequest(requests, head, visited);
            visited[closestRequestIndex] = true;
            int closestRequest = requests[closestRequestIndex];
            seekTime += Math.abs(closestRequest - head);
            head = closestRequest;
        }

        return seekTime;
    }

    public static int findClosestRequest(int[] requests, int head, boolean[] visited) {
        int n = requests.length;
        int minDistance = Integer.MAX_VALUE;
        int closestIndex = -1;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int distance = Math.abs(requests[i] - head);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestIndex = i;
                }
            }
        }

        return closestIndex;
    }
}
