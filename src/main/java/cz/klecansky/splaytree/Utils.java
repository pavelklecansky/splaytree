package cz.klecansky.splaytree;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Utils {

    public static String generateProductId() {
        StringBuilder builder = new StringBuilder();

        builder.append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomUtils.nextInt(1, 10))
                .append(RandomUtils.nextInt(1, 10));

        return builder.toString();
    }
    public static Product generateProduct() {
        StringBuilder builder = new StringBuilder();

        builder.append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomUtils.nextInt(1, 10))
                .append(RandomUtils.nextInt(1, 10));

        return new Product(builder.toString());
    }

    public static Dialog<Pair<String, String>> newTreeValue() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add new value");
        dialog.setHeaderText("Add new value to tree");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField keyField = new TextField();
        TextField valueField = new TextField();

        grid.add(new Label("Key:"), 0, 0);
        grid.add(keyField, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(valueField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(keyField.getText(), valueField.getText());
            }
            return null;
        });

        return dialog;
    }

    public static Dialog<String> removeTreeValue(SplayTree<String, Product> tree) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remove");
        dialog.setHeaderText("Remove value from tree");

        ButtonType addButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ChoiceBox<String> keyField = new ChoiceBox<>();
        keyField.getItems().addAll(tree.keys());

        grid.add(new Label("Key:"), 0, 0);
        grid.add(keyField, 1, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return keyField.getValue();
            }
            return null;
        });

        return dialog;
    }

    public static Dialog<String> findTreeValue(SplayTree<String, Product> tree) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Find");
        dialog.setHeaderText("Find value from tree");

        ButtonType addButtonType = new ButtonType("Find", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ChoiceBox<String> keyField = new ChoiceBox<>();
        keyField.getItems().addAll(tree.keys());

        grid.add(new Label("Key:"), 0, 0);
        grid.add(keyField, 1, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return keyField.getValue();
            }
            return null;
        });

        return dialog;
    }
}
