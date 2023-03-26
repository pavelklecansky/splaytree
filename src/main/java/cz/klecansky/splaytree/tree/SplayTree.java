package cz.klecansky.splaytree.tree;

import java.util.ArrayList;
import java.util.List;

public class SplayTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private TreeNode<Key, Value> root;   // root of the BST

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public Key getRootKey() {
        if (root == null) {
            return null;
        }
        return root.key;
    }

    @Override
    public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return null;
    }

    @Override
    public List<Key> keys() {
        List<Key> keys = new ArrayList<>();
        inOrderTraversalSaveAllKeys(root, keys);
        return keys;
    }

    @Override
    public void put(Key key, Value value) {
        // splay key to root
        if (root == null) {
            root = new TreeNode<>(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            TreeNode<Key, Value> n = new TreeNode<>(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        // Insert new node at root
        else if (cmp > 0) {
            TreeNode<Key, Value> n = new TreeNode<>(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        // It was a duplicate key. Simply replace the value
        else {
            root.value = value;
        }

    }

    /* This splays the key, then does a slightly modified Hibbard deletion on
     * the root (if it is the node to be deleted; if it is not, the key was
     * not in the tree). The modification is that rather than swapping the
     * root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right
     * child.
     */
    @Override
    public void remove(Key key) {
        if (root == null) return; // empty tree

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                TreeNode<Key, Value> x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }

        // else: it wasn't in the tree to remove
    }


    // splay key in the tree rooted at Node h. If a node with that key exists,
    //   it is splayed to the root of the tree. If it does not, the last node
    //   along the search path for the key is splayed to the root.
    private TreeNode<Key, Value> splay(TreeNode<Key, Value> h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            } else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else return rotateRight(h);
        } else if (cmp1 > 0) {
            // key not in tree, so we're done
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            } else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else return rotateLeft(h);
        } else return h;
    }


    /***************************************************************************
     *  Helper functions.
     ***************************************************************************/

    // height of tree (1-node tree has height 0)
    @Override
    public int height() {
        return height(root);
    }

    private int height(TreeNode<Key, Value> x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    @Override
    public int size() {
        return size(root);
    }


    private int size(TreeNode<Key, Value> x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    @Override
    public Key getLeftKey(Key parent) {
        TreeNode<Key, Value> node = root;
        TreeNode<Key, Value> treeNode = inOrderTraversalSearchByKey(node, parent);
        if (treeNode == null || treeNode.left == null) {
            return null;
        }
        return treeNode.left.key;
    }

    @Override
    public Key getRightKey(Key parent) {
        TreeNode<Key, Value> node = root;
        TreeNode<Key, Value> treeNode = inOrderTraversalSearchByKey(node, parent);
        if (treeNode == null || treeNode.right == null) {
            return null;
        }
        return treeNode.right.key;
    }

    // right rotate
    private TreeNode<Key, Value> rotateRight(TreeNode<Key, Value> h) {
        TreeNode<Key, Value> x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private TreeNode<Key, Value> rotateLeft(TreeNode<Key, Value> h) {
        TreeNode<Key, Value> x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    private TreeNode<Key, Value> inOrderTraversalSearchByKey(TreeNode<Key, Value> node, Key key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return inOrderTraversalSearchByKey(node.left, key);
        } else {
            return inOrderTraversalSearchByKey(node.right, key);
        }
    }

    private void inOrderTraversalSaveAllKeys(TreeNode<Key, Value> node, List<Key> keys) {
        if (node != null) {
            inOrderTraversalSaveAllKeys(node.left, keys);
            keys.add(node.key);
            inOrderTraversalSaveAllKeys(node.right, keys);
        }
    }
}
