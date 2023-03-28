package cz.klecansky.splaytree.view;

import cz.klecansky.splaytree.Product;
import cz.klecansky.splaytree.tree.SplayTree;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreeView extends Pane {
    private SplayTree<String, Product> tree;
    private double radius = 30;
    private double vGap = 65;

    public TreeView(SplayTree<String, Product> tree) {
        this.tree = tree;
        this.setMinSize(1000, 450);
        setStatus("Tree is empty");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear();
        if (tree.getRootKey() != null) {
            displayTree(tree.getRootKey(), getWidth() / 2, vGap, getWidth() / 4);
        }
    }

    private void displayTree(String key, double x, double y, double hGap) {
        if (tree.getLeftKey(key) != null) {
            Line line = new Line(x - hGap, y + vGap, x, y);
            line.setStroke(Color.RED);
            getChildren().add(line);
            displayTree(tree.getLeftKey(key), x - hGap, y + vGap, hGap / 2);
        }

        if (tree.getRightKey(key) != null) {
            Line line = new Line(x + hGap, y + vGap, x, y);
            line.setStroke(Color.BLUE);
            getChildren().add(line);
            displayTree(tree.getRightKey(key), x + hGap, y + vGap, hGap / 2);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        Text text = new Text(x - 28, y + 4, key + "");
        text.setStyle("-fx-font: 10 arial;");
        getChildren().addAll(circle, text);
    }
}
