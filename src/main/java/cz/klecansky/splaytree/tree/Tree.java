package cz.klecansky.splaytree.tree;

import java.util.List;

public interface Tree<Key extends Comparable<Key>, Value> {
    boolean contains(Key key);

    Key getRootKey();

    Value get(Key key);

    List<Key> keys();

    void put(Key key, Value value);

    void remove(Key key);

    int height();

    int size();

    Key getLeftKey(Key parent);

    Key getRightKey(Key parent);

    // BST helper node data type
    class TreeNode<Key extends Comparable<Key>, Value> {
        public Key key;            // key
        public Value value;        // associated data
        public TreeNode<Key, Value> left, right;   // left and right subtrees

        public TreeNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
}
