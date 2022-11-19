public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList<>();
        for(int i = 0; i< 3; i++){
            for(int j= 0; j < 3; j++){
               list.add(j);
            }
            for(int j= 0; j < 3; j++){
                list.remove(0);
            }

        }




    }
}
