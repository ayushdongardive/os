import java.util.*;

public class my_page_replacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter the frame numners:-");
        int frames = sc.nextInt();
        System.out.println("enter size of page:- ");
        int pages = sc.nextInt();
        int[] arr = new int[pages];
        System.out.println("enter the reference string");
        for(int i=0; i<pages; i++){
            arr[i] = sc.nextInt();
        }

        //1. FIFO
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        int pagefault =0;

        for(int c : arr){
            if(!set.contains(c)){
                pagefault++;
                if(queue.size() == frames){
                    int remove = queue.poll();
                    set.remove(remove);
                }
                set.add(c);
                queue.offer(c);
            }
        }
        System.out.println("page falut :- "+pagefault);

        //2. LRU :- least resently used
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer,Integer> map = new HashMap<>();
        pagefault = 0;

        for(int i=0; i< arr.length; i++){
            int current = arr[i];
            if(!map.containsKey(current)){
                pagefault++;
                if(list.size() == frames){
                    int lru = Collections.min(map.entrySet(),Map.Entry.comparingByValue()).getKey();
                    list.remove(lru);
                    map.remove(lru);
                }
                list.add(arr[i]);
                map.put(arr[i],i);
            }
        }
        System.out.println("LRU:- "+pagefault);

        //3. Optimal page fault
        pagefault = 0;
        ArrayList<Integer> list2 = new ArrayList<>();
        int far = -1;
        int position = -1;

        for(int i=0; i<arr.length; i++){
            int current = arr[i];
            if(!list2.contains(arr[i])){
                pagefault++;
                if(list2.size()== frames){
                    for(int j=0; j<frames; j++){
                        int next = far_fn(arr,i,list2.get(j));
                        if(next > far){
                            far = next;
                            position = j;
                        }
                    }
                    list2.set(position,current);

                }else {
                    list2.add(current);
                }

                System.out.println("optimal:- " +pagefault);
            }
        }



    }
    public static int far_fn(int[] arr, int current, int page ){

        for(int i=current+1; i<arr.length; i++){
            if(arr[i] == page){
                return i;
            }
        }
        return Integer.MAX_VALUE;

    }
}
