package SkipList;

/*
 * Bless!
 * :)
 */

import java.util.*;

public class SkipListSet<T extends Comparable<T>> implements SortedSet<T> {

    /* 
     * sets probability at .5 (coin flip)
     * lowest level to 16
     * max level is 16 initially, (will update as needed)
     * don't change prob and lowest level
     */

    private static final double PROB = 0.5;
    private static final int LOWEST_LEVEL = 16;
    private int MAXEST_LEVEL = 16;

    private Node<T> head;

    private int size;

    private Random random;

    // initialize a blank Skip List
    // head contains null members of maxest level
    public SkipListSet() {

        head = new Node<>(null, MAXEST_LEVEL);
        // number of nodes, excluding null
        size = 0;
        // level randomizor
        random = new Random();

    }

    // initialize a blank node
    // uses an array to store
    private static class Node<T> {

        T key;

        /*
         * ONLY USE A NEXT POINTER
         * KEEPING TRACK OF ALL EXTRA POINTERS IS TEDIOUS
         * THE ARRAY KEEPS TRACK OF UP AND DOWN
         * CREATE AN EXTRA ARRAY FOR REMOVE METHOD ONLY TO TRACK PREVIOUS
         */

        Node<T>[] next;

        Node(T key, int level) {

            this.key = key;
            next = new Node[level];

        }

    }

    // iterates over the bottom layer of the list
    private class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T> {

        private Node<T> curr;

        private Node<T> prev;

        SkipListSetIterator(Node<T> head) {
            curr = head.next[0];
        }

        // checks next node
        public boolean hasNext() {
            return curr != null;
        }

        // sets next node if not null
        public T next() {

            if (curr == null) {

                System.out.println("NEXT DOESN'T EXIST");

            }

            T key = curr.key;
            curr = curr.next[0];
            return key;

        }

        // same logic as REMOVE
        public void remove() {

            if (prev == null) {

                System.out.println("PREV DOESN'T EXIST");

            }

            for (int i = prev.next.length - 1; i >= 0; i--) {

                if (prev.next[i] == curr) {

                    prev.next[i] = curr.next[i];

                }
            }

            size--; 
            prev = null;
        }

    }

    // calculates log 2
    private static int log2(int n) {

        return (int) (Math.log(n) / Math.log(2));

    }

    /* 
     * gets called to change the max height of the list
     * GENERATE HEIGHT BY 2LOG2 and copy values
     * 
     */
    private void adjustHeight() {

        int newSize = size();
        // adjust the height logarithmically
        int newMaxLevel = Math.max(2 * log2(newSize), LOWEST_LEVEL);

        // adjust head's height if necessary
        if (newMaxLevel > head.next.length) {

            Node<T>[] copy = new Node[newMaxLevel];
            System.arraycopy(head.next, 0, copy, 0, head.next.length);
            head.next = copy;

        }

        MAXEST_LEVEL = newMaxLevel;

    }

    // checks for amount of nodes
    public int size() {
        return size;
    }

    // if skip list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     *    IMPLEMENTATION LOGIC
     * 1. try/catch casting exceptions for object
     * 2. initialize a temp head
     * 3. iterate the list from head
     * 4. return at any point key.compareTo == 0 true
     * 5. if not than false
     */

    public boolean contains(Object o) {

        try {
            // type cast object to type T
            // suppress the warnings
            @SuppressWarnings("unchecked")
            T key = (T) o;

            Node<T> temp = head;

            for (int i = MAXEST_LEVEL - 1; i >= 0; i--) {

                while (temp.next[i] != null && temp.next[i].key.compareTo(key) < 0) {

                    temp = temp.next[i];

                }
            }

            temp = temp.next[0];
            return temp != null && temp.key.compareTo(key) == 0;

        } catch (Exception e) {
            System.out.println(e);
        }

        return false;

    }

    /*
     *    IMPLEMENTATION LOGIC
     * 1. initialize temp array to keep track
     * 2. find insertion node
     * 3. duplicate check
     * 4. generate random height
     * 5. initialize a new node with key and random height
     * 6. updates the points to the next position
     * 7. call height adjustment
     */

    public boolean add(T value) {

        Node<T>[] temp = new Node[MAXEST_LEVEL];
        Node<T> node = head;

        for (int i = MAXEST_LEVEL - 1; i >= 0; i--) {

            while (node.next[i] != null && node.next[i].key.compareTo(value) < 0) {

                node = node.next[i];

            }

            temp[i] = node;
        }

        node = node.next[0];

        // dup check
        if (node != null && node.key.compareTo(value) == 0) {
            return false;
        }

        // calculates a random height
        int randLevel = randomLevel();

        node = new Node<>(value, randLevel);

        // pointer updatation
        for (int i = 0; i < randLevel; i++) {

            node.next[i] = temp[i].next[i];
            temp[i].next[i] = node;

        }

        // adds 1 to size of lsit
        size++;

        // checks to see if needed to adjust max level
        // could also be changed so we don't call every time
        adjustHeight();

        return true;
    }

    /*
     *    IMPLEMENTATION LOGIC
     * 1. try/catch casting exceptions
     * 2. initialize a removal array to keep track of
     *    predessecor for pointer adjustment
     * 3. initialize a temp head
     * 4. iterate the list from head
     * 5. stop at any point if key.compareTo == 0
     * 6. update pointer to this node to garbage collect it
     * 7. make sure to connect the previous node to nodes next
     * 8. call the height adjustment method
     */

    public boolean remove(Object o) {

        // type cast object to type T
        // suppress the warnings
        try {

            @SuppressWarnings("unchecked")
            T value = (T) o;

            Node<T>[] removal = new Node[MAXEST_LEVEL];
            Node<T> temp = head;

            for (int i = MAXEST_LEVEL - 1; i >= 0; i--) {

                while (temp.next[i] != null && temp.next[i].key.compareTo(value) < 0) {

                    temp = temp.next[i];

                }

                removal[i] = temp;

            }

            temp = temp.next[0];

            if (temp != null && temp.key.compareTo(value) == 0) {

                for (int i = 0; i < temp.next.length; i++) {

                    removal[i].next[i] = temp.next[i];

                }

                size--;

                // checks to see if needed to adjust max level
                // could also be changed so we don't call every time
                adjustHeight();

                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        // null
        return false;

    }

    // randomizes the height of Skip List
    // USE PROB FOR 50%
    private int randomLevel() {

        int level = 1;

        while (level < MAXEST_LEVEL && random.nextDouble() < PROB) {

            level++;

        }

        return level;

    }

    // IMPLEMENT ONCE
    public Iterator<T> iterator() {

        return new SkipListSetIterator<>(head);

    }

    /*
     * default toArray method
     * CREATE ARRAY, ADD KEYS
     * use for(key : this) since its called OBJECT.toArray()
     */
    public Object[] toArray() {

        Object[] array = new Object[size];

        int i = 0;

        for (T key : this) {

            array[i++] = key;

        }

        return array;
    }

    public <T> T[] toArray(T[] a) {

        throw new UnsupportedOperationException("Unimplemented method 'toArray'");

    }

    /*
     * default containsAll method
     * use for(OBJECT : COLLECTION) since its called OBJECT.containsALL()
     */
    public boolean containsAll(Collection<?> c) {

        for (Object o : c) {

            if (!contains(o)) {

                return false;

            }
        }

        return true;

    }

    // return true if set1 is equal to called set2
    // we do this by comparing each member of set1 and set2
    public boolean equals(Object set2) {

        // null check
        if (set2 == null) {

            return false;

        }

        // exact equality
        if (set2 == this) {
            return true;
        }
    
        // not an instance of SET
        if (!(set2 instanceof Set)) {

            return false;

        }
    
        Set<?> set1 = (Set<?>) set2;
    
        if (set1.size() != size()) {

            return false;

        }
    
        for (T key : this) {

            if (!set1.contains(key)) {

                return false;

            }
        }
    
        return true;

    }

    // add up hash codes. multiply by 31; common practice.
    public int hashCode() {

        int hash = 1;

        for (T key : this) {

            int hashCode = Objects.hashCode(key); 
            hash = 31 * hash + hashCode;

        }

        return hash;

    }

    /*
     * default addAll function
     * use for(KEY : COLLECTION) since its called COLLECTION.addALL()
     * check for correct addition
     */
    public boolean addAll(Collection<? extends T> c) {

        boolean yes = false;

        for (T key : c) {

            if (add(key)) {

                yes = true;

            }
        }

        return yes;

    }

    /*
     * default retainAll method
     * uses iterator remove
     * calls set1.retainAll(set2)
     * deletes everything in set1 NOT in set2
     */
    public boolean retainAll(Collection<?> c) {

        boolean yes = false;
        Iterator<T> it = iterator();

        while (it.hasNext()) {

            T key = it.next();
            if (!c.contains(key)) {

                it.remove();
                yes = true;

            }
        }

        return yes;

    }

    /*
     * default removeAll method
     * uses remove(object)
     * USE for(OBJECT : COLLECTION) because call is COLLECTION.removeALL()
     * deletes everything COLLECTION
     */
    public boolean removeAll(Collection<?> c) {

        boolean yes = false;
        for (Object o : c) {

            if (remove(o)) {

                yes = true;

            }
        }

        return yes;

    }

    // CLEARS LIST
    // bless JAVA for the garbage man
    public void clear() {

        head = new Node<>(null, MAXEST_LEVEL);
        size = 0;

    }

    // yes :)
    public Comparator<? super T> comparator() {
        return null;
    }

    /*
     * throw new UnsupportedOperationException
     */

    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException("Unimplemented method 'subSet'");
    }

    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException("Unimplemented method 'headSet'");
    }

    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException("Unimplemented method 'tailSet'");
    }

    // default first method (return head value)
    public T first() {

        return head.next[0].key;

    }

    // default last method (return tail value)
    // ITERATE THROUGH LIST SINCE HEAD ONLY
    public T last() {

        Node<T> node = head;

        for (int i = MAXEST_LEVEL - 1; i >= 0; i--) {

            while (node.next[i] != null) {

                node = node.next[i];

            }
        }

        return node.key;

    }

    /* 
     *    IMPLEMENTATION LOGIC
     *    MODEL LIKE REMOVAL LOGIC AND ADD HEIGHT INSTEAD OF DELETING
     * 1. Create new dummy list for linking list
     * 2. Make dummy the head with links
     * 3. Create a traversal node always one ahead of dummy
     * 4. Each loop create a new height and 
     *    create a new node with traversal node
     * 5. Update dummy list with links to newNode 
     * 6. Make dummy list the new node to move forward
     * 7. Next node
     */
    public void reBalance() {

        System.out.println("Balance Complete");

        Node<T>[] dummy = new Node[MAXEST_LEVEL];

        for (int i = 0; i < MAXEST_LEVEL; i++) {

            dummy[i] = head;

        }

        Node<T> node = head.next[0];

        while (node != null) {

            int random = randomLevel(); 

            Node<T> newNode = new Node<>(node.key, random);

            for (int i = 0; i < random; i++) {

                newNode.next[i] = dummy[i].next[i];

                dummy[i].next[i] = newNode;

                dummy[i] = newNode;

            }

            node = node.next[0];

        }

    }

}
