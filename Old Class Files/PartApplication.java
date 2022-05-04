package com.hermitfeather.ca482_pa_fx;

import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class PartApplication {
    // Grab the two buttons
    static RadioButton inHouse;
    static RadioButton outSourced;
    static Text source;
    static ToggleGroup tg = new ToggleGroup();

    // Create the addPart Window
    public static void addPart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PartApplication.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Part");

        // Assign the button values
        radioToggle(scene);

        // Set the scene and open
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    // Modify the addPart Window
    public static void modifyPart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PartApplication.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage modifyPartStage = new Stage();

        // Change text
        modifyPartStage.setTitle("Modify Part");
        Text partTitle = (Text) scene.lookup("#partTitle");
        partTitle.setText("Modify Part");

        // Assign the button values
        radioToggle(scene);

        // Set the scene and open
        modifyPartStage.setScene(scene);
        modifyPartStage.show();
    }

    // Set Radio button actions
    public static void radioToggle(Scene scene) {
        // Pull the buttons from the scene and FXML
        inHouse = (RadioButton) scene.lookup("#partInHouse");
        outSourced = (RadioButton) scene.lookup("#partOutsourced");
        source = (Text) scene.lookup("#source");

        // Add buttons to toggle group
        inHouse.setToggleGroup(tg);
        outSourced.setToggleGroup(tg);

        // Create the listener
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t1, Toggle t2) {
                // Button value
                if (ov.getValue() == outSourced) {
                    source.setText("Company Name");
                }
                else if (ov.getValue() == inHouse) {
                    source.setText("Machine ID");
                }
            }
        });
    }

    // Hide the popup window
    @FXML
    protected void hideWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        active.close();
    }
}
