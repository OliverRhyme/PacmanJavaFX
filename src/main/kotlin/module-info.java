module dev.rhyme.pacmanjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib.common;

    requires com.almasb.fxgl.all;
    requires kotlinx.coroutines.core;
    requires kotlinx.coroutines.javafx;
    requires javafx.media;

    opens dev.rhyme.pacmanjavafx to javafx.fxml;
    exports dev.rhyme.pacmanjavafx;
}