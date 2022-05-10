package com.hermitfeather.ca482_pa_fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Inventory extends Application implements Initializable {
    // Define Class Vars
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Main window variables
    @FXML private Button addProductButton;
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

    // Window functions
    // Main window function
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 320);

        // Change some stuff
        stage.setTitle("Inventory");

        stage.setScene(scene);
        stage.show();
    }

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

        // ****************************
        // Set up the part text view
        // ****************************
        FilteredList<Part> filteredPart = new FilteredList<>(allParts, p -> true);

        searchPart.textProperty().addListener((observable, oldValue, newValue) -> {
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
        partsTable.setItems(filteredPart);

        // ****************************
        // Set up the product text view
        // ****************************
        FilteredList<Product> filteredProduct = new FilteredList<>(allProducts, p -> true);

        searchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProduct.setPredicate(product -> {
                // Keep the results the same if empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // compare part ID or part Name
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else {
                    try {
                        if (product.getId() == Integer.parseInt(newValue)) {
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
        productsTable.setItems(filteredProduct);
    }

    // Specified in UML
    public static void addPart(Part newPart) {
        // Add logic for inhouse and outsourced
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        return allParts.get(partId);
    }

    //public static ObservableList<Part> lookupPart(String partName) {
    //
    //}

    public static Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    //public static ObservableList<Product> lookupProduct(String productName) {
    //
    //}

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void main(String args[]){
        launch(args);
    }

    // Part Pane Buttons
    @FXML
    protected void onAddPartClick() throws IOException { PartController.addPartWindow(); }

    @FXML
    protected void onModifyPartClick() throws IOException {
        TablePosition pos = partsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Part oldPart = partsTable.getItems().get(row);

        PartController.modifyPartWindow(oldPart);
    }

    @FXML
    protected void onDeletePartClick() {
        TablePosition pos = partsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Part oldPart = partsTable.getItems().get(row);

        deletePart(oldPart);
    };

    // Product Pane Buttons
    @FXML
    protected void onAddProductClick() throws IOException { ProductController.addProductWindow(); }

    @FXML
    protected void onModifyProductClick() throws IOException {
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

    @FXML
    protected void onDeleteProductClick() {
        try {
            TablePosition pos = productsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Product oldProduct = productsTable.getItems().get(row);

            deleteProduct(oldProduct);
        }
        catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error Deleting Product!");
            errorAlert.setContentText("The following error occurred when deleting the product: " + e);
            errorAlert.showAndWait();
        }
    };

    // Close if exiting
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
}

