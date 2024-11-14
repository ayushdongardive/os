import java.util.ArrayList;
import java.util.List;
public class Priority_NP {
    static int[] p = {1, 2, 3, 4, 5};  // Process IDs
    static int[] at = {0, 2, 4, 6, 8}; // Arrival times
    static int[] bt = {3, 6, 4, 5, 2}; // Burst times
    static int[] priority = {5, 4, 3, 2, 1}; //Priorities of process, lower number means higher priority
    static int[] ft = new int[5];      // Finish times
    static int[] tt = new int[5];      // Turnaround times
    static int[] wt = new int[5];      // Waiting times

    void priorityNP() {
        List<Integer> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int totalTime = 0;
        readyQueue.add(0); // Add the first process index to the ready queue

        while (!readyQueue.isEmpty()) {
            // Find the process with the highest priority in the ready queue
            int highestPriorityIndex = 0;
            for (int k = 1; k < readyQueue.size(); k++) {
                if (priority[readyQueue.get(k)] < priority[readyQueue.get(highestPriorityIndex)]) {
                    highestPriorityIndex = k;
                }
            }
            int i = readyQueue.remove(highestPriorityIndex); // Remove the process with the highest priority

            // Simulate process execution
            while (currentTime < bt[i]) {
                currentTime++;
                totalTime++;

                // Check for arriving processes and add to the ready queue
                for (int j = 0; j < p.length; j++) {
                    if (j != i && !readyQueue.contains(j) && totalTime == at[j]) {
                        readyQueue.add(j);
                    }
                }
            }
            // Process finishes execution
            ft[i] = totalTime; // Record finish time
            currentTime = 0;   // Reset currentTime for next process
        }

        // Calculate turnaround time and waiting time for each process
        for (int i = 0; i < p.length; i++) {
            tt[i] = ft[i] - at[i]; // Turnaround time
            wt[i] = tt[i] - bt[i]; // Waiting time
        }
    }
    void avgTime(){
        float avgTT=0;
        float avgWT=0;
        for(int i=0;i<5;i++){
            avgTT += tt[i];
        }
        avgTT = avgTT/p.length;
        System.out.println("Average Turn around time = "+avgTT);

        for(int i=0;i<5;i++){
            avgWT += wt[i];
        }
        avgWT = avgWT/p.length;
        System.out.println("Average Waiting time = "+avgWT);

    }
    void priorityP() {
        int[] rt = bt.clone();
        List<Integer> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < p.length) {
            // Add arriving processes to the ready queue
            for (int i = 0; i < p.length; i++) {
                if (at[i] == currentTime && !readyQueue.contains(i)) {
                    readyQueue.add(i);
                }
            }

            // Find the process with the highest priority in the ready queue
            int highestPriorityIndex = -1;
            int highestPriority = 999;
            for (int i : readyQueue) {
                if (priority[i] < highestPriority) {
                    highestPriority = priority[i];
                    highestPriorityIndex = i;
                }
            }

            if (highestPriorityIndex != -1) {
                // Execute the process with the highest priority
                rt[highestPriorityIndex]--;
                currentTime++;

                // If the process is finished
                if (rt[highestPriorityIndex] == 0) {
                    ft[highestPriorityIndex] = currentTime;
                    tt[highestPriorityIndex] = ft[highestPriorityIndex] - at[highestPriorityIndex];
                    wt[highestPriorityIndex] = tt[highestPriorityIndex] - bt[highestPriorityIndex];
                    readyQueue.remove(Integer.valueOf(highestPriorityIndex));
                    completedProcesses++;
                }
            } else {
                // If no process is ready to execute, increment the current time
                currentTime++;
            }
        }
    }

    public static void main(String[] args) {
        Priority_NP scheduler = new Priority_NP();
        scheduler.priorityNP();

        System.out.println("Priority Scheduling: ");
        System.out.println("Process    Priority    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.println(p[i] + "         " + priority[i] + "            "+ at[i] +"              " + bt[i] + "           " + ft[i] + "            " +  tt[i] + "            " + wt[i]);
        }

        scheduler.avgTime();

        scheduler.priorityP();

        System.out.println("Priority Scheduling: ");
        System.out.println("Process    Priority    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.println(p[i] + "         " + priority[i] + "            "+ at[i] +"              " + bt[i] + "           " + ft[i] + "            " +  tt[i] + "            " + wt[i]);
        }

        scheduler.avgTime();
    }
}
