module cz.klecansky.splaytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens cz.klecansky.splaytree to javafx.fxml;
    exports cz.klecansky.splaytree;
}