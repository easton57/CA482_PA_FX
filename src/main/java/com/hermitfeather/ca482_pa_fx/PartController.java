/**
 * Class file and controller for the part window
 *
 * @author Easton Seidel
 */

package com.hermitfeather.ca482_pa_fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class for the new and modify part windows and their functions
 */
public class PartController {
    // Additional class Variables
    private static RadioButton inHouseRadio;
    private static RadioButton outSourcedRadio;
    private static Text partText;
    private static Text partTitle;
    private static TextField partId;
    private static TextField partName;
    private static TextField partInv;
    private static TextField partPrice;
    private static TextField partMax;
    private static TextField partMin;
    private static TextField inOrOutPart;
    private static int idIterate = 0;

    /**
     * Method for opening and customizing the part window
     * @throws IOException for gui
     */
    public static void addPartWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Part");

        // Setup the Elements
        pageElements(scene);

        // Set the scene and open
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    /**
     * Method for opening and customizing the modify part window
     * @param oldPart is used for filling the existing information
     * @throws IOException for gui
     */
    public static void modifyPartWindow(Part oldPart) throws IOException {
        // setup the global part
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage modifyPartStage = new Stage();

        // Setup the Elements
        pageElements(scene);

        // Change text
        modifyPartStage.setTitle("Modify Part");
        partTitle.setText("Modify Part");

        // Fill in the fields
        partId.setText(Integer.toString(oldPart.getId()));
        partName.setText(oldPart.getName());
        partInv.setText(Integer.toString(oldPart.getStock()));
        partPrice.setText(Double.toString(oldPart.getPrice()));
        partMax.setText(Integer.toString(oldPart.getMax()));
        partMin.setText(Integer.toString(oldPart.getMin()));

        // Assign remaining value
        if(oldPart.getClass() == InHouse.class) {
            inOrOutPart.setText(Integer.toString(((InHouse) oldPart).getMachineId()));
        }
        else {
            outSourcedRadio.setSelected(true);
            inOrOutPart.setText(((Outsourced) oldPart).getCompanyName());
        }

        // Set the scene and open
        modifyPartStage.setScene(scene);
        modifyPartStage.show();
    }

    /**
     * Grab the elements from the XML
     * @param scene used to pull data from the existing scene
     */
    private static void pageElements(Scene scene) {
        // setup the input fields
        partId = (TextField) scene.lookup("#partId");
        partName = (TextField) scene.lookup("#partName");
        partInv = (TextField) scene.lookup("#partInv");
        partPrice = (TextField) scene.lookup("#partPrice");
        partMax = (TextField) scene.lookup("#partMax");
        partMin = (TextField) scene.lookup("#partMin");
        inOrOutPart = (TextField) scene.lookup("#inOrOutPart");

        // Setup the text field
        partTitle = (Text) scene.lookup("#partTitle");

        // Assign the button values
        radioToggle(scene);
    }

    /**
     * Method to create the radio buttons and add the listener
     * @param scene used to grab and set the values from the XML
     */
    private static void radioToggle(Scene scene) {
        // Pull the buttons from the scene and FXML
        ToggleGroup tg = new ToggleGroup();
        inHouseRadio = (RadioButton) scene.lookup("#partInHouse");
        outSourcedRadio = (RadioButton) scene.lookup("#partOutsourced");
        partText = (Text) scene.lookup("#partText");

        // Add buttons to toggle group
        inHouseRadio.setToggleGroup(tg);
        outSourcedRadio.setToggleGroup(tg);

        // Create the listener
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t1, Toggle t2) {
                // Button value
                if (ov.getValue() == outSourcedRadio) {
                    partText.setText("Company Name");
                } else if (ov.getValue() == inHouseRadio) {
                    partText.setText("Machine ID");
                }
            }
        });
    }

    /**
     * Method to close the window without modifying any parts
     * @param event used to close the window
     */
    @FXML
    protected void hideWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        active.close();
    }

    /**
     * Method to add or modify part objects in the window
     * @param event used to close the window after other tasks are executed
     */
    @FXML
    protected void onPartSaveClick(ActionEvent event) {
        // Declare the variables
        Part newPart;

        // Make sure the max is bigger than the min and inv is inbetween
        if (Integer.parseInt(partMax.getText()) > Integer.parseInt(partInv.getText()) &&
                Integer.parseInt(partInv.getText()) > Integer.parseInt(partMin.getText())) {
            // Create the new part
            if (partTitle.getText().equals("Add Part")) {
                // Iterate ID
                idIterate++;

                if (outSourcedRadio.isSelected()) {

                    newPart = new Outsourced(idIterate,
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            inOrOutPart.getText()
                    );
                } else {
                    newPart = new InHouse(idIterate,
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(inOrOutPart.getText())
                    );
                }

                // Add to the list
                Inventory.addPart(newPart);
            } else {
                // Update the part
                if (outSourcedRadio.isSelected()) {

                    newPart = new Outsourced(Integer.parseInt(partId.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            inOrOutPart.getText()
                    );
                } else {
                    newPart = new InHouse(Integer.parseInt(partId.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(inOrOutPart.getText())
                    );
                }

                // Add to the list
                Inventory.updatePart((Integer.parseInt(partId.getText())), newPart);
            }


            // Close the stuff
            Node node = (Node) event.getSource();
            Stage active = (Stage) node.getScene().getWindow();

            active.close();
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Value Error!");
            errorAlert.setContentText("Please verify that Max is greater than Min and Stock is between those.");
            errorAlert.showAndWait();
        }
    }
}
