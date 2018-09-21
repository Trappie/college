import java.util.Random;

/**
 * Queue implemented with circular array
 * @param <T> generic type
 * @author Di Wu
 */
public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable {

    // the underlying array to hold elements for the queue
    private T[] array;
    // the physical size of the queue, which equals the length of the array
    private int capacity;

    // the following 4 fields have same initial value of 0
    // the logical size of the queue, changed along with the offer and poll
    private int size;
    // the index of the logical head of the queue, the first item in the queue should be array[head]
    private int head;
    // the index of the logical tail of the queue, the last item in the queue should be array[tail-1]
    private int tail;
    // count the moves used in offer() and poll(), should be o(N), resizing and copying not counted
    private int moves;

    @SuppressWarnings("unchecked")
    public RandIndexQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("the initial capacity of the queue should be at least 1");
        } else {
            this.capacity = initialCapacity;
            this.array = (T[]) (new Object[capacity]);
        }
    }

    @Override
    public T get(int i) {
        if (isIndexValid(i)) {
            return array[getTrueIndex(i)];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void set(int i, T item) {
        if (isIndexValid(i)) {
            array[getTrueIndex(i)] = item;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean offer(T item) {
        // check size first, if full, create new array with doubled size and rearrange everything
        moves++;
        checkSize();
        // tail should be in the next position where new item can be offered
        array[tail] = item;
        // tail point to the next position, if the position is out of the boundary of the array, tail point to 0
        if(++tail == capacity) {
            tail = 0;
        }
        size++;

        return true;
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        } else {
            moves++;
            T tmp = array[head];
            if(++head == capacity) {
                head = 0;
            }
            size--;
            return tmp;
        }
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return array[head];
        }
    }

    @Override
    public boolean isFull() {
        return false;
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
    public void clear() {
        size = 0;
        head = 0;
        tail = 0;
    }

    @Override
    public int getMoves() {
        return moves;
    }

    @Override
    public void setMoves(int moves) {
        this.moves = moves;
    }

    @Override
    public void shuffle() {
        // Fisher–Yates shuffle learned from wikipedia.
        Random random = new Random();
        int struck;
        for(int i=size; i>0; i--) {
            struck = random.nextInt(i);
            swapLogically(struck, i-1);
        }
    }

    /**
     * swap two items with given logical index
     * @param i logic index of the first item to swap
     * @param j logic index of the second item to swap
     */
    private void swapLogically(int i, int j) {
        T tmp = get(i);
        set(i, get(j));
        set(j, tmp);
    }

    /**
     * check whether a logical index is valid
     * @param logicalIndex the logical index to be checked
     * @return if the index is logically valid([0,size))
     */
    private boolean isIndexValid(int logicalIndex) {
        return logicalIndex >= 0 && logicalIndex < size;
    }

    /**
     * return the physical index for a given logical index
     * @param logicalIndex the given logical index, this parameter should always be in [0, size)
     * @return the physical index to find the element in the array. if the logical index is out of boundary, retrun -1
     */
    private int getTrueIndex(int logicalIndex) {
        if (isIndexValid(logicalIndex)) {
            int tmpIndex = head + logicalIndex;
            return tmpIndex >= capacity ? (tmpIndex - capacity) : tmpIndex;
        } else {
            return -1;
        }
    }


    /**
     * called before offer() to check whether there is enough room for the new item
     * if the array is full, create a new one with doubled length and copy everything
     * adjust head, tail, and capacity to make sure logic of the queue is maintained.
     */
    @SuppressWarnings("unchecked")
    private void checkSize() {
        if (size == capacity) {
            // create new array with doubled size and array copy and set new size/capacity/head/tail
            T[] tmp = (T[]) (new Object[capacity * 2]);
            if (tail > head) {
                System.arraycopy(array, head, tmp, 0, size);
            } else {
                System.arraycopy(array, head, tmp, 0, capacity - head);
                System.arraycopy(array, 0, tmp, capacity - head, tail);
            }
            array = tmp;
            head = 0;
            tail = size;
            capacity = array.length;
        }
    }

    @Override
    public String toString() {
        String result = "Content: ";
        for (int i=0; i<size; i++) {
            result += get(i).toString() + " ";
        }
        return result;
    }
}
