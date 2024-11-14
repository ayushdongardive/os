import java.util.*;
public class my_bankers {
    public static void main(String[] args) {

        System.out.println("let there are 2 processes p1 and p1 & and 2 recourses A and B");
        System.out.println("set the recouse count for A and B");
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int count= 0;

        // Allocation, max-need, Available
        int[][] allocation = new int[2][2];
        int[][] max_need = new int[2][2];
        int[] available = new int[2];
        int[][] remaning_need= new int[2][2];

        System.out.println("write allocation resourse for each process:-");
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                allocation[i][j] = sc.nextInt();
            }
        }

        if(allocation[0][0] + allocation[1][0] > A || allocation[0][1] + allocation[1][1] > B){
            System.out.println("deadlock detected; not safe");
            return;
        }
        else{
            A = A - (allocation[0][0]+allocation[1][0]);
            B = B - (allocation[1][0]+allocation[1][1]);
        }
        available[0] = A;
        available[1] = B;



        System.out.println("write max_need resourse for each process:-");
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                max_need[i][j] = sc.nextInt();
            }
        }

        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                remaning_need[i][j] = max_need[i][j] - allocation[i][j];
            }
        }

        System.out.println("remaining need will be");
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                System.out.print(remaning_need[i][j]);;
            }
            System.out.println();
        }

        int complete = 0;
        int rest = 0;

        System.out.println("available "+available[0]+" "+available[1]);

        for(int i=0; i<2; i++){
            if(remaning_need[i][0] <= available[0] && remaning_need[i][1] <= available[1]){
                System.out.println(i+1+" process comppleted");
                available[0] = available[0]+remaning_need[i][0];
                available[1] = available[1]+remaning_need[i][1];
                remaning_need[i][0] = 0;
                remaning_need[i][1] = 0;
                complete++;
                rest = 0;
                if(complete == 2){
                    System.out.println("all cleared");
                    return;
                }

            }
            else{
                rest++;
                complete = 0;
                if(rest == 2){
                    System.out.println("deadlock detected");
                    return;
                }
            }
        }
        System.out.println("deadlock detected");
        return;



    }
}
