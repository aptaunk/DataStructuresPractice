public class AdiBinaryTree<K extends Comparable<K>,V>
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
    
    public AdiBinaryTree() {
        root = null;
        lastContains = null;
    }
    
    private AdiBinaryTreeNode<K,V> search(K key) {
        if (root == null) {
            return null;
        }
        AdiBinaryTreeNode<K,V> n = root;
        while (true) {
            if (n == null) {
                return null;
            } else if (n.key.compareTo(key) < 0) {
                n = n.left;
            } else if (n.key.compareTo(key) > 0) {
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
            if (n.key.compareTo(key) < 0) {
                if (n.left == null) {
                    n.left = new AdiBinaryTreeNode<K,V>(n,key,val);
                    return;
                } else {
                    n = n.left;
                }
            } else if (n.key.compareTo(key) > 0) {
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
}
