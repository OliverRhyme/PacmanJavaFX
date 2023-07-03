module dev.rhyme.pacmanjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.almasb.fxgl.all;
    requires kotlinx.coroutines.core;
    requires kotlinx.coroutines.javafx;

    opens dev.rhyme.pacmanjavafx to javafx.fxml;
    exports dev.rhyme.pacmanjavafx;
}