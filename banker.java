import java.util.Scanner;
import java.util.Arrays;

public class banker {
    static int[][] allocated, need, max;
    static int[] available;
    static int processNum;
    static int resourceNum;
    static boolean[] finished;

    void init() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        processNum = sc.nextInt();

        System.out.println("Enter the number of resources: ");
        resourceNum = sc.nextInt();

        allocated = new int[processNum][resourceNum];
        max = new int[processNum][resourceNum];
        available = new int[resourceNum];
        need = new int[processNum][resourceNum];

        System.out.println("Enter the Allocation matrix: ");
        for (int i = 0; i < processNum; i++) {
            for (int j = 0; j < resourceNum; j++) {
                allocated[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter the Max matrix: ");
        for (int i = 0; i < processNum; i++) {
            for (int j = 0; j < resourceNum; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter the Available matrix: ");
        for (int i = 0; i < resourceNum; i++) {
            available[i] = sc.nextInt();
        }

        for (int i = 0; i < processNum; i++) {
            for (int j = 0; j < resourceNum; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }
    }

    boolean isSafe() {
        int[] work = Arrays.copyOf(available, available.length);
        boolean[] finish = new boolean[processNum];
        int[] safeSequence = new int[processNum];
        int count = 0;

        while (count < processNum) {
            boolean found = false;

            for (int i = 0; i < processNum; i++) {
                if (!finish[i]) {
                    boolean canAllocate = true;

                    for (int j = 0; j < resourceNum; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int j = 0; j < resourceNum; j++) {
                            work[j] += allocated[i][j];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("The system is not in a safe state.");
                return false;
            }
        }

        System.out.println("The system is in a safe state.");
        System.out.print("Safe sequence is: "+ Arrays.toString(safeSequence));

        return true;
    }

    void resourceRequest(int requestIndex, int[] request) {
        for (int i = 0; i < resourceNum; i++) {
            if (request[i] > need[requestIndex][i]) {
                System.out.println("Process exceeds its maximum need, cannot allocate.");
                return;
            }
        }

        for (int i = 0; i < resourceNum; i++) {
            if (request[i] > available[i]) {
                System.out.println("Resources are not available, cannot allocate.");
                return;
            }
        }

        // allocate resources
        for (int i = 0; i < resourceNum; i++) {
            available[i] -= request[i];
            allocated[requestIndex][i] += request[i];
            need[requestIndex][i] -= request[i];
        }

        // Check if the system is in a safe state
        if (isSafe()) {
            System.out.println("Request can be granted. System remains in a safe state.");
        } else {

            for (int i = 0; i < resourceNum; i++) {
                available[i] += request[i];
                allocated[requestIndex][i] -= request[i];
                need[requestIndex][i] += request[i];
            }
            System.out.println("Request cannot be granted. System would not be in a safe state.");
        }
    }

    public static void main(String[] args) {
        banker bankers = new banker();
        bankers.init();

        bankers.isSafe();

        int requestIndex = 1;
        int[] request = {1, 0, 2};

        System.out.println("Process P" + requestIndex + " requesting resources: " + Arrays.toString(request));
        bankers.resourceRequest(requestIndex, request);
    }
}
