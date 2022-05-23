/**
 * Class file and controller for the product window
 *
 * @author Easton Seidel
 */

package com.hermitfeather.ca482_pa_fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Product window GUI and functions
 */
public class ProductController implements Initializable {
    // Class variables
    private static Product globalProduct;
    private static int idIterate = 0;
    private static Text productTitle;
    private static TextField productId;
    private static TextField productName;
    private static TextField productInv;
    private static TextField productPrice;
    private static TextField productMax;
    private static TextField productMin;
    private static ObservableList<Part> tempList = FXCollections.observableArrayList();

    @FXML private TextField productSearch;
    @FXML private TableView<Part> existingParts;
    @FXML private TableView<Part> implementedParts;
    @FXML private TableColumn<Part, Integer> existingPartId;
    @FXML private TableColumn<Part, String> existingPartName;
    @FXML private TableColumn<Part, Integer> existingPartInv;
    @FXML private TableColumn<Part, Double> existingPartPrice;
    @FXML private TableColumn<Part, Integer> implementedPartId;
    @FXML private TableColumn<Part, String> implementedPartName;
    @FXML private TableColumn<Part, Integer> implementedPartInv;
    @FXML private TableColumn<Part, Double> implementedPartPrice;

    /**
     * Method for opening and customizing the add product window
     * @throws IOException for the gui
     */
    public static void addProductWindow() throws IOException {
        // Create the new product
        globalProduct = new Product(0, "", 0, 0, 0, 0);

        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Product");

        // Setup the elements
        pageElements(scene);

        addPartStage.setScene(scene);
        addPartStage.show();
    }

    /**
     * Method for opening and customizing the modify product window
     * @param oldProduct the product to modify
     * @throws IOException for the gui
     */
    public static void modifyProductWindow(Product oldProduct) throws IOException {
        // assign the globalProduct
        globalProduct = oldProduct;

        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage modifyPartStage = new Stage();

        // Setup the elements
        pageElements(scene);

        // Change Text
        modifyPartStage.setTitle("Modify Product");
        productTitle.setText("Modify Product");

        // Fill in the fields
        productId.setText(Integer.toString(oldProduct.getId()));
        productName.setText(oldProduct.getName());
        productInv.setText(Integer.toString(oldProduct.getStock()));
        productPrice.setText(Double.toString(oldProduct.getPrice()));
        productMax.setText(Integer.toString(oldProduct.getMax()));
        productMin.setText(Integer.toString(oldProduct.getMin()));

        // Set the scene and open
        modifyPartStage.setScene(scene);
        modifyPartStage.show();
    }

    /**
     * Initialization method for additional variables and objects in the xml file
     */
    @Override
    public void initialize(URL Location, ResourceBundle resources) {
        existingPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        existingPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        existingPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        existingPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        implementedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        implementedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        implementedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        implementedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the error label
        existingParts.setPlaceholder(new Label("No Parts Found!"));
        implementedParts.setPlaceholder(new Label("No Parts Found!"));

        // Setup upper table
        ObservableList<Part> allParts = Inventory.getAllParts();

        FilteredList<Part> filteredPart = new FilteredList<>(allParts, p -> true);

        productSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPart.setPredicate(part -> {
                // Keep the results the same if empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // compare part ID or part Name
                String lowerCaseFilter = newValue.toLowerCase();

                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else {
                    try {
                        if (part.getId() == Integer.parseInt(newValue)) {
                            return true;
                        }
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                }
                return false;
            });
        });

        // Assign the templist
        try {
            // fill the temp list iwth the objects
            tempList.addAll(globalProduct.getAllAssociatedParts());
        }
        catch (Exception e) {
            System.out.println("INFO: Empty globalProduct.");
        }

        // Place the values
        existingParts.setItems(filteredPart);

        // Setup Lower Table
        implementedParts.setItems(tempList);
    }

    /**
     * @param scene to pull objects from the xml
     */
    private static void pageElements(Scene scene) {
        // setup the input fields
        productId = (TextField) scene.lookup("#productId");
        productName = (TextField) scene.lookup("#productName");
        productInv = (TextField) scene.lookup("#productInv");
        productPrice = (TextField) scene.lookup("#productPrice");
        productMax = (TextField) scene.lookup("#productMax");
        productMin = (TextField) scene.lookup("#productMin");

        // Setup the text field
        productTitle = (Text) scene.lookup("#productTitle");
    }

    /**
     * Empty's the temporary list of parts
     */
    private static void emptyTempList() {
        // Empty the list
        while (!tempList.isEmpty()) {
            tempList.remove(0);
        }
    }

    /**
     * Method to close the window without modifying any products
     * @param event event variable to control the window
     */
    @FXML
    protected void hideWindow(ActionEvent event) {
        emptyTempList();

        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        active.close();
    }

    /**
     * Method to save new products or update existing products when the save button is clicked
     * @param event parameter to control and close the window
     */
    @FXML
    protected void onProductSaveClick(ActionEvent event) {
        // Make sure Inv is an Int
        try {
            Integer.parseInt(productInv.getText());
        } catch(NumberFormatException n) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Value Error!");
            errorAlert.setContentText("Please verify that your Inv value is a number.");
            errorAlert.showAndWait();
            return;
        }

        // Check max and min
        try {
            Integer.parseInt(productMax.getText());
            Integer.parseInt(productMin.getText());
        } catch(NumberFormatException n) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Value Error!");
            errorAlert.setContentText("Please verify that your Min and Max values are numbers.");
            errorAlert.showAndWait();
            return;
        }

        // Check the price
        try {
            Double.parseDouble(productPrice.getText());
        } catch(NumberFormatException n) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Value Error!");
            errorAlert.setContentText("Please verify that your Price value is a number.");
            errorAlert.showAndWait();
            return;
        }

        // Make sure the max is bigger than the min and inv is inbetween
        if (!(Integer.parseInt(productMax.getText()) > Integer.parseInt(productInv.getText()) &&
                Integer.parseInt(productInv.getText()) > Integer.parseInt(productMin.getText()))) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Value Error!");
            errorAlert.setContentText("Please verify that Max is greater than Min and Stock is between those.");
            errorAlert.showAndWait();

        }
        else {
            // Create the new part
            if (productTitle.getText().equals("Add Product")) {
                // Id Iterate
                idIterate++;

                globalProduct.setId(idIterate);
                globalProduct.setName(productName.getText());
                globalProduct.setPrice(Double.parseDouble(productPrice.getText()));
                globalProduct.setStock(Integer.parseInt(productInv.getText()));
                globalProduct.setMin(Integer.parseInt(productMin.getText()));
                globalProduct.setMax(Integer.parseInt(productMax.getText()));

                // Add to the list
                Inventory.addProduct(globalProduct);
            } else {
                // set the new values
                globalProduct.setId(Integer.parseInt(productId.getText()));
                globalProduct.setName(productName.getText());
                globalProduct.setPrice(Double.parseDouble(productPrice.getText()));
                globalProduct.setStock(Integer.parseInt(productInv.getText()));
                globalProduct.setMin(Integer.parseInt(productMin.getText()));
                globalProduct.setMax(Integer.parseInt(productMax.getText()));

                // Add to the list
                Inventory.updateProduct((Integer.parseInt(productId.getText())), globalProduct);
            }

            emptyTempList();

            // Close the stuff
            Node node = (Node) event.getSource();
            Stage active = (Stage) node.getScene().getWindow();

            active.close();
        }
    }

    /**
     * Method to add parts to the products parts list and array
     */
    @FXML
    protected void onPartAddButtonClick() {
        TablePosition pos = existingParts.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Part newPart = existingParts.getItems().get(row);

        if (!globalProduct.getAllAssociatedParts().contains(newPart)) {
            globalProduct.addAssociatedPart(newPart);
            tempList.add(newPart);
        }
    }

    /**
     * Method to remove parts from a products parts list and array
     */
    @FXML
    protected void onRemovePartButtonClick() {
        TablePosition pos = implementedParts.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Part oldPart = implementedParts.getItems().get(row);

        // Create the confirmation window
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Confirm Removal");
        confirmation.setContentText("Are you sure you'd like to remove the following part? " + oldPart.getName());
        confirmation.showAndWait();

        if (confirmation.getResult().getText().equals("OK")) {
            globalProduct.deleteAssociatedPart(oldPart);
            tempList.remove(oldPart);
        }
    }
}
