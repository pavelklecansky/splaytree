module cz.klecansky.splaytree {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires atlantafx.base;


    opens cz.klecansky.splaytree to javafx.fxml;
    exports cz.klecansky.splaytree;
    exports cz.klecansky.splaytree.view;
    opens cz.klecansky.splaytree.view to javafx.fxml;
    exports cz.klecansky.splaytree.tree;
    opens cz.klecansky.splaytree.tree to javafx.fxml;
}