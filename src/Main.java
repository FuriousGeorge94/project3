public class Main {
    public static void main(String[] args) {
        ArrayList list = new ArrayList<>();
        ArrayList list2 = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);

        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(8);


        list.merge(list2);
        list.pairSwap();
        System.out.println(list);
        System.out.println(list.size());

    }
}
