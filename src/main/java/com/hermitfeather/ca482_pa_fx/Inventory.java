/**
 * Main file and driver for the program
 *
 * @author Easton Seidel
 */

/**
 * FUTURE ENHANCEMENT
 * Save the data to a csv or other data file to have persistent data between launches.
 */

package com.hermitfeather.ca482_pa_fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Inventory extends Application implements Initializable {
    // Define Class Vars
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredPart = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProduct = FXCollections.observableArrayList();

    // Main window variables
    @FXML private TextField searchPart;
    @FXML private TextField searchProduct;
    @FXML private TableView<Part> partsTable;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInvColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInvColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    /**
     * start method to initialize the window and run the program.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 320);

        // Change some stuff
        stage.setTitle("Inventory");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * overridden initialize method to initialize additional variables that are in the xml file and set up the table data.
     */
    @Override
    public void initialize(URL Location, ResourceBundle resources) {
        // Set up the table cell's
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the error label
        partsTable.setPlaceholder(new Label("No Parts Found!"));
        productsTable.setPlaceholder(new Label("No Products Found!"));

        // ****************************
        // Set up the part table view
        // ****************************
        partsTable.setItems(filteredPart);

        // ****************************
        // Set up the product table view
        // ****************************
        productsTable.setItems(filteredProduct);
    }

    /**
     * method to add the part to the appropriate arrays
     */
    public static void addPart(Part newPart) {
        // Add logic for inhouse and outsourced
        filteredPart.add(newPart);
        allParts.add(newPart);
    }

    /**
     * method to add the product to the appropriate arrays
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        filteredProduct.add(newProduct);
    }

    /**
     * method to find a part based on it's ID
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            if ((allParts.get(i) != null) && (allParts.get(i).getId() == partId)) {
                return allParts.get(i);
            }
        }

        return null;
    }

    /**
     * method to find a part based on it's name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredList = FXCollections.observableArrayList();

        // Check each item and filter the list
        for (int i = 1; i <= allParts.size(); i++) {
            if ((lookupPart(i) != null) && ((lookupPart(i).getName().contains(partName)))) {
                filteredList.add(lookupPart(i));
            }
        }

        return filteredList;
    }

    /**
     * method to find a product based on it's ID
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            if ((allProducts.get(i) != null) && (allProducts.get(i).getId() == productId)) {
                return allProducts.get(i);
            }
        }

        return null;
    }

    /**
     * method to find a product based on it's name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredList = FXCollections.observableArrayList();

        // Check each item and filter the list
        for (int i = 0; i <= allProducts.size(); i++) {
            if ((lookupProduct(i) != null) && (lookupProduct(i).getName().contains(productName))) {
                filteredList.add(lookupProduct(i));
            }
        }

        return filteredList;
    }

    /**
     * Updates a part based on it's ID and a newly generated part object
     */
    public static void updatePart(int index, Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if ((allParts.get(i) != null) && (allParts.get(i).getId() == index)) {
                allParts.set(i, selectedPart);
            }
        }

        resetFilteredPart();
    }

    /**
     * Updates a product based on it's ID and a newly generated product object
     */
    public static void updateProduct(int index, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if ((allProducts.get(i) != null) && (allProducts.get(i).getId() == index)) {
                allProducts.set(i, newProduct);
            }
        }
        for (int i = 0; i < filteredProduct.size(); i++) {
            if ((filteredProduct.get(i) != null) && (filteredProduct.get(i).getId() == index)) {
                filteredProduct.set(i, newProduct);
            }
        }
    }

    /**
     * Removes a part from all arrays
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            for (int i = 0; i < allParts.size(); i++) {
                if ((allParts.get(i) != null) && (allParts.get(i).getId() == selectedPart.getId())) {
                    allParts.set(i, null);
                }
            }

            resetFilteredPart();

            return true;
        }
        return false;
    }

    /**
     * Removes a product from all arrays
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if ((allProducts.get(i) != null) && (allProducts.get(i).getId() == selectedProduct.getId())) {
                    allProducts.set(i, null);
                }
            }

            resetFilteredProduct();

            return true;
        }
        return false;
    }

    /**
     * Returns all valid parts
     */
    public static ObservableList<Part> getAllParts() {
        return filteredPart;
    }

    /**
     * Returns all valid products
     */
    public static ObservableList<Product> getAllProducts() {
        return filteredProduct;
    }

    /**
     * refreshes the temporary part list in the table
     */
    public static void resetFilteredPart() {
        while (!filteredPart.isEmpty()) {
            filteredPart.remove(0);
        }

        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i) != null) {
                filteredPart.add(allParts.get(i));
            }
        }
    }

    /**
     * refreshes the temporary product list in the table
     */
    public static void resetFilteredProduct() {
        while (!filteredProduct.isEmpty()) {
            filteredProduct.remove(0);
        }

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i) != null) {
                filteredProduct.add(allProducts.get(i));
            }
        }
    }

    /**
     * Opens the add part window
     */
    @FXML
    protected void onAddPartClick() throws IOException {
        PartController.addPartWindow();
    }

    /**
     * Opens the modify part window
     */
    @FXML
    protected void onModifyPartClick() {
        try {
            TablePosition pos = partsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Part oldPart = partsTable.getItems().get(row);

            PartController.modifyPartWindow(oldPart);
        }
        catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error Modifying Part!");
            errorAlert.setContentText("The Part couldn't be modified or the table is empty!");
            errorAlert.showAndWait();
        }
    }

    /**
     * Deletes the selected part from the table
     */
    @FXML
    protected void onDeletePartClick() {
        /**
         * RUNTIME ERROR
         * Gracefully handle hitting the delete button without a part selected or with an empty table.
         * The same was done with the product table and its delete button.
         */
        try {
            TablePosition pos = partsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Part oldPart = partsTable.getItems().get(row);

            // Create the confirmation window
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setHeaderText("Confirm Delete");
            confirmation.setContentText("Are you sure you'd like to delete the following part? " + oldPart.getName());
            confirmation.showAndWait();

            if (confirmation.getResult().getText().equals("OK")) {
                deletePart(oldPart);
            }
        }
        catch (Exception e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error Deleting Part!");
            errorAlert.setContentText("The following error occurred when deleting the part: " + e);
            errorAlert.showAndWait();
        }
    };

    /**
     * Opens the add product window
     */
    @FXML
    protected void onAddProductClick() throws IOException {
        ProductController.addProductWindow();
    }

    /**
     * Opens the modify product window
     */
    @FXML
    protected void onModifyProductClick() {

        try {
            TablePosition pos = productsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Product oldProduct = productsTable.getItems().get(row);

            ProductController.modifyProductWindow(oldProduct);
        }
        catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error Modifying Product!");
            errorAlert.setContentText("The product couldn't be modified or the table is empty!");
            errorAlert.showAndWait();
        }
    }

    /**
     * Deletes the selected product from the table
     */
    @FXML
    protected void onDeleteProductClick() {
        try {
            TablePosition pos = productsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Product oldProduct = productsTable.getItems().get(row);

            // Create the confirmation window
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setHeaderText("Confirm Delete");
            confirmation.setContentText("Are you sure you'd like to delete the following product? " + oldProduct.getName());
            confirmation.showAndWait();

            if (confirmation.getResult().getText().equals("OK")) {
                // Check to see if there are parts associated
                if (oldProduct.getAllAssociatedParts().isEmpty()) {
                    deleteProduct(oldProduct);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error Deleting Product!");
                    errorAlert.setContentText("The product still has parts associated. Please unassociated all parts before deleting.");
                    errorAlert.showAndWait();
                }
            }
        }
        catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error Deleting Product!");
            errorAlert.setContentText("The following error occurred when deleting the product: " + e);
            errorAlert.showAndWait();
        }
    };

    /**
     * Closes the program
     */
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    /**
     * Method to search for parts in the parts table
     */
    @FXML
    protected void onPartSearch() {
        String searchText = searchPart.getText();

        while (!filteredPart.isEmpty()) {
            filteredPart.remove(0);
        }

        // Check to see if it's a number
        try {
            Part returnedPart = lookupPart(Integer.parseInt(searchText));

            if (returnedPart != null) {
                filteredPart.add(returnedPart);
            }
        }
        catch (Exception e) {
            // Check for text or add part
            if (searchText.isEmpty()) {
                resetFilteredPart();
            }
            else {
                filteredPart.addAll(lookupPart(searchText));
            }
        }
    }

    /**
     * Method to search for products in the products table
     */
    @FXML
    protected void onProductSearch() {
        String searchText = searchProduct.getText();

        while (!filteredProduct.isEmpty()) {
            filteredProduct.remove(0);
        }

        // Check to see if it's a number
        try {
            Product returnedProduct = lookupProduct(Integer.parseInt(searchText));

            if (returnedProduct != null) {
                filteredProduct.add(returnedProduct);
            }
        }
        catch (Exception e) {
            // Check for text or add part
            if (searchText.isEmpty()) {
                resetFilteredProduct();
            }
            else {
                filteredProduct.addAll(lookupProduct(searchText));
            }
        }
    }
}

