package com.hermitfeather.ca482_pa_fx;

import javafx.beans.Observable;
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

    // Create the addProduct Window
    public static void addProductWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Product");

        // Setup the elements
        pageElements(scene);

        addPartStage.setScene(scene);
        addPartStage.show();
    }

    // Modify the addProduct Window
    public static void modifyProductWindow(Product oldProduct) throws IOException {
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

        // Place the values
        existingParts.setItems(filteredPart);

        // Assign the templist
        try {
            tempList = globalProduct.getAllAssociatedParts();
        }
        catch (Exception e) {
            System.out.println("INFO: Empty globalProduct.");
        }

        // Setup Lower Table
        implementedParts.setItems(tempList);
    }

    // Make the values on the page modifiable
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

    private static void emptyTempList() {
        // Empty the list
        while (!tempList.isEmpty()) {
            tempList.remove(0);
        }
    }

    // Hide the popup window
    @FXML
    protected void hideWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        active.close();
    }

    // Add Part Save Button
    @FXML
    protected void onProductSaveClick(ActionEvent event) {
        // Declare the variables
        Product newProduct;

        // Id Iterate
        idIterate++;

        // Make sure the max is bigger than the min and inv is inbetween
        if (Integer.parseInt(productMax.getText()) > Integer.parseInt(productInv.getText()) &&
                Integer.parseInt(productInv.getText()) > Integer.parseInt(productMin.getText())) {
            // Create the new part
            if (productTitle.getText().equals("Add Product")) {
                newProduct = new Product(idIterate,
                        productName.getText(),
                        Double.parseDouble(productPrice.getText()),
                        Integer.parseInt(productInv.getText()),
                        Integer.parseInt(productMin.getText()),
                        Integer.parseInt(productMax.getText()));

                // Add to the list
                Inventory.addProduct(newProduct);
            } else {
                newProduct = new Product(Integer.parseInt(productId.getText()),
                        productName.getText(),
                        Double.parseDouble(productPrice.getText()),
                        Integer.parseInt(productInv.getText()),
                        Integer.parseInt(productMin.getText()),
                        Integer.parseInt(productMax.getText()));

                // Add to the list
                Inventory.updateProduct((Integer.parseInt(productId.getText()) - 1), newProduct);
            }

            // add the parts
            for (int i = 0; i < tempList.size(); i++) {
                newProduct.addAssociatedPart(tempList.get(i));
            }

            emptyTempList();

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

    // Add Part button
    @FXML
    protected void onPartAddButtonClick() {
        TablePosition pos = existingParts.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Part newPart = existingParts.getItems().get(row);

        if (!tempList.contains(newPart)) {
            tempList.add(newPart);
        }
    }

    // Remove Part
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
            tempList.remove(oldPart);
        }
    }
}
