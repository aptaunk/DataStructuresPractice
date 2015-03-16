public class AdiBinaryHeap<E extends Comparable<E>>
{
    private static class AdiBinaryHeapNode<E> {
        public E e;
        public int nodeIndex;
        public AdiBinaryHeapNode(int index, E e) {
            this.nodeIndex = index;
            this.e = e;
        }
    }
    private boolean minHeap;
    private AdiDynamicArray<AdiBinaryHeapNode<E>> heapArray;
    
    public AdiBinaryHeap() {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<E>>();
        minHeap = true;
    }
    
    public AdiBinaryHeap(int initCap) {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<E>>(initCap);
        minHeap = true;
    }
    
    public AdiBinaryHeap(int initCap, boolean isMinHeap) {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<E>>(initCap);
        minHeap = isMinHeap;
    }
    
    public int getSize() {
        return heapArray.getSize();
    }
    
    private AdiBinaryHeapNode<E> getLeft(AdiBinaryHeapNode<E> p) {
        return 2*p.nodeIndex+1>=heapArray.getSize()?null:heapArray.get(2*p.nodeIndex+1);
    }
    
    private AdiBinaryHeapNode<E> getRight(AdiBinaryHeapNode<E> p) {
        return 2*p.nodeIndex+2>=heapArray.getSize()?null:heapArray.get(2*p.nodeIndex+2);
    }
    
    private AdiBinaryHeapNode<E> getParent(AdiBinaryHeapNode<E> n) {
        if (n == heapArray.get(0)) {
            return null;
        }
        return n.nodeIndex%2==0?heapArray.get((n.nodeIndex-2)/2):heapArray.get((n.nodeIndex-1)/2);
    }
    
    private void switchNodes(AdiBinaryHeapNode<E> a, AdiBinaryHeapNode<E> b) {
        heapArray.set(a.nodeIndex,b);
        heapArray.set(b.nodeIndex,a);
    
        int c_nodeIndex = a.nodeIndex;
        a.nodeIndex = b.nodeIndex;
        b.nodeIndex = c_nodeIndex;
    }
    
    private int compareNodes(AdiBinaryHeapNode<E> a, AdiBinaryHeapNode<E> b) {
        return minHeap?a.e.compareTo(b.e):b.e.compareTo(a.e);
    }
    
    public E get() {
        return heapArray.get(0).e;
    }
    
    public void insert(E e) {
        if (heapArray.getSize() == 0) {
            heapArray.insert(new AdiBinaryHeapNode<E>(0,e));
            return;
        }
        AdiBinaryHeapNode<E> toBeInserted = new AdiBinaryHeapNode<E>(heapArray.getSize(),e);
        heapArray.insert(toBeInserted);
        while (getParent(toBeInserted) != null && compareNodes(toBeInserted,getParent(toBeInserted))<0) {
             switchNodes(toBeInserted,getParent(toBeInserted));
        }
    }
    
    public void remove() {
        if (heapArray.getSize() == 1) {
            heapArray.remove();
            return;
        }
        switchNodes(heapArray.get(0),heapArray.get(heapArray.getSize()-1));
        heapArray.remove();
        AdiBinaryHeapNode<E> trickleDown = heapArray.get(0); 
        while(true) {
            if (getLeft(trickleDown) == null && getRight(trickleDown) == null) {
                return;
            }
            if (getLeft(trickleDown) == null && getRight(trickleDown) != null) {
                if (compareNodes(trickleDown,getRight(trickleDown))>0) {
                    switchNodes(trickleDown,getRight(trickleDown));
                }
                return;
            } 
            if (getLeft(trickleDown) != null && getRight(trickleDown) == null) {
                if (compareNodes(trickleDown,getLeft(trickleDown))>0) {
                    switchNodes(trickleDown,getLeft(trickleDown));
                }
                return;
            }
            if (compareNodes(getLeft(trickleDown),getRight(trickleDown))<0) {
                if (compareNodes(trickleDown,getLeft(trickleDown))>0) {
                    switchNodes(trickleDown,getLeft(trickleDown));
                } else {
                    return;
                }
            } else {
                if (compareNodes(trickleDown,getRight(trickleDown))>0) {
                    switchNodes(trickleDown,getRight(trickleDown));
                } else {
                    return;
                }
            }
        }
    }
}
