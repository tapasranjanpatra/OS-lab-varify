
import java.util.Scanner;
import java.util.ArrayList;
class Process2{
	int id;
	int arrivalTime;
	int burstTime;
	int tempBT;
	int startingTime;
	int compleitionTime;
	int waitingTime;
	int turnAroundTime;
	Boolean completed = false;
	
	Process2(int id , int arrT , int bT){
		this.id = id;
		this.arrivalTime = arrT;
		this.burstTime = bT;
		this.startingTime = 0;
		this.compleitionTime = 0;
		this.waitingTime = 0;
		this.turnAroundTime = 0;
		this.tempBT = bT;
	}
}

public class SRTF1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Process2> processList = new ArrayList<Process2>();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of process : ");
		int n = sc.nextInt();
		
		System.out.println("Enter Arrival & Burst Time");
		for(int i=0;i<n;i++) {
			processList.add(new Process2(i+1,sc.nextInt(),sc.nextInt()));
		}
		
		int currentTime =0;
		int totalWaitingTime = 0;
		int totalTurnAroundTime = 0;
		int completedProcess = 0;
		int throughput = 0;
		
		System.out.println("PID\t\tArrival\t\tBurst\t\tCompleition\t\tWaiting\t\tTurnAround");
		while(completedProcess < n) {
			Process2 selectedProcess = null;
			int minBurstTime = Integer.MAX_VALUE;
			
			for(Process2 p : processList) {
				if(p.arrivalTime <= currentTime && p.burstTime <minBurstTime && !p.completed) {
					selectedProcess = p;
					minBurstTime = p.burstTime;
				}
			}
			
			if(selectedProcess == null) {
				currentTime++;
				continue;
			}else if(selectedProcess.burstTime > 1) {
				
			}else if(selectedProcess.burstTime == 1){
				completedProcess++;
				selectedProcess.completed =true;
			}
			
			selectedProcess.burstTime -= 1;
			selectedProcess.compleitionTime = currentTime + 1;
			
			selectedProcess.turnAroundTime = selectedProcess.compleitionTime - selectedProcess.arrivalTime;
			selectedProcess.waitingTime = selectedProcess.turnAroundTime - selectedProcess.tempBT;
			//currentTime += selectedProcess.burstTime;
			throughput = selectedProcess.compleitionTime;
			currentTime++;
			
			}
		
		for(Process2 selectedProcess : processList) {
			System.out.println(selectedProcess.id+"\t\t"+selectedProcess.arrivalTime+"\t\t"+selectedProcess.tempBT+"\t\t"+
					selectedProcess.compleitionTime+"\t\t\t"+selectedProcess.waitingTime+"\t\t"+selectedProcess.turnAroundTime);
			totalTurnAroundTime += selectedProcess.turnAroundTime;
			totalWaitingTime += selectedProcess.waitingTime;
		}
		System.out.println("Average Waiting Time : " + (float)totalWaitingTime/n);
		System.out.println("Average TurnAround Time : " + (float)totalTurnAroundTime/n);
		System.out.println("Throughput : " + (float)n/throughput);
	}

}