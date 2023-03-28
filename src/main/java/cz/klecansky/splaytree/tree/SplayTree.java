package cz.klecansky.splaytree.tree;

import java.util.ArrayList;
import java.util.List;

public class SplayTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private TreeNode<Key, Value> root;

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
        if (key == null) {
            return null;
        }
        root = splay(root, key);
        if (root == null) {
            return null;
        }
        return key.equals(root.key) ? root.value : null;
    }

    @Override
    public List<Key> keys() {
        List<Key> keys = new ArrayList<>();
        inOrderTraversalSaveAllKeys(root, keys);
        return keys;
    }

    @Override
    public void put(Key key, Value value) {
        if (root == null) {
            root = new TreeNode<>(key, value);
            return;
        }

        root = splay(root, key);

        int compare = key.compareTo(root.key);

        if (compare < 0) {
            putToLeftOfOldRoot(key, value);
        } else if (compare > 0) {
            putToRightOfOldRoot(key, value);
        } else {
            root.value = value;
        }
    }

    @Override
    public void remove(Key key) {
        if (root == null) return;
        if (key == null) return;

        root = splay(root, key);

        if (key.equals(root.key)) {
            if (isLeftNotInNode(root)) {
                root = root.right;
            } else {
                TreeNode<Key, Value> node = root.right;
                root = root.left;
                root = splay(root, key);
                root.right = node;
            }
        }
    }

    private TreeNode<Key, Value> splay(TreeNode<Key, Value> node, Key key) {
        if (node == null) return null;

        int compare = key.compareTo(node.key);

        if (compare < 0) {
            if (isLeftNotInNode(node)) {
                return node;
            }
            int compareLeftKey = key.compareTo(node.left.key);
            if (compareLeftKey < 0) {
                node = rightZigZig(node, key);
            } else if (compareLeftKey > 0) {
                rightZigZag(node, key);
            }
            if (isLeftNotInNode(node)) {
                return node;
            } else {
                return rightZig(node);
            }
        } else if (compare > 0) {
            if (isRightNotInNode(node)) {
                return node;
            }

            int compareRightKey = key.compareTo(node.right.key);
            if (compareRightKey < 0) {
                leftZigZag(node, key);
            } else if (compareRightKey > 0) {
                node = leftZigZig(node, key);
            }

            if (isRightNotInNode(node)) {
                return node;
            } else {
                return leftZig(node);
            }
        } else {
            return node;
        }
    }

    private void leftZigZag(TreeNode<Key, Value> node, Key key) {
        node.right.left = splay(node.right.left, key);
        if (node.right.left != null)
            node.right = rotateRight(node.right);
    }

    private TreeNode<Key, Value> leftZigZig(TreeNode<Key, Value> node, Key key) {
        node.right.right = splay(node.right.right, key);
        node = rotateLeft(node);
        return node;
    }

    private TreeNode<Key, Value> rightZigZig(TreeNode<Key, Value> node, Key key) {
        node.left.left = splay(node.left.left, key);
        node = rotateRight(node);
        return node;
    }

    private void rightZigZag(TreeNode<Key, Value> node, Key key) {
        node.left.right = splay(node.left.right, key);
        if (node.left.right != null)
            node.left = rotateLeft(node.left);
    }

    private TreeNode<Key, Value> rightZig(TreeNode<Key, Value> node) {
        return rotateRight(node);
    }

    private TreeNode<Key, Value> leftZig(TreeNode<Key, Value> node) {
        return rotateLeft(node);
    }

    private boolean isRightNotInNode(TreeNode<Key, Value> node) {
        return node.right == null;
    }

    private boolean isLeftNotInNode(TreeNode<Key, Value> node) {
        return node.left == null;
    }

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

    private TreeNode<Key, Value> rotateRight(TreeNode<Key, Value> node) {
        TreeNode<Key, Value> leftNode = node.left;
        node.left = leftNode.right;
        leftNode.right = node;
        return leftNode;
    }

    private TreeNode<Key, Value> rotateLeft(TreeNode<Key, Value> node) {
        TreeNode<Key, Value> rightNode = node.right;
        node.right = rightNode.left;
        rightNode.left = node;
        return rightNode;
    }

    private void putToRightOfOldRoot(Key key, Value value) {
        TreeNode<Key, Value> newNode = new TreeNode<>(key, value);
        newNode.right = root.right;
        newNode.left = root;
        root.right = null;
        root = newNode;
    }

    private void putToLeftOfOldRoot(Key key, Value value) {
        TreeNode<Key, Value> newNode = new TreeNode<>(key, value);
        newNode.left = root.left;
        newNode.right = root;
        root.left = null;
        root = newNode;
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
