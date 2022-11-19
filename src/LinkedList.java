public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node<T> head;
    private int endIndex;
    private Node<T> endNode;
    boolean isSorted;

    // constructor
    public LinkedList() {
        head = new Node<>(null);
        endNode = head;
        endIndex = -1;
        isSorted = true;
    }

    // adds an element to the end of the list
    public boolean add(T element) {

        if (element != null) {

            // create a node containing the element
            Node<T> next = new Node<>(element);

            // if list is sorted and not empty, check if the added element is less the last element in the list
            // if it is the list won't be sorted after the add;
            if (isSorted){
                if(endNode != head && endNode.getData().compareTo(next.getData()) > 0){
                    isSorted = false;
                }
            }else {
                isSorted = checkIfSorted();
            }

            // put the created node at the end of then update the end node variable and the end index variable
            endNode.setNext(next);
            endNode = next;
            endIndex = endIndex + 1;
            return true;// element successfully added
        }
        return false;// element not added
    }


    // adds an element at a specific index of the list
    public boolean add(int index, T element) {

        //create a new node containing the element
        Node<T> addedNode = new Node<>(element);

        if (element != null && (index >= 0 && index <= endIndex) && head.getNext() != null) {

            // if element and index are valid creates node equal to head, then traverses up the list to the index
            Node<T> currentElement = head;
            for (int i = 0; i < index; i++) {
                currentElement = currentElement.getNext();
            }

            addedNode.setNext(currentElement.getNext());
            currentElement.setNext(addedNode);
            endIndex = endIndex + 1;

            if(isSorted){
                if(index == 0){
                    if(addedNode.getData().compareTo(addedNode.getNext().getData()) > 0){
                        isSorted = false;
                    }
                } else if(addedNode.getData().compareTo(currentElement.getData()) < 0 || addedNode.getData().compareTo(addedNode.getNext().getData()) > 0){
                    isSorted = false;
                }
            }

            if(index == endIndex){
                endNode = getEndNode();
            }

            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        head.setNext(null);
        endNode = head;
        endIndex = -1;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        Node<T> currentElement;
        if(head.getNext() != null) {
            currentElement = head.getNext();
            if (index <= endIndex && index >= 0) {
                for (int i = 0; i < index; i++) {
                    currentElement = currentElement.getNext();
                }
                return (T) currentElement.getData();
            }
        }
            return null;
    }

    @Override
    public int indexOf(T element) {
        Node<T> currentElement = head.getNext();
        if (element == null) {
            return -1;
        }
        if (isSorted){
            if( endNode.getData() == null || endNode.getData().compareTo(element) < 0 || head.getNext().getData().compareTo(element) > 0){
                return -1;
            }
        }
        for (int i = 0; i <= endIndex; i++) {
            if (currentElement.getData() == element) {
                return i;
            }
            currentElement = currentElement.getNext();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    @Override
    public int size() {
        return endIndex + 1;
    }

    @Override
    public void sort() {
        // if the size of the list is less than two it's already sorted
        if(!isSorted) {
            if (size() >= 2) {
                //pointer and trailer need to rearrange list after a sort
                // also pointer is used to compare for the sort
                Node<T> pointer = head.getNext().getNext();
                Node<T> trailer = head.getNext();
                Node<T> compareNode; // what pointer is compared against

                // iterates through each element in the list
                for (int i = 0; i < size() - 1; i++) {
                    compareNode = head;

                    // Compares each element of the sorted part of the list with the current element
                    // if the pointer element is found to be less than the compareNode element it is inserted in that spot
                    // if the compareNode is the same Node as the pointer Node then it is in the correct spot in the currently sorted list
                    while (pointer.getData().compareTo(compareNode.getNext().getData()) > 0 && compareNode.getNext() != pointer) {
                        compareNode = compareNode.getNext();
                    }
                    if (compareNode.getNext() == pointer) { // if compareNode has traversed until it is pointing to the pointer node

                        // the list is sorted so far so if pointer is not the last node we simply increment the pointer
                        // and trailer to the node they are pointing to
                        if (pointer.getNext() != null) {
                            pointer = pointer.getNext();
                            trailer = trailer.getNext();
                        }
                    } else { // if the pointer node fits in the middle of the already sorted part of the list

                        // if the pointer not the last element have trailer point to what pointer is currently pointing at
                        if (pointer.getNext() != null) {
                            trailer.setNext(pointer.getNext());
                        }

                        // effecively moves pointer between compareNode and what compareNode is pointing to
                        pointer.setNext(compareNode.getNext());
                        compareNode.setNext(pointer);

                        //increment the pointer to the next element that needs to be sorted
                        pointer = trailer.getNext();
                    }
                }
                Node<T> endNode = head.getNext();
                for (int i = 0; i < size() - 1; i++) {
                    endNode = endNode.getNext();
                }
                endNode.setNext(null);
            }
            endNode = getEndNode();
        }
        isSorted = true;
    }

    // 0 1 2 6 8 9 5 1
    @Override
    public T remove(int index) {
        T removedData;
        Node<T> trailer = head;

        if (index == endIndex) {
            endNode = getEndNode();
        }

        if (index >= 0 && index <= endIndex) {
            Node<T> pointer = head.getNext();
            for (int i = 0; i < index; i++) {
                trailer = pointer;
                pointer = pointer.getNext();
            }
            removedData = pointer.getData();
            if (index == endIndex) {
                trailer.setNext(null);
            } else {
                trailer.setNext(pointer.getNext());
            }
            endIndex--;
            isSorted = checkIfSorted();
        } else {
            isSorted = checkIfSorted();
            return null;
        }
        if(size() ==0 ){
            endNode = head;
        }
        return removedData;
    }

    @Override
    public void equalTo(T element) {
        Node<T> pointer = head.getNext();
        Node<T> trailer = head;
        boolean hasElement = false;
        if (isSorted) {
            if (endNode.getData() == null || endNode.getData().compareTo(element) < 0 || head.getNext().getData().compareTo(element) > 0) {
                head.setNext(null);
            }
        }
        if (element != null) {
            int updatedEndIndex = -1;
            for (int i = 0; i < size(); i++) {
                if (pointer.getData() == element) {
                    updatedEndIndex++;
                    if(isSorted){
                        head.setNext(pointer);
                        while(pointer.getNext() != null && pointer.getNext().getData() == element){
                            pointer = pointer.getNext();
                            updatedEndIndex++;
                        }
                        pointer.setNext(null);
                        endIndex = updatedEndIndex;
                        isSorted = true;
                        return;
                    }
                    hasElement = true;
                    trailer.setNext(pointer);
                    trailer = pointer;
                }
                pointer = pointer.getNext();
            }
            if (!hasElement) {
                head.setNext(null);
            }

            endIndex = updatedEndIndex;
            endNode = getEndNode();

        }
        isSorted = true;
    }

    @Override
    public void reverse() {
        Node<T> currentElement = head.getNext(); // 1
        Node<T> nextElement = currentElement.getNext();// 2
        Node<T> temp = nextElement.getNext();

        endNode = currentElement;

        currentElement.setNext(null);
        nextElement.setNext(currentElement);
        currentElement = nextElement;
        nextElement = temp;

        for (int i = 1; i < size() - 1; i++) {
            temp = nextElement.getNext(); // 3
            nextElement.setNext(currentElement); // 2, 1
            currentElement = nextElement; // 2
            nextElement = temp; // 3

        }
        head.setNext(currentElement);
        isSorted = checkIfSorted();
    }

    @Override
    public void merge(List<T> otherList) {
        if (otherList != null) {
            LinkedList<T> other = (LinkedList<T>) otherList;
            sort();
            other.sort();

            boolean firstListFinished = false;
            boolean otherListFinished = false;

            Node<T> firstListPointer = head.getNext();
            Node<T> otherListPointer = other.head.getNext();
            Node<T> lastAddedElement = null;
            LinkedList<T> newList = new LinkedList<T>();
            Node<T> currentNode = newList.head;


            for (int i = 0; i < (size() + other.size() - 1); i++) {
                if (firstListFinished) {
                    currentNode.setNext(otherListPointer);
                    head.setNext(newList.head.getNext());
                    endIndex = size() + other.size() - 1;
                    return;
                }
                if (otherListFinished) {
                    lastAddedElement.setNext(firstListPointer);
                    head.setNext(newList.head.getNext());
                    endIndex = size() + other.size() - 1;
                    return;
                }

                if (firstListPointer.getData().compareTo(otherListPointer.getData()) <= 0) {
                    newList.add(firstListPointer.getData());
                    currentNode = currentNode.getNext();
                    if (firstListPointer.getNext() != null) {
                        firstListPointer = firstListPointer.getNext();
                    } else {
                        firstListFinished = true;
                    }
                    lastAddedElement = firstListPointer;
                } else {
                    newList.add(otherListPointer.getData());
                    currentNode = currentNode.getNext();
                    if (otherListPointer.getNext() != null) {
                        otherListPointer = otherListPointer.getNext();
                    } else {
                        otherListFinished = true;
                    }
                    lastAddedElement = otherListPointer;
                }
            }
            endIndex = size() + other.size() - 1;
            head.setNext(newList.head.getNext());
        }

        isSorted = true;
        endNode = getEndNode();
    }

    @Override
    public void pairSwap() {
        if (size() > 1) {
            Node<T> pointer = head.getNext();
            Node<T> trailer = head;

            while (pointer.getNext() != null) {
                trailer.setNext(pointer.getNext());
                pointer.setNext(pointer.getNext().getNext());
                trailer.getNext().setNext(pointer);
                trailer = pointer;
                if (pointer.getNext() != null) {
                    pointer = pointer.getNext();
                }
            }

        }
        isSorted = checkIfSorted();
        endNode = getEndNode();
    }

    public Node<T> getEndNode() {
        int i = 0;
        endNode = head;
        while (i < size()) {
            i++;
            endNode = endNode.getNext();
        }
        return endNode;
    }

    @Override
    public boolean isSorted() {
        return isSorted;

    }

    public String toString() {
        Node<T> pointer = head.getNext();
        String listString = "";
        for (int i = 0; i < size(); i++) {
            listString += pointer.getData() + "\n";
            pointer = pointer.getNext();
        }
        return listString;
     }

     public boolean checkIfSorted(){
        Node<T> pointer = head;
        while(pointer.getNext() != null){
            pointer = pointer.getNext();
            if ( pointer.getNext() != null && pointer.getData().compareTo(pointer.getNext().getData()) > 0){
                return false;
            }
        }
        return true;
     }

    }


