package remi.utils;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import remi.gui.MainWindow;
import remi.model.Ui;

/**
 * A GUI for Duke using FXML.
 */
public class GuiLauncher extends Application {
    private Ui ui = new Ui();
    private static final String TITLE = "Remi";

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle(TITLE);

            FXMLLoader fxmlLoader = new FXMLLoader(GuiLauncher.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setUi(ui);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

