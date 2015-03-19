import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdiHashTable<K,V>
{
    private static class AdiHashTableNode<K,V> {
        public K key;
        public V val;
        public AdiHashTableNode(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
    private AdiDynamicArray<AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>> hashTable;
    private int size;
    
    public AdiHashTable() {
        this(10);
    }
    
    public AdiHashTable(int initCap) {
        size = 0;
        hashTable = new AdiDynamicArray<AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>>(initCap+1);
        for (int i=0; i<initCap; i++) {
            hashTable.insert(new AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>());
        }
    }
    
    public int getSize() {
        return size;
    }
    
    private void expand() {
        AdiDynamicArray<AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>> appendThisToHashTable 
            = new AdiDynamicArray<AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>>(hashTable.getSize()+1);
        for (AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>> t : hashTable) {
            appendThisToHashTable.insert(new AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>>());
            for (Integer k : t) {
                if (k%hashTable.getSize() != k%(2*hashTable.getSize())) {
                    appendThisToHashTable.get(appendThisToHashTable.getSize()-1).insert(k,t.get(k));
                    t.remove(k);
                }
            }
        }
        for (AdiBinaryTree<Integer,AdiLinkedList<AdiHashTableNode<K,V>>> t : appendThisToHashTable) {
            hashTable.insert(t);
        }
    }
    
    public boolean contains(K key) {
        if (hashTable.get(key.hashCode()%hashTable.getSize()).contains(key.hashCode())) {
            for (AdiHashTableNode<K,V> n : hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode())) {
                if (n.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public V get(K key) {
        AdiLinkedList<AdiHashTableNode<K,V>> toBeReturned = hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode());
        for (AdiHashTableNode<K,V> n : toBeReturned) {
            if (n.key.equals(key)) {
                return n.val;
            }
        }
        throw new NoSuchElementException();
    }
    
    public void set(K key, V val) {
        AdiLinkedList<AdiHashTableNode<K,V>> toBeSet = hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode());
        for (AdiHashTableNode<K,V> n : toBeSet) {
            if (n.key.equals(key)) {
                n.val = val;
                return;
            }
        }
        throw new NoSuchElementException();
    }
    
    public void insert(final K key, final V val) {
        size++;
        if (size == hashTable.getSize()) {
            expand();
        }
        if (hashTable.get(key.hashCode()%hashTable.getSize()).contains(key.hashCode())) {
            for (AdiHashTableNode<K,V> n : hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode())) {
                if (n.key.equals(key)) {
                    n.val = val;
                    return;
                }
            }
            hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode()).insertTail(new AdiHashTableNode<K,V>(key,val));
        } else {
            hashTable.get(key.hashCode()%hashTable.getSize()).insert(key.hashCode(),new AdiLinkedList<AdiHashTableNode<K,V>>(){{insertTail(new AdiHashTableNode<K,V>(key,val));}});
        }
    }
    
    public void remove(K key) {
        size--;
        AdiLinkedList<AdiHashTableNode<K,V>> toBeRemovedList = hashTable.get(key.hashCode()%hashTable.getSize()).get(key.hashCode());
        Iterator<AdiHashTableNode<K,V>> iter = toBeRemovedList.iterator();
        AdiHashTableNode<K,V> toBeRemoved = null;
        while (iter.hasNext()) {
            toBeRemoved = iter.next();
            if (toBeRemoved.key.equals(key)) {
                iter.remove();
                if (toBeRemovedList.getSize() == 0) {   
                    hashTable.get(key.hashCode()%hashTable.getSize()).remove(key.hashCode());
                }
                return;
            }
        }
        throw new NoSuchElementException();
    }
}
