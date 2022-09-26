package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Item;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ValidationUtil;
import view.tdm.CustomerTM;
import view.tdm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageItemController implements Initializable {
    public final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public AnchorPane root;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    @FXML
    private TableView<ItemTM> tblItems;
    @FXML
    private TableColumn<ItemTM, String> colItemCode;
    @FXML
    private TableColumn<ItemTM, String> colDescription;
    @FXML
    private TableColumn<ItemTM, String> colPackSize;
    @FXML
    private TableColumn<ItemTM, String> colUnitPrice;
    @FXML
    private TableColumn<ItemTM, String> colQtyOnHand;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern itemCodePattern = Pattern.compile("^(I00)[0-9]{1,4}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z]{1,25}$");
    Pattern packSizePattern = Pattern.compile("^[0-9]{1,5}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{1,5}(.)[0-9]{2}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,5}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        this.colItemCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        this.colPackSize.setCellValueFactory(new PropertyValueFactory("packSize"));
        this.colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        this.colQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));

        try{
            loadDataToTable();
            tblItems.setOnMouseClicked((e) -> {
                this.event();
            });
        }catch (Exception ex){}
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtItemCode, itemCodePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
               // new Alert(Alert.AlertType.INFORMATION, "Added!").showAndWait();
            }
                btnAdd.setDisable(true);
        }
    }

    private void loadDataToTable() {
        tblItems.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItems.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(),item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void event() {
        this.txtItemCode.setText(tblItems.getSelectionModel().getSelectedItem().getItemCode());
        txtItemCode.setDisable(true);
        txtDescription.setText(tblItems.getSelectionModel().getSelectedItem().getDescription());
        txtPackSize.setText(String.valueOf(tblItems.getSelectionModel().getSelectedItem().getPackSize()));
        txtUnitPrice.setText(String.valueOf(tblItems.getSelectionModel().getSelectedItem().getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(tblItems.getSelectionModel().getSelectedItem().getQtyOnHand()));
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void initUI() {
        try {
            txtItemCode.clear();
            txtDescription.clear();
            txtPackSize.clear();
            txtUnitPrice.clear();
            txtQtyOnHand.clear();
            txtItemCode.requestFocus();
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (Exception ex) {
        }
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode=txtItemCode.getText();
        String description=txtDescription.getText();
        int packSize= Integer.parseInt(txtPackSize.getText());
        BigDecimal unitPrice=BigDecimal.valueOf(Double.parseDouble(txtUnitPrice.getText()));
        int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        if(!existItem(itemCode)) {
            try {
                if (existItem(itemCode)) {
                    new Alert(Alert.AlertType.ERROR, itemCode + " already exists").show();
                }
                ItemDTO itemDTO = new ItemDTO(itemCode, description, packSize, unitPrice, qtyOnHand);
                itemBO.addItem(itemDTO);
                tblItems.getItems().add(new ItemTM(itemCode, description, packSize, unitPrice, qtyOnHand));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the Item " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Item Code Exists!").show();
        }
        initUI();
    }

    private boolean existItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(itemCode);
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode=txtItemCode.getText();
        String description=txtDescription.getText();
        int packSize= Integer.parseInt(txtPackSize.getText());
        BigDecimal unitPrice=BigDecimal.valueOf(Double.parseDouble(txtUnitPrice.getText()));
        int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        if(existItem(txtItemCode.getText())){
            try {
                /*Update Item*/
                ItemDTO dto = new ItemDTO(itemCode, description, packSize,unitPrice, qtyOnHand);
                itemBO.updateItem(dto);

                ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setPackSize(packSize);
                tblItems.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + itemCode).show();
        }
        initUI();
    }

    public void deleteItemOnAction(ActionEvent actionEvent) {
        String code = tblItems.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            itemBO.deleteItem(code);
            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initUI();
    }

    public void navigateHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManagementDashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void enableAddBtn(ActionEvent actionEvent) {
        btnAdd.setDisable(false);
    }

}
