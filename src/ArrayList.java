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

            // if the list is already sorted, we just need to check if the last element is larger than the next to last
            // element
            if(isSorted){
                if( size < 2 || list[size - 2].compareTo(element) < 0){
                    isSorted = true;
                }else { // otherwise we have check the whole array to see if it's sorted
                    isSorted = checkIfSorted();
                }
            }

            // returns true if the element was added to the array
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

            // updates isSorted
            // if the list is already sorted, we can compare the elements at the indexes to the left and right of the added element
            // have to have account for if the index is the beginning or the end of the list so we don't try and access out of the bounds of the array
            if (isSorted){
                // element added to end of list
               if (index == size - 1){
                   if (element.compareTo(list[index - 1 ]) < 0){
                       isSorted = false;
                   }
               // element added to beginning of list
               }else if(index == 0){
                   if (element.compareTo(list[1]) > 0){
                       isSorted = false;
                   }
                // element added to the middle of the list
                }else if (element.compareTo(list[index-1]) < 0 || element.compareTo(list[index+1]) > 0){
                   isSorted = false;
               }
            } else{ // if it's not sorted initially we have to check the whole array
                isSorted = checkIfSorted();
            }

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
        // update the size of the list
        size = 0;
        // update isSorted variable
        isSorted = true;
    }

    // returns the element at certain index
    // returns null if index is invalid
    public T get(int index) {
        if(index >= 0 && index < list.length){
            return list[index];
        }
        return null;
    }

    // returns the index of the first instance of the element in the array, if it exists
    // returns -1 otherwise
    public int indexOf(T element) {
        if (element != null && !isEmpty()) {

            if(isSorted){
                if(element.compareTo(list[0]) < 0 || element.compareTo(list[size -1]) > 0){
                    return -1;
                }
            }
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
        if(isSorted){
            return;
        }
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

    // removes element at index and returns it if the index is valid
    @Override
    public T remove(int index) {

        // checks if the index is valid
        if (index >= 0 && index < size) {

            // Create a new array of type T that whose size is one smaller to account for the removed element
            T[] shiftedArray = (T[]) new Comparable[ list.length - 1];

            // copies the elements from the original array before the removed element into the new array
            for(int i = 0; i < index; i++){
                shiftedArray[i] = list[i];
            }

            // copies the elements of the original array behind the removed element into the new array
            for(int i = index + 1; i < size; i++){
                shiftedArray[i-1] = list[i];
            }
            T removedElement = list[index];
            // updates the size variable to account for removed element
            size--;

            // reassigns the original to the new array
            list = shiftedArray;

            // if the array was originally sorted, removing an element won't affect if it is still sorted
            if(!isSorted){
                // if it wasn't sorted we have to check the whole array to update isSorted
                isSorted = checkIfSorted();
            }

            return removedElement;
        }

        return null;
    }

    // condenses the array to only include instances of the passed in element
    @Override
    public void equalTo(T element) {

       if (element != null){
           int numInstances = 0; // keeps track of the number of times the element has been found in th array
           int firstEmpty   = 0; // keeps track of the index at which we can put the element if we were to find one in the array

           // iterates through the array, setting each element to null
           for (int i = 0; i < size; i++) {
               if (list[i] != element){
                   list[i] = null;
               }else {
                  // if the  element is found and first empty does not equal the current index, the element at i
                  // is coppied into the array at the firstEmpty index

                   // my attempt to make algorithm more efficient if the array is already sorted
                   if(isSorted && list[i+1] != element){
                       list[firstEmpty] = list[i];
                       numInstances++;
                       for(int j = i +1; j < size; j++){
                           list[j] = null;
                       }
                       size = numInstances;
                       return;

                   }
                   if(firstEmpty != i) {
                       list[firstEmpty] = list[i];
                       list[i] = null;
                       numInstances++;
                       firstEmpty++;
                   }else{
                       // if first empty equals the current index, element is in the right place
                       numInstances++;
                       firstEmpty++;
                   }
               }
           }
           // the array will always be sorted
           isSorted = true;
           size = numInstances;
       }
    }

    // reverses the elements in the array
    @Override
    public void reverse() {
        T temp;
        // reversing an array only takes size/2 iterations because you can progressively swap the i and size-1-i elements
        // of the array
        for(int i = 0; i < (size)/2; i++){
            temp = list[i]; // need a temp in order to swap elements in an arrau
            list[i] = list[size-1-i];
            list[size-1-i] = temp;
        }

        // we can't know for sure if the array is not sorted after reverse even if it was sorted beforehand
        // because an array with values 1 1 1 is still sorted after a reverse
        isSorted = checkIfSorted();
    }



    @Override
    // merges two lists together in sorted order
    public void merge(List<T> otherList) {
        // casts otherList into an ArrayList
        ArrayList<T> other = (ArrayList<T>) otherList;

        sort();
        other.sort();

        // keeps track of the index of the next item that can be added in each list
        // incremented after an element was used from that list
        int arrayIndex = 0;
        int otherArrayIndex = 0;
        int totalSize = size + other.size;

        // initiallize a new array with the size of both lists combined
        T[] mergedArray = (T[]) new Comparable[totalSize];


        // iterates a number of times equal to the size of both lists combined
        // once one array is depleted of elements, we can add the rest of the other arrays elements to the new list
        // this is because the list is already sorted
        for(int i = 0; i < totalSize; i++){

            if(arrayIndex == size){ // if the original list is out of items to add, we only add items from the other list
                mergedArray[i] = other.get(otherArrayIndex);
                otherArrayIndex++;

            }else if(otherArrayIndex == other.size){ // if the other list is out of items to add, we only add from the original list
                mergedArray[i] = list[arrayIndex];
                arrayIndex++;

            } else if (list[arrayIndex].compareTo(other.get(otherArrayIndex)) < 0){ // otherwise we compare the items at the indexes we have been updating
                // if the next element in the original array is smaller we place it in the new list
                mergedArray[i] = list[arrayIndex];
                arrayIndex++;
            } else {
                // if the next element in the other array is smaller we place it in the new list
                mergedArray[i] = other.get(otherArrayIndex);
                otherArrayIndex++;
                }
            }

        // once the merged list has been completed it's assigned to the list instance variable along with updating the size
        list = mergedArray;
        size = totalSize;
        isSorted = true;
    }

    @Override
    // swaps elements two at a time with each other up the list
    public void pairSwap() {
        T temp;
        // increases i by two every iteration to skip over the element uou just swapped
        for (int i = 0; i < size - 1; i = i +2){
            // uses a temp to enable swapping
            temp = list[i];

            // swaps the element at the index and one index higher
            list[i] = list[i+1];
            list[i+1] = temp;
        }
        // updates the isSorted variable
        isSorted = checkIfSorted();
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    public String toString(){
        String listString = "";

        for(int i = 0; i< list.length; i++){
            listString += list[i] + " ";
        }
        return listString;
    }

}
