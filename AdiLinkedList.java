public class AdiLinkedList<E>
{
    private static class AdiLinkedListNode<E> {
        public AdiLinkedListNode<E> prev;
        public AdiLinkedListNode<E> next;
        public E val;
        public AdiLinkedListNode(AdiLinkedListNode<E> prev, AdiLinkedListNode<E> next, E val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }
    private AdiLinkedListNode<E> head;
    private AdiLinkedListNode<E> tail;
    private int size;
    
    public AdiLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public int getSize() {
        return size;
    }
    
    public E getHead() {
        return head.val;
    }
    
    public E getTail() {
        return tail.val;
    }
    
    public E getAtIndex(int index) {
        AdiLinkedListNode<E> toBeReturned = head;
        for (int i=0; i<index; i++) {
            toBeReturned = toBeReturned.next;
        }
        return toBeReturned.val;
    }
    
    public void insertHead(E val) {
        AdiLinkedListNode<E> toBeInserted = new AdiLinkedListNode<E>(null,head,val);
        if (head != null) {
            head.prev = toBeInserted;
        }
        head = toBeInserted;
        if (tail == null) {
            tail = toBeInserted;
        }
        size++;
    }
    
    public void insertTail(E val) {
        AdiLinkedListNode<E> toBeInserted = new AdiLinkedListNode<E>(tail,null,val);
        if (tail != null) {
            tail.next = toBeInserted;
        }
        tail = toBeInserted;
        if (head == null) {
            head = toBeInserted;
        }
        size++;
    }
    
    public void insertAtIndex(int index, E val) {
        if (index == 0) {
            insertHead(val);
            return;
        }
        if (index == size) {
            insertTail(val);
            return;
        }
        AdiLinkedListNode<E> nextAfterInsert = head;
        for (int i=0; i<index; i++) {
            nextAfterInsert = nextAfterInsert.next;
        }
        AdiLinkedListNode<E> toBeInserted = new AdiLinkedListNode<E>(nextAfterInsert.prev,nextAfterInsert,val);
        nextAfterInsert.prev.next = toBeInserted;
        nextAfterInsert.prev = toBeInserted;
        size++;
    }
    
    public void removeHead() {
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
    }
    
    public void removeTail() {
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        size--;
    }
    
    public void removeAtIndex(int index) {
        if (index == 0) {
            removeHead();
            return;
        }
        if (index == size-1) {
            removeTail();
            return;
        }
        AdiLinkedListNode<E> toBeRemoved = head;
        for (int i=0; i<index; i++) {
            toBeRemoved = toBeRemoved.next;
        }
        toBeRemoved.prev.next = toBeRemoved.next;
        toBeRemoved.next.prev = toBeRemoved.prev;
        size--;
    }

}
