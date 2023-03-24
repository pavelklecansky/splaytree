package cz.klecansky.splaytree;

import cz.klecansky.splaytree.experiment.ExperimentResult;
import cz.klecansky.splaytree.experiment.TreeExperiment;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static cz.klecansky.splaytree.Utils.generateProductId;

public class MainController implements Initializable {

    private final int TREE_VIEW_POSITION = 1;

    @FXML
    public HBox hbox;
    @FXML
    public Text findValue;
    @FXML
    public Text averageDisplay;
    @FXML
    public Text maxDisplay;
    @FXML
    public Text minDisplay;
    @FXML
    public Text modeDisplay;

    private SplayTree<String, String> tree;

    private TreeView treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tree = new SplayTree<>();
        ChangeEmptyGraphUi(tree);
        for (int i = 0; i < 12; i++) {
            String id = generateProductId();
            tree.put(id, id);
            reloadUi();
        }
    }

    @FXML
    public void addNewValue(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = Utils.newTreeValue();
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            tree.put(pair.getKey(), pair.getValue());
            reloadUi();
        });
    }

    @FXML
    public void remove(ActionEvent actionEvent) {
        Dialog<String> dialog = Utils.removeTreeValue(tree);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(key -> {
            tree.remove(key);
            reloadUi();
        });
    }

    @FXML
    public void findValue(ActionEvent actionEvent) {
        Dialog<String> dialog = Utils.findTreeValue(tree);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(key -> {
            String value = tree.get(key);
            findValue.setText(value);
            reloadUi();
        });
    }

    @FXML
    public void runExperiments(ActionEvent actionEvent) throws IOException {
        TreeExperiment treeExperiment = new TreeExperiment();
        ExperimentResult experimentResult = treeExperiment.runExperiments();
        averageDisplay.setText(String.valueOf(experimentResult.average()));
        maxDisplay.setText(String.valueOf(experimentResult.max()));
        minDisplay.setText(String.valueOf(experimentResult.min()));
        modeDisplay.setText(String.valueOf(experimentResult.mode()));
    }

    private void reloadUi() {
        Platform.runLater(() -> treeView.displayTree());
    }


    private void ChangeEmptyGraphUi(SplayTree<String, String> tree) {
        TreeView treeView = new TreeView(tree);
        hbox.getChildren().set(TREE_VIEW_POSITION, treeView);
        this.treeView = treeView;
        HBox.setHgrow(this.treeView, Priority.ALWAYS);
    }


}