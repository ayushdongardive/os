import java.util.ArrayList;
import java.util.List;

public class SJF_NP {
    static int[] p = {1, 2, 3, 4, 5};  // Process IDs
    static int[] at = {0, 2, 4, 6, 8}; // Arrival times
    static int[] bt = {3, 6, 4, 5, 2}; // Burst times
    static int[] ft = new int[5];      // Finish times
    static int[] tt = new int[5];      // Turnaround times
    static int[] wt = new int[5];      // Waiting times

    void sjfNP() {
        List<Integer> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int totalTime = 0;
        readyQueue.add(0);

        while (!readyQueue.isEmpty()) {
            int i = readyQueue.remove(0); //  first process from the ready queue

            while (currentTime < bt[i]) {
                currentTime++;
                totalTime++;

                // checking arriving processes and add to the ready queue
                for (int j =0; j < p.length; j++) {
                    if (j != i && !readyQueue.contains(j) && totalTime == at[j]) {
                        int k;
                        for (k = 0; k < readyQueue.size(); k++) {
                            if (bt[readyQueue.get(k)] > bt[j]) {
                                break;
                            }
                        }
                        readyQueue.add(k, j);
                    }
                }
            }
            ft[i] = totalTime;
            currentTime = 0;   // Reset currentTime for next process
        }


        for (int i = 0; i < p.length; i++) {
            tt[i] = ft[i] - at[i];
            wt[i] = tt[i] - bt[i];
        }
    }

    void sjfP() {
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

            // Find the process with the shortest remaining time in the ready queue
            int shortestJobIndex = -1;
            int shortestRemainingTime = Integer.MAX_VALUE;
            for (int i : readyQueue) {
                if (rt[i] < shortestRemainingTime) {
                    shortestRemainingTime = rt[i];
                    shortestJobIndex = i;
                }
            }

            if (shortestJobIndex != -1) {
                // Execute the process with the shortest remaining time
                rt[shortestJobIndex]--;
                currentTime++;

                // If the process is finished
                if (rt[shortestJobIndex] == 0) {
                    ft[shortestJobIndex] = currentTime;
                    tt[shortestJobIndex] = ft[shortestJobIndex] - at[shortestJobIndex];
                    wt[shortestJobIndex] = tt[shortestJobIndex] - bt[shortestJobIndex];
                    readyQueue.remove(Integer.valueOf(shortestJobIndex));
                    completedProcesses++;
                }
            } else {
                // If no process is ready to execute, increment the current time
                currentTime++;
            }
        }
    }


    public static void main(String[] args) {
        SJF_NP scheduler = new SJF_NP();
        scheduler.sjfNP();

        System.out.println("SJF Scheduling: ");
        System.out.println("Process    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.println(p[i] + "         " + at[i] + "               " + bt[i] + "           " + ft[i] + "          " +  tt[i] + "           " + wt[i]);
        }

        scheduler.sjfP();

        System.out.println("SJF Scheduling: ");
        System.out.println("Process    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.println(p[i] + "         " + at[i] + "               " + bt[i] + "           " + ft[i] + "          " +  tt[i] + "           " + wt[i]);
        }
    }
}
