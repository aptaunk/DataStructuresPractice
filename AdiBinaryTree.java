import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdiBinaryTree<K extends Comparable<K>,V> implements Iterable<K>
{
    private static class AdiBinaryTreeNode<K,V> {
        public AdiBinaryTreeNode<K,V> left;
        public AdiBinaryTreeNode<K,V> right;
        public AdiBinaryTreeNode<K,V> parent;
        public K key;
        public V val;
        public AdiBinaryTreeNode(AdiBinaryTreeNode<K,V> parent, K key, V val) {
            this.parent = parent;
            this.key = key;
            this.val = val;
        }
    }
    
    private AdiBinaryTreeNode<K,V> root;
    private AdiBinaryTreeNode<K,V> lastContains;
    private Iterator<K> emptyIterator;
    
    public static final int IN_ORDER = 0;
    public static final int DEPTH_FIRST = 1;
    public static final int BREDTH_FIRST = 2;
    
    private int iteratorType;
    
    public AdiBinaryTree() {
        this.root = null;
        this.lastContains = null;
        this.emptyIterator = new Iterator<K>() {
            public boolean hasNext() {
                return false;
            }
            public K next() {
                throw new NoSuchElementException();
            }
            public void remove() {
                throw new IllegalStateException();
            }
        };
        this.iteratorType = DEPTH_FIRST;
    }
    
    public AdiBinaryTree(int iteratorType) {
        this.root = null;
        this.lastContains = null;
        this.emptyIterator = new Iterator<K>() {
            public boolean hasNext() {
                return false;
            }
            public K next() {
                throw new NoSuchElementException();
            }
            public void remove() {
                throw new IllegalStateException();
            }
        };
        this.iteratorType = iteratorType;
    }
    
    private AdiBinaryTreeNode<K,V> search(K key) {
        if (root == null) {
            return null;
        }
        AdiBinaryTreeNode<K,V> n = root;
        while (true) {
            if (n == null) {
                return null;
            } else if (key.compareTo(n.key) < 0) {
                n = n.left;
            } else if (key.compareTo(n.key) > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
    }
    
    public boolean contains(K key) {
        lastContains = search(key);
        return lastContains != null;
    }
    
    public V get(K key) {
        if (lastContains != null && lastContains.key.equals(key)) {
            return lastContains.val;
        }
        return search(key).val;
    }
    
    public void insert(K key, V val) {
        if (root == null) {
            root = new AdiBinaryTreeNode<K,V>(null,key,val);
            return;
        }
        AdiBinaryTreeNode<K,V> n = root;
        while (true) {
            if (key.compareTo(n.key) < 0) {
                if (n.left == null) {
                    n.left = new AdiBinaryTreeNode<K,V>(n,key,val);
                    return;
                } else {
                    n = n.left;
                }
            } else if (key.compareTo(n.key) > 0) {
                if (n.right == null) {
                    n.right = new AdiBinaryTreeNode<K,V>(n,key,val);
                    return;
                } else {
                    n = n.right;
                }
            } else {
                n.key = key;
                n.val = val;
            }
        }
    }
    
    private AdiBinaryTreeNode<K,V> maxNode(AdiBinaryTreeNode<K,V> subTreeRoot) {
        AdiBinaryTreeNode<K,V> n = subTreeRoot;
        while(n.right != null) {
            n = n.right;
        }
        return n;
    }
    
    private void removeRoot() {
        if (root.left == null && root.right == null) {
            root = null;
        } else if (root.left == null && root.right != null) {
            root = root.right;
            root.parent = null;
        } else if (root.left != null && root.right == null) {
            root = root.left;
            root.parent = null;
        } else {
            AdiBinaryTreeNode<K,V> maxNodeOfLeftTree = maxNode(root.left);
            maxNodeOfLeftTree.right = root.right;
            root.right.parent = maxNodeOfLeftTree;
            root = root.left;
            root.parent = null;
        }
    }
    
    private void remove(AdiBinaryTreeNode<K,V> n) {
        if (n.equals(root)) {
            removeRoot();
            return;
        }
        AdiBinaryTreeNode<K,V> p = n.parent;
        if (p.left.equals(n)) {
            if (n.left == null && n.right == null) {
                p.left = null;
            } else if (n.left == null && n.right != null) {
                n = n.right;
                n.parent = p;
                p.left = n;
            } else if (n.left != null && n.right == null) {
                n = n.left;
                n.parent = p;
                p.left = n;
            } else {
                AdiBinaryTreeNode<K,V> maxNodeOfLeftTree = maxNode(n.left);
                maxNodeOfLeftTree.right = n.right;
                n.right.parent = maxNodeOfLeftTree;
                n = n.left;
                n.parent = p;
                p.left = n;
            }
        } else {
            if (n.left == null && n.right == null) {
                p.right = null;
            } else if (n.left == null && n.right != null) {
                n = n.right;
                n.parent = p;
                p.right = n;
            } else if (n.left != null && n.right == null) {
                n = n.left;
                n.parent = p;
                p.right = n;
            } else {
                AdiBinaryTreeNode<K,V> maxNodeOfLeftTree = maxNode(n.left);
                maxNodeOfLeftTree.right = n.right;
                n.right.parent = maxNodeOfLeftTree;
                n = n.left;
                n.parent = p;
                p.right = n;
            }
        }
    }
    
    public void remove(K key) {
        if (lastContains != null && lastContains.key.equals(key)) {
            remove(lastContains);
            lastContains = null;
            return;
        }
        remove(search(key));
    }
    
    public Iterator<K> depthFirstIterator() {
        if (root == null) {
            return emptyIterator;
        }
        return new Iterator<K>() {
            private AdiDynamicArray<AdiBinaryTreeNode<K,V>> bstIteratorStack;
            private AdiBinaryTreeNode<K,V> nextNode;
            private K currKey;
            {
                bstIteratorStack = new AdiDynamicArray<AdiBinaryTreeNode<K,V>>();
                nextNode = root;
                currKey = null;
            }
            public boolean hasNext() {
                return nextNode != null;
            }
            public K next() {
                currKey = nextNode.key;
                if (nextNode.right != null) {
                    bstIteratorStack.insert(nextNode.right);
                }
                if (nextNode.left != null) {
                    nextNode = nextNode.left;
                } else {
                    nextNode = bstIteratorStack.getSize()==0?null:bstIteratorStack.get(bstIteratorStack.getSize()-1);
                    if (bstIteratorStack.getSize()>0) {
                        bstIteratorStack.remove();
                    }
                }
                return currKey;
            }
            public void remove() {
                if (currKey == null) {
                    throw new IllegalStateException();
                }
                AdiBinaryTree.this.remove(currKey);
                currKey = null;
            }
        };
    }
    
    public Iterator<K> bredthFirstIterator() {
        if (root == null) {
            return emptyIterator;
        }
        return new Iterator<K>() {
            private AdiDynamicArray<AdiBinaryTreeNode<K,V>> currentLevel;
            private AdiDynamicArray<AdiBinaryTreeNode<K,V>> nextLevel;
            private AdiBinaryTreeNode<K,V> nextNode;
            private K currKey;
            {
                currentLevel = new AdiDynamicArray<AdiBinaryTreeNode<K,V>>();
                nextLevel = new AdiDynamicArray<AdiBinaryTreeNode<K,V>>();
                nextNode = root;
                currKey = null;
            }
            public boolean hasNext() {
                return nextNode != null;
            }
            public K next() {
                currKey = nextNode.key;
                if (nextNode.left != null) {
                    nextLevel.insert(nextNode.left);
                }
                if (nextNode.right != null) {
                    nextLevel.insert(nextNode.right);
                } 
                if (currentLevel.getSize()==0) {
                    currentLevel = nextLevel;
                    nextLevel = new AdiDynamicArray<AdiBinaryTreeNode<K,V>>(); 
                }
                nextNode = currentLevel.getSize()==0?null:currentLevel.get(currentLevel.getSize()-1);
                if (currentLevel.getSize()>0) {
                    currentLevel.remove();
                }
                return currKey;
            }
            public void remove() {
                if (currKey == null) {
                    throw new IllegalStateException();
                }
                AdiBinaryTree.this.remove(currKey);
                currKey = null;
            }
        };
    }
    
    public Iterator<K> inOrderIterator() {
        if (root == null) {
            return emptyIterator;
        }
        return new Iterator<K>() {
            private AdiDynamicArray<AdiBinaryTreeNode<K,V>> bstIteratorStack;
            private AdiBinaryTreeNode<K,V> nextNode;
            private K currKey;
            {
                bstIteratorStack = new AdiDynamicArray<AdiBinaryTreeNode<K,V>>();
                nextNode = root;
                while (nextNode.left != null) {
                    bstIteratorStack.insert(nextNode);
                    nextNode = nextNode.left;
                }
                currKey = null;
            }
            public boolean hasNext() {
                return nextNode != null;
            }
            public K next() {
                currKey = nextNode.key;
                if (nextNode.right != null) {
                    nextNode = nextNode.right;
                    while (nextNode.left != null) {
                        bstIteratorStack.insert(nextNode);
                        nextNode = nextNode.left;
                    }
                } else {
                    nextNode = bstIteratorStack.getSize()==0?null:bstIteratorStack.get(bstIteratorStack.getSize()-1);
                    if (bstIteratorStack.getSize()>0) {
                        bstIteratorStack.remove();
                    }
                }
                return currKey;
            }
            public void remove() {
                if (currKey == null) {
                    throw new IllegalStateException();
                }
                AdiBinaryTree.this.remove(currKey);
                currKey = null;
            }
        };
    }
    
    public Iterator<K> iterator() {
        if (iteratorType == IN_ORDER) {
            return inOrderIterator();
        } else if (iteratorType == DEPTH_FIRST) {
            return depthFirstIterator();
        } else if (iteratorType == BREDTH_FIRST) {
            return bredthFirstIterator();
        } else {
            throw new NoSuchElementException();
        }
    }
}
