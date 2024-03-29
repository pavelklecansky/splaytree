package cz.klecansky.splaytree;

import cz.klecansky.splaytree.experiment.ExperimentResult;
import cz.klecansky.splaytree.experiment.TreeExperiment;
import cz.klecansky.splaytree.tree.SplayTree;
import cz.klecansky.splaytree.view.TreeView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    private final int TREE_VIEW_POSITION = 0;

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
    @FXML
    public LineChart cumulativeAverageChart;
    @FXML
    public VBox vbox;
    @FXML
    public StackPane stackPane;

    private SplayTree<String, Product> tree;

    private TreeView treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tree = new SplayTree<>();
        InitializeTree(tree);
    }

    @FXML
    public void addNewValue(ActionEvent actionEvent) {
        Dialog<String> dialog = Utils.newTreeValue();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(key -> {
            tree.put(key, new Product(key));
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
            Product value = tree.get(key);
            findValue.setText(value.id());
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
        XYChart.Series<Number, Number> cumulativeAverageGraph = dataForCumulativeChart(experimentResult.cumulativeAverage());
        cumulativeAverageChart.getData().clear();
        cumulativeAverageChart.getData().add(cumulativeAverageGraph);
    }

    private void reloadUi() {
        Platform.runLater(() -> treeView.displayTree());
    }


    private void InitializeTree(SplayTree<String, Product> tree) {
        TreeView treeView = new TreeView(tree);
        vbox.getChildren().set(TREE_VIEW_POSITION, treeView);
        this.treeView = treeView;
        HBox.setHgrow(this.treeView, Priority.ALWAYS);
    }

    private XYChart.Series<Number, Number> dataForCumulativeChart(List<Double> cumulativeAverage) {
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Data");
        for (int i = 0; i < cumulativeAverage.size(); i++) {
            dataSeries.getData().add(new XYChart.Data<>(i, cumulativeAverage.get(i)));
        }
        return dataSeries;
    }

    public void loadTree(ActionEvent actionEvent) throws FileNotFoundException {
        File treeDataFile = new File("./data/products.txt");
        if (!treeDataFile.exists()) {
            throw new IllegalArgumentException("File is not existing.");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(treeDataFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Product product = new Product(line);
                tree.put(product.id(), product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadUi();
    }
}