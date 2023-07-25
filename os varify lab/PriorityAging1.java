import java.util.*;

class Process3 {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int tempPriority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed ;
    int age;
  

    public Process3(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.tempPriority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
        this.age = 2;
    }
}

public class PriorityAging1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process3> processes = new ArrayList<>();

        System.out.println("Enter Arrival , Burst , Priority ");
        for (int i = 0; i < n; i++) {
            System.out.println("for process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process3 process = new Process3(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt((Process3 p) -> p.arrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            Process3 selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process3 process : processes) {
            	
              if(process.age == 0 && process.arrivalTime <= currentTime && process.priority < highestPriority  && !process.completed) {
            	  highestPriority = process.priority;
                  selectedProcess = process;
            	  
              }
            	
              else if (process.arrivalTime <= currentTime && process.priority < highestPriority && !process.completed) {
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
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;

            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;
            completedProcesses++;

            // Aging: Increase the priority of waiting processes
            for (Process3 process : processes) {
                if (process.arrivalTime <= currentTime && process != selectedProcess) {
                    process.priority--;
                    process.age -= 1;
                }
            }
        }

        float AverageWaitingTime = 0f , AverageTurnAroundTime = 0f;
        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tPriority\tCompletion\tWaiting\t\tTurnaround");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        
        int lastCT = 0;
        for (Process3 process : processes) {
        	AverageTurnAroundTime += process.turnaroundTime;
        	AverageWaitingTime += process.waitingTime;
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +process.tempPriority+"\t\t"+
                    + process.completionTime + "\t\t" + process.waitingTime +
                    "\t\t" + process.turnaroundTime);
                    lastCT = Math.max(lastCT, process.completionTime);
        }
        
        System.out.println("Average Waiting Time : " + AverageWaitingTime/n);
        System.out.println("Average TurnAround Time : " + AverageTurnAroundTime/n);
        System.out.println("Throughput : "+ (float)n/lastCT);

        scanner.close();
    }
}