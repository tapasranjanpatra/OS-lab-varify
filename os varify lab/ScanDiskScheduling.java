import java.util.*;

class ScanDiskScheduling {
    static int size;
    static int disk_size;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of requests: ");
        size = scanner.nextInt();

        int[] arr = new int[size];
        System.out.println("Enter the request array:");
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.print("Enter the initial head position: ");
        int head = scanner.nextInt();

        System.out.print("Enter the direction (left or right): ");
        String direction = scanner.next();

        System.out.println();

        disk_size = 200;
        int seekCount = scan(arr, head, direction);
        double throughput = (double) size / seekCount; // Calculate throughput

        System.out.println("Total number of seek operations = " + seekCount);
        System.out.println("Throughput: " + throughput);
    }

    static int scan(int arr[], int head, String direction) {
        int seek_count = 0;
        int distance, cur_track;
        Vector<Integer> left = new Vector<Integer>();
        Vector<Integer> right = new Vector<Integer>();
        Vector<Integer> seek_sequence = new Vector<Integer>();

        // Appending end values which have to be visited before reversing the direction
        if (direction.equals("left")) {
            left.add(0);
        } else if (direction.equals("right")) {
            right.add(disk_size - 1);
        }

        for (int i = 0; i < size; i++) {
            if (arr[i] < head)
                left.add(arr[i]);
            if (arr[i] > head)
                right.add(arr[i]);
        }

        // Sorting left and right vectors
        Collections.sort(left);
        Collections.sort(right);

        // Run the while loop two times, one by one scanning right and left of the head
        int run = 2;
        while (run-- > 0) {
            if (direction.equals("left")) {
                for (int i = left.size() - 1; i >= 0; i--) {
                    cur_track = left.get(i);

                    // Appending current track to seek sequence
                    seek_sequence.add(cur_track);

                    // Calculate absolute distance
                    distance = Math.abs(cur_track - head);

                    // Increase the total count
                    seek_count += distance;

                    // Accessed track is now the new head
                    head = cur_track;
                }
                direction = "right";
            } else if (direction.equals("right")) {
                for (int i = 0; i < right.size(); i++) {
                    cur_track = right.get(i);

                    // Appending current track to seek sequence
                    seek_sequence.add(cur_track);

                    // Calculate absolute distance
                    distance = Math.abs(cur_track - head);

                    // Increase the total count
                    seek_count += distance;

                    // Accessed track is now new head
                    head = cur_track;
                }
                direction = "left";
            }
        }

        return seek_count;
    }
}
