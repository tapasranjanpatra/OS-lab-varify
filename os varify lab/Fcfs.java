import java.util.Scanner;

public class Fcfs {
    public static void main(String[] args) {
        int[] p, at, bt, ct, tat, wt;
        int n;
        float awt = 0, atat = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes you want: ");
        n = scanner.nextInt();

        p = new int[n];
        at = new int[n];
        bt = new int[n];
        ct = new int[n];
        tat = new int[n];
        wt = new int[n];

        System.out.println("Enter the process numbers: ");
        for (int i = 0; i < n; i++) {
            p[i] = scanner.nextInt();
        }

        System.out.println("Enter the arrival times: ");
        for (int i = 0; i < n; i++) {
            at[i] = scanner.nextInt();
        }

        System.out.println("Enter the burst times: ");
        for (int i = 0; i < n; i++) {
            bt[i] = scanner.nextInt();
        }

        // Sorting based on arrival time (Bubble Sort)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (at[j] > at[j + 1]) {
                    int temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;

                    temp = at[j];
                    at[j] = at[j + 1];
                    at[j + 1] = temp;

                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;
                }
            }
        }

        // Calculating completion time (ct)
        ct[0] = at[0] + bt[0];
        for (int i = 1; i < n; i++) {
            if (ct[i - 1] < at[i]) {
                ct[i] = at[i] + bt[i];
            } else {
                ct[i] = ct[i - 1] + bt[i];
            }
        }

        // Calculating turnaround time (tat) and waiting time (wt)
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\t\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
            atat += tat[i];
            awt += wt[i];
            System.out.printf("P%d\t\t%d\t\t%d\t\t%d\t\t%d\t%d%n", p[i], at[i], bt[i], ct[i], tat[i], wt[i]);
        }

        atat = atat / n;
        awt = awt / n;
        System.out.printf("\nAverage turnaround time is %.2f%n", atat);
        System.out.printf("Average waiting time is %.2f%n", awt);
    }
}
