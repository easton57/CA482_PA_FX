package com.hermitfeather.ca482_pa_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductApplication {
    // Create the addProduct Window
    public static void addProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PartApplication.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Product");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    // Modify the addProduct Window
    public static void modifyProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PartApplication.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage modifyPartStage = new Stage();

        // Change Text
        modifyPartStage.setTitle("Modify Product");
        Text productTitle = (Text) scene.lookup("#productTitle");
        productTitle.setText("Modify Product");

        // Set the scene and open
        modifyPartStage.setScene(scene);
        modifyPartStage.show();
    }

    // Hide the popup window
    @FXML
    protected void hideWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        active.close();
    }
}
