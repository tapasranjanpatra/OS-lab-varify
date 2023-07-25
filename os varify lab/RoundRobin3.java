import java.util.Scanner;

class RoundRobin3 {
    static final int N = 100;

    static int[] queue = new int[N];
    static int front = 0, rear = 0;
    static Process4[] proc = new Process4[N];

    static void push(int processId) {
        queue[rear] = processId;
        rear = (rear + 1) % N;
    }

    static int pop() {
        if (front == rear)
            return -1;

        int returnPosition = queue[front];
        front = (front + 1) % N;
        return returnPosition;
    }

    public static void main(String[] args) {
        float waitTimeTotal = 0.0f, tat = 0.0f;
        int n, timeQuantum;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        n = scanner.nextInt();

        System.out.println("Enter arrival and burst time ");
        for (int i = 0; i < n; i++) {
            System.out.print("For process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            proc[i] = new Process4(i + 1, arrivalTime, burstTime);
            proc[i].remainingTime = proc[i].burstTime;
        }

        System.out.print("Enter time quantum: ");
        timeQuantum = scanner.nextInt();

        int time = 0;
        int processesLeft = n;
        int position = -1;
        int localTime = 0;
        int throughput = 0; // Initialize throughput counter

        for (int j = 0; j < n; j++)
            if (proc[j].arrivalTime == time)
                push(j);

        while (processesLeft > 0) {
            if (localTime == 0) {
                if (position != -1)
                    push(position);

                position = pop();
            }

            for (int i = 0; i < n; i++) {
                if (proc[i].arrivalTime > time)
                    continue;
                if (i == position)
                    continue;
                if (proc[i].remainingTime == 0)
                    continue;

                proc[i].waitingTime++;
                proc[i].turnAroundTime++;
            }

            if (position != -1) {
                proc[position].remainingTime--;
                proc[position].turnAroundTime++;

                if (proc[position].remainingTime == 0) {
                    processesLeft--;
                    localTime = -1;
                    position = -1;
                    throughput++; // Increment throughput counter when a process completes
                }
            } else {
                localTime = -1;
            }

            time++;
            localTime = (localTime + 1) % timeQuantum;
            for (int j = 0; j < n; j++)
                if (proc[j].arrivalTime == time)
                    push(j);
        }

        System.out.println();
        System.out.println("Process\t\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.print(proc[i].processId + "\t\t" + proc[i].arrivalTime + "\t\t");
            System.out.print(proc[i].burstTime + "\t\t" + proc[i].waitingTime + "\t\t" + proc[i].turnAroundTime);
            System.out.println();

            tat += proc[i].turnAroundTime;
            waitTimeTotal += proc[i].waitingTime;
        }

        tat = tat / n;
        waitTimeTotal = waitTimeTotal / n;

        System.out.println();
        System.out.println("Average waiting time     : " + waitTimeTotal);
        System.out.println("Average turnaround time : " + tat);
        System.out.println("Throughput              : " + throughput);
    }
}

class Process4 {
    int processId;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnAroundTime;
    int remainingTime;

    public Process4(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        this.remainingTime = 0;
    }
}
