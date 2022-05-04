package com.hermitfeather.ca482_pa_fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    // Main window function
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 320);
        stage.setTitle("Software 1 Inventory");
        stage.setScene(scene);
        stage.show();
    }

    // Close if exiting
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    // Open the addPart Window
    @FXML
    protected void onAddPartButtonClick() {
        try {
            PartApplication.addPart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the modifyPart Window
    @FXML
    protected void onModifyPartButtonClick() {
        try {
            PartApplication.modifyPart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the addPart Window
    @FXML
    protected void onAddProductButtonClick() {
        try {
            ProductApplication.addProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the addPart Window
    @FXML
    protected void onModifyProductButtonClick() {
        try {
            ProductApplication.modifyProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
