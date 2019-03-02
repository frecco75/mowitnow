package com.bbc.automower;

import com.bbc.automower.ui.Grass;
import io.vavr.collection.List;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static com.bbc.automower.Main.*;

@Slf4j
public class MainUI extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        val filePath = getFilePath(List.ofAll(getParameters().getRaw()));

        handleError(execute(filePath)
                .map(Grass::new)
                .map(grass -> grass.fill(stage))
                .map(Grass::execute));

        stage.show();
    }
}
