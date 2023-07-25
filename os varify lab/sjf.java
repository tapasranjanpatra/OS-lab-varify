

import java.util.*;

class Process6 {
	int id;
	int arrivalTime;
	int burstTime;
	int startTime;
	int completionTime;
	int waitingTime;
	int turnAroundTime;
	Boolean completed = false;

	Process6(int id, int arr, int bt) {
		this.id = id;
		this.arrivalTime = arr;
		this.burstTime = bt;
	}

}

public class sjf {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the number of processes: ");
		int n = scanner.nextInt();

		// Create an array of processes
		Process6[] processes = new Process6[n];
		List<Process6> processList = new ArrayList<Process6>();

		System.out.println("Enter Arrival and Burst time :");
		// Get arrival time and burst time for each process
		for (int i = 0; i < n; i++) {

			System.out.println("For process " + (i + 1) + " : ");
			int arr = scanner.nextInt();
			int bt = scanner.nextInt();
			Process6 p = new Process6(i + 1, arr, bt);
			processList.add(p);
		}

		
		processList.sort(Comparator.comparingInt((Process6 p) -> p.arrivalTime));

		int helper = 0;
		int currentTime = 0; // Current time
		float totalTurnAroundTime = 0;
		float totalWaitingTime = 0;

		System.out.println(
				"\nProcess\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tWaiting Time\tTurnaround Time");
		int numProcess = 0;
		// Calculate scheduling metrics for each process
		while (numProcess < n) {

			Process6 currentProcess = null;
			int lowestBT = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {

				if (processList.get(i).arrivalTime <= currentTime && processList.get(i).burstTime < lowestBT
						&& processList.get(i).completed == false) {
					currentProcess = processList.get(i);
					lowestBT = processList.get(i).burstTime;
				}

			}

			if (currentProcess == null) {
				currentTime++;
				continue;
			}

			// Calculate starting time and completion time
			currentProcess.startTime = currentTime;
			currentProcess.completionTime = currentTime + currentProcess.burstTime;

			// Calculate turnaround time
			currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;

			// Update current time
			currentTime = currentProcess.completionTime;

			// Waiting time
			currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

			// Calculate total turnaround time and total completion time
			totalTurnAroundTime += currentProcess.turnAroundTime;
			totalWaitingTime += currentProcess.waitingTime;

			// Print process details
			System.out.printf("%-8d%-16d%-16d%-16d%-20d%-16d%-16d\n", currentProcess.id, currentProcess.arrivalTime,
					currentProcess.burstTime, currentProcess.startTime, currentProcess.completionTime,
					currentProcess.waitingTime, currentProcess.turnAroundTime);
			
			currentProcess.completed = true;
			numProcess++;
			helper = currentProcess.completionTime;
		}

	
       
		float avgTurnAroundTime = (totalTurnAroundTime / processes.length);
		float avgWaitingTime = (totalWaitingTime / processes.length);

		System.out.println("\nAverage Turnaround Time: " + avgTurnAroundTime);
		System.out.println("Average Waiting Time: " + avgWaitingTime);
		System.out.println("Throughput : " + (float) processes.length / helper);
		scanner.close();
	}
}
