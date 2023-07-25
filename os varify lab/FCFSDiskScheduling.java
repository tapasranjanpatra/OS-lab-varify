import java.util.*;

public class FCFSDiskScheduling {
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
        double throughput = calculateThroughput(n, totalSeekTime);

        System.out.println("Total seek time: " + totalSeekTime);
        System.out.println("Throughput: " + throughput + " requests per unit of time");

        sc.close();
    }

    public static int calculateSeekTime(int[] requests, int head) {
        int n = requests.length;
        int seekTime = 0;

        for (int i = 0; i < n; i++) {
            int currentRequest = requests[i];
            seekTime += Math.abs(currentRequest - head);
            head = currentRequest;
        }

        return seekTime; 
    }

    public static double calculateThroughput(int numRequests, int totalSeekTime) {
        double totalProcessingTime = numRequests; // Assuming each request takes 1 unit of time
        double totalTime = totalSeekTime + totalProcessingTime;
        return numRequests / totalTime;
    }
}
