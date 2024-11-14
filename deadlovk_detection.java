import java.util.Scanner;
import java.util.Arrays;

public class Detection {
    int processNum, resourceNum;
    int[][] allocated, request;
    int[] available;
    boolean[] finished;

    void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes:");
        processNum = sc.nextInt();
        System.out.println("Enter number of resources:");
        resourceNum = sc.nextInt();

        allocated = new int[processNum][resourceNum];
        request = new int[processNum][resourceNum];
        available = new int[resourceNum];
        finished = new boolean[processNum];

        System.out.println("Enter Allocation matrix:");
        for (int i = 0;i<processNum;i++){
            for(int j= 0;j<resourceNum;j++){
                allocated[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter Request matrix:");
        for (int i = 0;i<processNum;i++){
            for(int j= 0;j<resourceNum;j++){
                request[i][j] = sc.nextInt();
                if (request[i][j] != 0) {
                    finished[i] = false; // If there's a request, mark the process as not finished
                }
            }
        }

        System.out.println("Enter Available matrix:");
        for (int i = 0;i<resourceNum;i++){
            available[i] = sc.nextInt();
        }
    }

    void checkDeadlock(){
        int[] work = Arrays.copyOf(available, available.length);
        boolean deadlock = false;

        for (int i = 0; i < processNum; i++) {
            if (!finished[i]) {
                boolean canProceed = true;
                for (int j = 0; j < resourceNum; j++) {
                    if (request[i][j] > work[j]) {
                        canProceed = false;
                        break;
                    }
                }
                if (canProceed) {
                    // Process can proceed, release resources
                    for (int j = 0; j < resourceNum; j++) {
                        work[j] += allocated[i][j];
                    }
                    finished[i] = true;
                    i = -1; // Reset to check from the beginning
                }
            }
        }

        for (int i = 0; i < processNum; i++) {
            if (!finished[i]) {
                System.out.println("Process P" + i + " is deadlocked");
                deadlock = true;
            }
        }
        if (!deadlock) {
            System.out.println("No deadlock detected.");
        }
    }

    public static void main(String[] args) {
        Detection detect = new Detection();
        detect.init();
        detect.checkDeadlock();
    }
}
