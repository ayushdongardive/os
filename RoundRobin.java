import java.util.ArrayList;

public class RoundRobin {
    static int[] process = {0, 1, 2, 3, 4};
    static int[] originalBT = {3,6,4,5,2}; // Original Burst Time to use in final printout
    static int TQ = 2; // Time Quantum
    static int[] AT = {0,2,4,6,8};
    static int[] BT = {3,6,4,5,2};
    static int[] FT = new int[5];
    static int[] TT = new int[5];
    static int[] WT = new int[5];

    void Calculate() {
        int timer = 0, totalTime = 0;
        int processCount = 1;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(process[0]);

        while (!list.isEmpty()) {
            int currentProcess = list.remove(0);
            while (timer < TQ) {
                if (BT[currentProcess] == 0) {
                    break;
                }
                totalTime++;
                timer++;
                BT[currentProcess] = BT[currentProcess] - 1;
                if (processCount < 5 && AT[processCount] == totalTime) {
                    list.add(processCount);
                    processCount++;
                }
            }
            if (BT[currentProcess] != 0) {
                list.add(currentProcess);
            } else {
                FT[currentProcess] = totalTime;
            }
            timer = 0;
        }

        for (int i = 0; i < 5; i++) {
            TT[i] = FT[i] - AT[i];
            WT[i] = TT[i] - originalBT[i];
        }
    }

    public static void main(String[] args) {
        RoundRobin r = new RoundRobin();
        r.Calculate();
        System.out.println("RR Scheduling: ");
        System.out.println("Process    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%-10d %-13d %-11d %-12d %-11d %-12d\n", process[i], AT[i], originalBT[i], FT[i], TT[i], WT[i]);
        }
    }
}
