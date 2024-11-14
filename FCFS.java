public class FCFS {
    static int[] process = {1,2,3,4,5};
    static int[] AT = {0,2,4,6,8};
    static int[] BT = {3,6,4,5,2};
    static int[] FT = new int[5];
    static int[] TT = new int[5];
    static int[] WT = new int[5];

    void fcfs(){

        int time = 0;

        for(int i=0;i<5;i++){
            FT[i] = time + BT[i];
            time += BT[i];
        }
        for(int i=0;i<5;i++){

            TT[i] = FT[i] - AT[i];
        }
        for(int i=0;i<5;i++){
            WT[i] = TT[i] - BT[i];
        }
    }
    void averageTiming(){
        float avgTT=0;
        float avgWT=0;
        for(int i=0;i<5;i++){
            avgTT += TT[i];
        }
        avgTT = avgTT/5;
        System.out.println("Average Turn around time = "+avgTT);

        for(int i=0;i<5;i++){
            avgWT += WT[i];
        }
        avgWT = avgWT/5;
        System.out.println("Average Waiting time = "+avgWT);

    }

    public static void main(String[] args) {
        FCFS example = new FCFS();
        example.fcfs();

        System.out.println("FCFS Scheduling: ");
        System.out.println("Process    ArrivalTime    BurstTime   FinishTime   TotalTime   WaitingTime");
        for (int i = 0; i < 5; i++) {
            System.out.println(process[i] + "         " + AT[i] + "               " + BT[i] + "           " + FT[i] + "            "  + TT[i] + "            " + WT[i]);
        }

        example.averageTiming();
    }
}
