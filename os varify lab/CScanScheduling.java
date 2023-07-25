import java.util.*;

class CScanScheduling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of requests: ");
        int size = scanner.nextInt();

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

        int seekCount = cscan(arr, head, direction);
        double throughput = (double) size / seekCount; // Calculate throughput

        System.out.println("Total number of seek operations = " + seekCount);
        System.out.println("Throughput: " + throughput);
    }

    static int cscan(int arr[], int head, String direction) {
        int seek_count = 0;
        int distance, cur_track;

        Vector<Integer> left = new Vector<Integer>();
        Vector<Integer> right = new Vector<Integer>();
        Vector<Integer> seek_sequence = new Vector<Integer>();

        // Appending end values which have
        // to be visited before reversing
        // the direction
        left.add(0);
        right.add(199);

        // Tracks on the left of the
        // head will be serviced when
        // once the head comes back
        // to the beginning (left end).
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < head)
                left.add(arr[i]);
            if (arr[i] > head)
                right.add(arr[i]);
        }

        // Sorting left and right vectors
        Collections.sort(left);
        Collections.sort(right);

        if (direction.equals("right")) {
            // First service the requests
            // on the right side of the
            // head.
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

            // Once reached the right end
            // jump to the beginning.
            head = 0;

            // Adding seek count for head returning from 199 to 0
            seek_count += 199;

            // Now service the requests again
            // which are left.
            for (int i = 0; i < left.size(); i++) {
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
        } else if (direction.equals("left")) {
            // First service the requests
            // on the left side of the
            // head.
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

            // Once reached the left end
            // jump to the end.
            head = 199;

            // Adding seek count for head returning from 0 to 199
            seek_count += 199;

            // Now service the requests again
            // which are right.
            for (int i = right.size() - 1; i >= 0; i--) {
                cur_track = right.get(i);

                // Appending current track to seek sequence
                seek_sequence.add(cur_track);

                // Calculate absolute distance
                distance = Math.abs(cur_track - head);

                // Increase the total count
                seek_count += distance;

                // Accessed track is now the new head
                head = cur_track;
            }
        }

        return seek_count;
    }
}
