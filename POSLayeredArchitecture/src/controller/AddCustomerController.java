package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerController implements Initializable {
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    
    public TableView <CustomerTM> tblCustomers;
    public TableColumn colCusID;
    public TableColumn colCusTitle;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusContact;
    public JFXButton btnAdd;
    public AnchorPane root;
    public TextField txtCusID;
    public TextField txtCusTitle;
    public TextField txtCusName;
    public TextField txtCusAddress;
    public TextField txtCusContact;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(C00)[0-9]{1,4}$");
    Pattern titlePattern = Pattern.compile("^[A-z.]{1,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9.,/ ]{3,20}$");
    Pattern contactPattern = Pattern.compile("^[0-9]{3}(-)[0-9]{7}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUI();
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        colCusTitle.setCellValueFactory(new PropertyValueFactory<>("cusTitle"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("cusContact"));

        loadDataToTable();
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtCusID, idPattern);
        map.put(txtCusTitle, titlePattern);
        map.put(txtCusName, namePattern);
        map.put(txtCusAddress, addressPattern);
        map.put(txtCusContact, contactPattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAdd.setDisable(true);
            }
        }
    }

    private void initUI() {
        txtCusID.clear();
        txtCusTitle.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
        btnAdd.setDisable(true);
    }

    private void loadDataToTable() {
        tblCustomers.getItems().clear();
        try {
           ArrayList<CustomerDTO> allCustomers= customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCustomers.getItems().add(new CustomerTM(customer.getCusID(),customer.getCusTitle(),customer.getCusName(),customer.getCusAddress(),customer.getCusContact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        String cusID = txtCusID.getText();
        String cusTitle = txtCusTitle.getText();
        String cusName = txtCusName.getText();
        String cusAddress = txtCusAddress.getText();
        String cusContact = txtCusContact.getText();

        if (!btnAdd.isDisable()) {
            try {
                if (existCustomer(cusID)) {
                    new Alert(Alert.AlertType.ERROR, cusID + " already exists").show();
                }
                CustomerDTO customerDTO = new CustomerDTO(cusID, cusTitle, cusName,cusAddress,cusContact);
                customerBO.addCustomer(customerDTO);
                tblCustomers.getItems().add(new CustomerTM(cusID, cusTitle, cusName,cusAddress,cusContact));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        txtCusID.clear();
        txtCusTitle.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
    }

    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void navigateHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/CashierDashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void enableBtnAdd(ActionEvent actionEvent) {
        btnAdd.setDisable(false);
    }
}
