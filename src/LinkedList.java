
//Written by George Thompson X500: thom7918
public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node<T> head;
    private int endIndex; // index of the last element in the list
    private Node<T> endNode; // last Node in the listj
    boolean isSorted;

    // constructor
    public LinkedList() {
        head = new Node<>(null);
        endNode = head;
        endIndex = -1;
        isSorted = true;
    }

    // adds an element to the end of the list if the element isn't null
    public boolean add(T element) {

        if (element != null) {

            // create a node containing the element
            Node<T> next = new Node<>(element);

            // if list is sorted and not empty, check if the added element is less the last element in the list
            // if it is the list won't be sorted after the add;
            if (isSorted) {
                if (endNode != head && endNode.getData().compareTo(next.getData()) > 0) {
                    isSorted = false;
                }
            } else {
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

            // set the node above the element we stopped on to the next variable of the node we want to add
            // then sets the next variable of the node we stopped on to the added node, inserting between the two in the list!
            addedNode.setNext(currentElement.getNext());
            currentElement.setNext(addedNode);
            endIndex = endIndex + 1;

            // if the list is sorted , compare the nodes in front and behind to tell if list is still sorted;
            if (isSorted) {
                if (index == 0) {
                    if (addedNode.getData().compareTo(addedNode.getNext().getData()) > 0) {
                        isSorted = false;
                    }
                    } else if( index == endIndex) {
                    if (addedNode.getData().compareTo(endNode.getData()) < 0) {
                        isSorted = false;
                    }
                }else
                if (addedNode.getData().compareTo(currentElement.getData()) < 0 || addedNode.getData().compareTo(addedNode.getNext().getData()) > 0) {
                    isSorted = false;
                }
            }else{// otherwise have to check if still sorted
                isSorted = checkIfSorted();
            }

            // if inserting at the end we have to change the variable tracking the last node in the list
            if (index == endIndex) {
                endNode = getEndNode();
            }

            return true;// node was added successfully
        }
        return false; // node wasn't added
    }

    // resets the list to an empty state
    public void clear() {
        head.setNext(null);
        endNode = head;
        endIndex = -1;
        isSorted = true;
    }


    // gets an element at a specific index and returns it
    public T get(int index) {
        Node<T> currentElement;
        if (head.getNext() != null) {

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
        if (isSorted) {
            if (endNode.getData() == null || endNode.getData().compareTo(element) < 0 || head.getNext().getData().compareTo(element) > 0) {
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
        if (!isSorted) {
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
                // update endNode when done sorting
                endNode = getEndNode();
                endNode.setNext(null);
            }
        }
        isSorted = true;
    }

    // removes an element from the list at the given index
    public T remove(int index) {

        T removedData;

        if (index == endIndex) {
            endNode = getEndNode();
        }

        if (index >= 0 && index <= endIndex) {
            // Use a pointer and trailer to keep track of where we are in the list
            Node<T> pointer = head.getNext();
            Node<T> trailer = head;

            //moves the pointer to the index of the element we want to remove
            //moves the trailer to the 1 - index of the element we want to remove
            for (int i = 0; i < index; i++) {
                trailer = pointer;
                pointer = pointer.getNext();
            }

            removedData = pointer.getData();

            // the index is at the end of the list, we simply set the trailer's next value to null
            if (index == endIndex) {
                trailer.setNext(null);
            } else { // otherwise we set the trailer to point at what the Node we want to remove is pointing at
                     // effectively removes the element from the list
                trailer.setNext(pointer.getNext());
            }
            // have to decrement endIndex a Node was removed
            endIndex--;

            isSorted = checkIfSorted();
        } else {
            return null; // index out of bounds
        }

        if (size() == 0) {// if the last element was removed
            endNode = head;
        }

        return removedData;
    }



        // reduces the list to only Nodes that whose data is equal to element
        // keeps original ordering
    public void equalTo(T element) {

        // pointer and trailer used to keep our place as we traverse through list
        Node<T> pointer = head.getNext();
        Node<T> trailer = head;
        boolean hasElement = false;// flag flipped if the element is found in the list

        // if the list is sorted we can check if the element is beteween the first element and last element, and if it is not
        // the element is not in the list, and we can clear the list and return
        if (isSorted) {
            if (endNode.getData() == null || endNode.getData().compareTo(element) < 0 || head.getNext().getData().compareTo(element) > 0) {
                clear();
                return;
            }
        }


        if (element != null) {

            int updatedEndIndex = -1; // keep a record of how many of element we have found

            // iterate through the array, and if we find the element we add it to the front of the list
            for (int i = 0; i < size(); i++) {
                if (pointer.getData() == element) {
                    updatedEndIndex++;

                    // if the list is sorted once we hit the first element we can iterate until the first non element,
                    // then we can set the remove the rest of the list
                    if (isSorted) {
                        head.setNext(pointer);
                        while (pointer.getNext() != null && pointer.getNext().getData() == element) {
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
                // itterate to the next Node if element was not found
                pointer = pointer.getNext();
            }

            if (!hasElement) { // if element isn't found clear the list
                clear();
            }


            endIndex = updatedEndIndex;
            endNode = getEndNode();

        }
        isSorted = true;
    }

    @Override
    public void reverse() {

        if (size() >2) {
            // put the first three nodes of the list into variables
            Node<T> currentElement = head.getNext();
            Node<T> nextElement = currentElement.getNext();
            Node<T> temp = nextElement.getNext();

            // special case at the start because we have to set the first element in the list to null
            // points the second node at the first node while having what the second node is pointing at originally stored
            endNode = currentElement;
            currentElement.setNext(null);
            nextElement.setNext(currentElement);
            currentElement = nextElement;
            nextElement = temp;

            // does the same steps until the list is reversed
            for (int i = 1; i < size() - 1; i++) {
                temp = nextElement.getNext(); // 3
                nextElement.setNext(currentElement); // 2, 1
                currentElement = nextElement; // 2
                nextElement = temp; // 3

            }
            // have the head point to the start of the reversed list
            head.setNext(currentElement);
            isSorted = checkIfSorted();
        } else if (size() == 2){// if size is 2 just have to point the head at the second element, the second at the first, and the first at null
            head.setNext(endNode);
            endNode.setNext(head.getNext());
            endNode = head.getNext().getNext();
            endNode.setNext(null);
        }
    }

    // merges two lists together into one sorted list
    public void merge(List<T> otherList) {
        if (otherList != null) {
            // create a new LinkedList by typecasting the list passed in
            LinkedList<T> other = (LinkedList<T>) otherList;

            sort();
            other.sort();

            // flags to check which list has been iterated through first
            boolean firstListFinished = false;
            boolean otherListFinished = false;

            // need two seperate pointers for the two lists
            Node<T> firstListPointer = head.getNext();
            Node<T> otherListPointer = other.head.getNext();
            // need to keep track of the element that was added last
            Node<T> lastAddedElement = null;
            // create a new linked list that we will merge both lists into
            LinkedList<T> newList = new LinkedList<T>();
            Node<T> currentNode = newList.head;


            for (int i = 0; i < (size() + other.size() - 1); i++) {
                if (firstListFinished) {// if the original list finishes, just need to point the current node at the rest of the other list
                    // effectively appending it on, completing the merged list
                    currentNode.setNext(otherListPointer);
                    head.setNext(newList.head.getNext());
                    endIndex = size() + other.size() - 1;
                    return;
                }
                if (otherListFinished) {// same logic if the list being merged finishes first
                    lastAddedElement.setNext(firstListPointer);
                    head.setNext(newList.head.getNext());
                    endIndex = size() + other.size() - 1;
                    return;
                }

                // otherwise compare the data from the pointers in both lists, and add lesser node onto the merged list
                if (firstListPointer.getData().compareTo(otherListPointer.getData()) <= 0) {// original list has a smaller element
                    newList.add(firstListPointer.getData());
                    currentNode = currentNode.getNext();
                    // need to check if getNext() is null otherwise there would be a null pointer error
                    if (firstListPointer.getNext() != null) {
                        firstListPointer = firstListPointer.getNext();
                    } else {// if it is null the list is finished
                        firstListFinished = true;
                    }
                    //update last element added to the Node that was addded
                    lastAddedElement = firstListPointer;
                } else { // samer logic for if the other list has a smaller element
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
            // updates the ending index of the list
            endIndex = size() + other.size() - 1;
            //sets the newly created merged list to the list variable
            head.setNext(newList.head.getNext());
        }

        isSorted = true;
        endNode = getEndNode();
    }

    // swaps every two elements in the list up to the end of the list
    // if list size is odd it doesn't affect the last element
    public void pairSwap() {
        if (size() > 1) {

            Node<T> pointer = head.getNext();
            Node<T> trailer = head;

            // set the trailing node to point to what the pointer is pointing at
            // set the pointer to point at two elements ahead in the list
            // the element originally in front of pointer to point at pointer
            // effectively swaps elements
            while (pointer.getNext() != null) { // loops while the pointer has not reached the end of the list
                trailer.setNext(pointer.getNext());
                pointer.setNext(pointer.getNext().getNext());
                trailer.getNext().setNext(pointer);
                trailer = pointer;
                //avoids null pointer issues
                if (pointer.getNext() != null) {
                    pointer = pointer.getNext();
                }
            }

        }
        isSorted = checkIfSorted();
        endNode = getEndNode();
    }

    // traverses the list to find the last node and returns it
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

    // returns true if the list is sorted and false if not
    public boolean checkIfSorted() {
        Node<T> pointer = head;
        //checks if the node  above the pointer is larger than the pointer
        // if not returns false, otherwise moves the pointer up the list
        while (pointer.getNext() != null) {
            pointer = pointer.getNext();
            if (pointer.getNext() != null && pointer.getData().compareTo(pointer.getNext().getData()) > 0) {
                return false;
            }
        }
        return true;
    }

}


