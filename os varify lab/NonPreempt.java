
import java.util.*;

class Process1 {
    int id;
    int arrivalTime;
    int burstTime;
    int tempBT;
    int priority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    public Process1(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.tempBT = burstTime;
        this.priority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

public class NonPreempt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process1> processes = new ArrayList<>();

        System.out.println("Enter Arrival, Burst, Priority:");
        for (int i = 0; i < n; i++) {
            System.out.println("For process " + (i + 1) + ":");
            
            int arrivalTime = scanner.nextInt();           
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process1 process = new Process1(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

       
        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            Process1 selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process1 process : processes) {
                if (process.arrivalTime <= currentTime && !process.completed && process.priority < highestPriority) {
                    highestPriority = process.priority;
                    selectedProcess = process;
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            selectedProcess.startTime = currentTime; 
            selectedProcess.completionTime = selectedProcess.startTime + selectedProcess.burstTime;
            //selectedProcess.completionTime = currentTime + selectedProcess.burstTime;
            currentTime += selectedProcess.burstTime; 
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;

            completedProcesses++;
        }

        float AverageWaitingTime = 0f, AverageTurnAroundTime = 0f;
        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tPriority\t\tStart\t\tCompletion\t\tWaiting\t\tTurnaround");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Process1 process : processes) {
        	AverageTurnAroundTime += process.turnaroundTime;
        	AverageWaitingTime += process.waitingTime;
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.tempBT + "\t\t" +process.priority+"\t\t\t"+
                    process.startTime + "\t\t" + process.completionTime + "\t\t\t" + process.waitingTime +
                    "\t\t\t" + process.turnaroundTime);
        }
        
        System.out.println("Average Waiting Time : "+ (float)(AverageWaitingTime/n));
        System.out.println("Average TurnAround Time : "+ (float)(AverageTurnAroundTime/n));
        
        

        scanner.close();
    }
}