package com.hermitfeather.ca482_pa_fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Inventory extends Application implements Initializable {
    // Define Class Vars
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts; // Keep broken so you can do the javadoc with a runtime error

    // Additional class Variables
    // Part text fields
    static ToggleGroup tg = new ToggleGroup();
    @FXML private Text partTitle;
    @FXML private RadioButton partInHouse;
    @FXML private RadioButton partOutsourced;
    @FXML private Text partText;
    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField inOrOutPart;

    // Main window variables
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

    // Create the addPart Window
    public void addPartWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddPart.fxml"));
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
    public void modifyPartWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage modifyPartStage = new Stage();

        // Change text
        modifyPartStage.setTitle("Modify Part");

        // Assign the button values
        radioToggle(scene);

        // Set the scene and open
        modifyPartStage.setScene(scene);
        modifyPartStage.show();

        // Change the text on the top left
        partTitle.setText("Modify Part");
    }

    // Create the addProduct Window
    public void addProductWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 615);
        Stage addPartStage = new Stage();
        addPartStage.setTitle("Add Product");
        addPartStage.setScene(scene);
        addPartStage.show();
    }

    // Modify the addProduct Window
    public void modifyProductWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Inventory.class.getResource("AddProduct.fxml"));
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

    // Additional Functions
    // Set Radio button actions
    public void radioToggle(Scene scene) {
        // Add buttons to toggle group
        partInHouse.setToggleGroup(tg);
        partOutsourced.setToggleGroup(tg);

        // Create the listener
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t1, Toggle t2) {
                // Button value
                if (ov.getValue() == partOutsourced) {
                    partText.setText("Company Name");
                }
                else if (ov.getValue() == partInHouse) {
                    partText.setText("Machine ID");
                }
            }
        });
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

    // FXML actions
    // Open the addPart Window
    @FXML
    protected void onAddPartButtonClick() {
        try {
            addPartWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the modifyPart Window
    @FXML
    protected void onModifyPartButtonClick() {
        try {
            modifyPartWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the addPart Window
    @FXML
    protected void onAddProductButtonClick() {
        try {
            addProductWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Open the addPart Window
    @FXML
    protected void onModifyProductButtonClick() {
        try {
            modifyProductWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Close if exiting
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
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
    protected void onPartSaveClick(ActionEvent event) {
        // Declare the variables
        Part newPart;

        // Create the new part
        if (partTitle.getText().equals("Add Part")) {
            if (partOutsourced.isArmed()) {
                newPart = new Outsourced((allParts.size() + 1),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partInv.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        inOrOutPart.getText()
                );
            }
            else {
                newPart = new InHouse((allParts.size() + 1),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partInv.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        Integer.parseInt(inOrOutPart.getText())
                );
            }

            // Add to the list
            addPart(newPart);
        }
        else {
            // Create the new part
            if (partOutsourced.isArmed()) {
                newPart = new Outsourced((allParts.size() + 1),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partInv.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        inOrOutPart.getText()
                );
            }
            else {
                newPart = new InHouse((allParts.size() + 1),
                        partName.getText(),
                        Double.parseDouble(partPrice.getText()),
                        Integer.parseInt(partInv.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        Integer.parseInt(inOrOutPart.getText())
                );
            }

            // Add to the list
            updatePart(Integer.parseInt(partId.getText()), newPart);
        }


        // Close the stuff
        Node node = (Node) event.getSource();
        Stage active = (Stage) node.getScene().getWindow();

        // update the table view
        partsTable.refresh();

        active.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.getItems().setAll(allParts);
    }
}