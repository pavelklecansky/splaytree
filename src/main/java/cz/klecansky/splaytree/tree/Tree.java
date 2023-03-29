package cz.klecansky.splaytree.tree;

import java.util.List;

public interface Tree<Key extends Comparable<Key>, Value> {
    boolean contains(Key key);

    Key getRootKey();

    Value get(Key key);

    List<Key> keys();

    void put(Key key, Value value);

    Value remove(Key key);

    int height();

    int size();

    Key getLeftKey(Key parent);

    Key getRightKey(Key parent);

    class TreeNode<Key extends Comparable<Key>, Value> {
        public Key key;
        public Value value;
        public TreeNode<Key, Value> left;
        public TreeNode<Key, Value> right;

        public TreeNode(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
}
