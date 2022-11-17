public class Main {
    public static void main(String[] args){
        LinkedList list = new LinkedList<>();
        LinkedList list2 = new LinkedList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);


        list2.add(2);
        list2.add(1);
        list2.add(50);
        list2.add(222);
        list2.add(2234);

        list.sort();
        System.out.println(list);
        list.pairSwap();
        System.out.println(list);
    }
}
