public class AdiBinaryHeap<K extends Comparable<K>,V>
{
    private static class AdiBinaryHeapNode<K,V> {
        public K key;
        public V val;
        public int nodeIndex;
        public AdiBinaryHeapNode(int index, K key, V val) {
            this.nodeIndex = index;
            this.key = key;
            this.val = val;
        }
    }
    private boolean minHeap;
    private AdiDynamicArray<AdiBinaryHeapNode<K,V>> heapArray;
    
    public AdiBinaryHeap() {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<K,V>>();
        minHeap = true;
    }
    
    public AdiBinaryHeap(int initCap) {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<K,V>>(initCap);
        minHeap = true;
    }
    
    public AdiBinaryHeap(int initCap, boolean isMinHeap) {
        heapArray = new AdiDynamicArray<AdiBinaryHeapNode<K,V>>(initCap);
        minHeap = isMinHeap;
    }
    
    private AdiBinaryHeapNode<K,V> getLeft(AdiBinaryHeapNode<K,V> p) {
        return heapArray.get(2*p.nodeIndex+2);
    }
    
    private AdiBinaryHeapNode<K,V> getRight(AdiBinaryHeapNode<K,V> p) {
        return heapArray.get(2*p.nodeIndex+3);
    }
    
    private AdiBinaryHeapNode<K,V> getParent(AdiBinaryHeapNode<K,V> n) {
        return n.nodeIndex%2==0?heapArray.get((n.nodeIndex-2)/2):heapArray.get((n.nodeIndex-3)/2);
    }
    
    private void switchNodes(AdiBinaryHeapNode<K,V> a, AdiBinaryHeapNode<K,V> b) {
        AdiBinaryHeapNode<K,V> c = heapArray.get(a.nodeIndex);
        heapArray.set(a.nodeIndex,b);
        heapArray.set(b.nodeIndex,c);
    
        int c_nodeIndex = a.nodeIndex;
        a.nodeIndex = b.nodeIndex;
        b.nodeIndex = c.nodeIndex;
    }
    
    private int compareNodes(AdiBinaryHeapNode<K,V> a, AdiBinaryHeapNode<K,V> b) {
        return minHeap?a.key.compareTo(b.key):b.key.compareTo(a.key);
    }
    
    public V getHead() {
        return heapArray.get(0).val;
    }
    
    public void insert(K key, V val) {
        AdiBinaryHeapNode<K,V> toBeInserted = new AdiBinaryHeapNode<K,V>(heapArray.getSize(),key,val);
        heapArray.insert(toBeInserted);
        while (compareNodes(toBeInserted,getParent(toBeInserted))<0) {
             switchNodes(toBeInserted,getParent(toBeInserted));
        }
    }
}
