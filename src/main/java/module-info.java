module cz.klecansky.splaytree {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.klecansky.splaytree to javafx.fxml;
    exports cz.klecansky.splaytree;
}