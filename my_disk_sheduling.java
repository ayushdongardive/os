import javax.imageio.ImageTranscoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class my_disk_sheduling {
    public static void main(String[] args) {
        int[] arr = {82,170,43,140,24,16,190};
        int head = 50;

        //1. first come first serve
        head = 50;
        int seek_time = 0;
        for(int i=0; i<arr.length; i++){
            seek_time = seek_time + Math.abs(arr[i] - head);
            head = arr[i];
        }
        System.out.println("seek time for FCFS:- "+ seek_time);

        //2. shortest seek time first
        head = 50;
        seek_time = 0;
        ArrayList<Integer> low = new ArrayList<>();
        ArrayList<Integer> high = new ArrayList<>();

        for(int c : arr){
            if(c < 50){
                low.add(c);
            }
            else {
                high.add(c);
            }
        }
        Collections.sort(low);
        Collections.sort(high);

        if(Math.abs(low.get(low.size()-1)-head) < Math.abs(high.get(high.size()-1)-head)){
            seek_time += Math.abs(low.get(0) - head);
            seek_time += Math.abs(low.get(0) - high.get(high.size()-1));

        }
        else{
            seek_time += Math.abs(high.get(high.size()-1)-head);
            seek_time += Math.abs(high.get(high.size()-1)-low.get(0));

        }
        System.out.println("SSTF :- "+ seek_time);

        //3. SCAN
        // let in higher' in one direction till end
        seek_time = 0;
        int[] temp = arr.clone();
        Arrays.sort(temp);

        seek_time += Math.abs(199 - head);
        seek_time += Math.abs(199 - temp[0]);

        System.out.println("SCAN:- " +seek_time);

        //. C-SCAN
        int current=0;
        for(int i : arr){
            if(i<head && current<i){
                current = i;
            }
        }
        seek_time = 0;
        seek_time += Math.abs(head - 199) + 199 + current;
        System.out.println("C-SCAN = "+seek_time);

    }
}
