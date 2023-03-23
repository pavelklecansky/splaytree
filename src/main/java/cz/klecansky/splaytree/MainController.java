package cz.klecansky.splaytree;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static cz.klecansky.splaytree.Utils.generateProductId;

public class MainController implements Initializable {

    private final int TREE_VIEW_POSITION = 1;

    @FXML
    public HBox hbox;

    private SplayTree<String, String> tree;

    private TreeView treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tree = new SplayTree<>();
        for (int i = 0; i < 15; i++) {
            String id = generateProductId();
            tree.put(id, id);
        }
        reloadUi();
    }

    private void reloadUi() {
        ChangeEmptyGraphUi(tree);
        Platform.runLater(() -> treeView.displayTree());
    }

    private void ChangeEmptyGraphUi(SplayTree<String, String> tree) {
        TreeView treeView = new TreeView(tree);
        hbox.getChildren().set(TREE_VIEW_POSITION, treeView);
        this.treeView = treeView;
        HBox.setHgrow(this.treeView, Priority.ALWAYS);
    }
}