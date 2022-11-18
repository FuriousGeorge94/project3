public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] list;
    private int size = 0;

    private boolean isSorted = true;

    public ArrayList(){
        this.list = (T[]) new Comparable[2];
    }

    // use to verify if the list is sorted
    public boolean checkIfSorted(){

        // if the size if the list is one or zero, the list is already sorted
        if(size <= 1){
            return true;
        }

        // if the element at an index one lower than the element at the current index is larger, the list is not sorted
        for(int i = 0 ; i < size - 1; i++){
           if (list[i].compareTo(list[i+1]) > 0) {
               return false;
           }
        }

        // returns true if the for loop completes checking all the values in the array
        return true;
    }

    // Used to resize an array if added elements surpass its length
    public void resizeArray(){

        // creates a new array of type T that's double the length of the original
        T[] newList = (T[]) new Comparable[list.length * 2];

        // coppies the elements from the original array into the ne array
        for (int i = 0; i < size(); i++){
           newList[i] = list[i];
        }

        // assigns the new array to the variable holding the original array
        list = newList;
    }
    @Override
    // adds an element to the end of the list
    public boolean add(T element) {

        if (element != null) {
            // resizes the array to double its length if array is full
            if (size >= list.length) {
                resizeArray();
            }
            // increments the size variable because we added to the list
            size ++;
            // adds the element to the end of the list
            list[size - 1] = element;

            // returns true if the element was addedto the array
            return true;
        }
        // returns false if the element null
        return false;
    }

    // adds an element at a specific index in the array
    @Override
    public boolean add(int index, T element) {

        // checks if the element and index is valid
        if (element != null && index >= 0 && index < size){

            // create a new array of type T that can hold one more element than the original array
            T[] shiftedArray = (T[]) new Comparable[ list.length + 1];

            // increases the size instance variable because we are adding to the array
            size++;

            // coppies the values from before the index of the added value in to the new array
            for(int i =0; i < index ; i++){
                shiftedArray[i] = list[i];
            }
            // adds the element to the index of the new array
            shiftedArray[index] = element;

            // coppies the rest of the values after the index into the new array
            for(int i = index + 1; i < shiftedArray.length; i++){
                shiftedArray[i] = list[i-1];
            }

            // reassigns the new array to the original array
            list = shiftedArray;

            return true;
        }
        return false;
    }

    // sets all the elements of an array to zero
    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            list[i] = null;
        }
        size = 0;
    }

    /**
     * Return the element at given index. If index is
     * out-of-bounds, it will return null.
     *
     * @param index index to obtain from the list.
     * @return the element at the given index.
     */
    @Override
    public T get(int index) {
        if(index >= 0 && index < list.length){
            return list[index];
        }
        return null;
    }
/**
     * Return the first index of element in the list. If element
     * is null or not found in the list, return -1. If isSorted is
     * true, uses the ordering of the list to increase the efficiency
     * of the search.
     */
    @Override
    public int indexOf(T element) {
        if (element != null) {
            // traverses the array checking if the element at the index is equal to the passed in element
            // if so return the index
            for (int i = 0; i < size; i++) {
                if (list[i] == element) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        if(size < 2){
            return;
        }

        // utilize insertion sort to sort the array

        T temp; // needed to swap the position two elements in the array

        // iterates through the array, keeping every element in the array to the left of the index sorted

        // outer loop iterates through the array
        // The inner loop moves backwards through the sorted part of the array and inserts the current element
        // at the index where the element being looked at is greater than it
        for(int i = 0; i < size; i++){
            for(int j = i - 1; j >= 0 && list[j+1].compareTo(list[j]) < 0 ; j--){
                temp = list[j+1];
                list[j+1] = list[j];
                list[j] = temp;
            }
        }

        isSorted = true;
    }
    // 0 1 2 3 4
 /**
     * Remove whatever is at index 'index' in the list and return
     * it. If index is out-of-bounds, return null. For the ArrayList,
     * elements to the right of index should be shifted over to maintain
     * contiguous storage. Must check to see if the list is sorted after removal
     * of the element at the given index and updates isSorted accordingly.
     *
     * @param index position to remove from the list.
     * @return the removed element.
     */
    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T[] shiftedArray = (T[]) new Comparable[ list.length - 1];
            for(int i = 0; i < index; i++){
                shiftedArray[i] = list[i];
            }

            for(int i = index + 1; i < size; i++){
                shiftedArray[i-1] = list[i];
            }
            T removedElement = list[index];
            size--;
            list = shiftedArray;
            return removedElement;
        }

        return null;
    }

    @Override
    public void equalTo(T element) {
       if (element != null){
       }



    }

    @Override
    public void reverse() {

    }

    @Override
    public void merge(List<T> otherList) {

    }

    @Override
    public void pairSwap() {

    }

    @Override
    public boolean isSorted() {
        return false;
    }

    public String toString(){
        String listString = "";

        for(int i = 0; i< list.length; i++){
            listString += list[i] + " ";
        }

        return listString;
    }

}
