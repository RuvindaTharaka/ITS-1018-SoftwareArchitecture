package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tdm.ItemTM;
import view.tdm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CashierDashBoardController implements Initializable {
    public AnchorPane root;
    public Label lblOrderID;
    public Label lblDate;
    public ComboBox cmbCusID;
    public TextField txtQty;
    public Label lblGrossTotal;
    public Label lblDiscount;
    public Label lblNetTotal;
    public JFXButton btnAdd;
    public JFXButton btnConfirm;
    public RadioButton rdBtnCash;
    public RadioButton rdBtnCard;
    public Label lblCusName;
    public Label txtItemCode;
    public Label txtDescription;
    public Label txtQtyOnHand;
    public Label txtUnitPrice;
    public TextField txtBalance;
    public TextField txtBankName;
    public TextField txtAmount;
    public TextField txtCardNum;
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    private final CustomerBO customerBO=(CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);
    public JFXButton btnPrint;
    public Label lbTime1;
    public Label lblTime;

    @FXML
    private TableView<OrderDetailTM> tblList;
    @FXML
    private TableColumn<OrderDetailTM, String> colItemCodeList;
    @FXML
    private TableColumn<OrderDetailTM, String> colDescriptionList;
    @FXML
    private TableColumn<OrderDetailTM, String> colQtyList;
    @FXML
    private TableColumn<OrderDetailTM, String> colUnitPriceList;
    @FXML
    private TableColumn  <OrderDetailTM, Button>colBtn;
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
    private String orderId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setDisable(true);
        btnConfirm.setDisable(true);
        txtBankName.setDisable(true);
        txtCardNum.setDisable(true);
        btnPrint.setDisable(true);
        colItemCodeList.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescriptionList.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyList.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPriceList.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblList.getColumns().get(4);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblList.getItems().remove(param.getValue());
                tblList.getSelectionModel().clearSelection();
                try {
                    calTotal();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
       try {
           loadDataToTbl();
           loadCusID();
           loadDate();

           this.tblItems.setOnMouseClicked((e) -> {
               this.event2();
           });
       }catch (Exception ex){
       }
        orderId = generateNewOrderId();
        lblOrderID.setText(" " + orderId);
    }

    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadDate() {
        lblTime.setText(LocalDate.now().toString());
        Date d=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
        lbTime1.setText(formatter.format(d));
    }

    private void loadCusID() {
        ObservableList obList= FXCollections.observableArrayList();
        try {
            ArrayList<CustomerDTO> allCustomer = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomer) {
                obList.add(customer.getCusID());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCusID.setItems(obList);
    }

    private void event2() {
        this.txtItemCode.setText(tblItems.getSelectionModel().getSelectedItem().getItemCode());
        this.txtDescription.setText(tblItems.getSelectionModel().getSelectedItem().getDescription());
        this.txtUnitPrice.setText(String.valueOf(tblItems.getSelectionModel().getSelectedItem().getUnitPrice()));
        this.txtQtyOnHand.setText(String.valueOf(tblItems.getSelectionModel().getSelectedItem().getQtyOnHand()));
        txtItemCode.setDisable(false);
        txtQty.requestFocus();
        btnAdd.setDisable(false);
    }

    private void loadDataToTbl() {
        tblItems.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItems.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(),item.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void navigateHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/LogInForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void addListOnAction(ActionEvent actionEvent) {
        try {
            updateList();
            calTotal();
            enableOrDisablePlaceOrderButton();
            enableOrDisablePlaceOrderButton();
            enableOrDisablePlaceOrderButton();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtItemCode.setText("");
        txtQty.clear();
        btnAdd.setDisable(true);
        tblItems.requestFocus();
    }

    private void calTotal() throws SQLException, ClassNotFoundException {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetailTM detail : tblList.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblGrossTotal.setText(String.valueOf(total));
        lblNetTotal.setText(String.valueOf(total));
        lblDiscount.setText("0%");
    }

    private void updateList() {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        Double unitPrice= Double.valueOf(txtUnitPrice.getText().toString());
        int qty = Integer.parseInt(txtQty.getText());
        Double total = unitPrice*qty;
        boolean exists = tblList.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(txtItemCode.getText()));

        if (exists) {
            OrderDetailTM orderDetailTM = tblList.getItems().stream().filter(detail -> detail.getItemCode().equals(txtItemCode.getText())).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQtyOnHand(qty);
                orderDetailTM.setTotal(BigDecimal.valueOf(total));
                tblList.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQtyOnHand(orderDetailTM.getQtyOnHand() + qty);
                total = orderDetailTM.getQtyOnHand()*total;
                orderDetailTM.setTotal(BigDecimal.valueOf(total));
            }
            tblList.refresh();
        } else {
            tblList.getItems().add(new OrderDetailTM(itemCode, description, qty, BigDecimal.valueOf(unitPrice),BigDecimal.valueOf(total)));
        }
        try {
            updateStock();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateStock() throws SQLException, ClassNotFoundException {
       String code=txtItemCode.getText();
       int qty= Integer.parseInt(txtQty.getText());
       int qtyOnHand=tblItems.getSelectionModel().getSelectedItem().getQtyOnHand();
       int updatedQty=(qtyOnHand-qty);
       ItemDTO itemDTO=new ItemDTO(code,tblItems.getSelectionModel().getSelectedItem().getDescription(),tblItems.getSelectionModel().getSelectedItem().getPackSize(),tblItems.getSelectionModel().getSelectedItem().getUnitPrice(),updatedQty);
       itemBO.updateItem(itemDTO);
        loadDataToTbl();
    }

    public void loadCusNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(cmbCusID.getValue().toString());
        lblCusName.setText(customerDTO.getCusName());
        enableOrDisablePlaceOrderButton();
    }

    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCusID.getValue().toString(),tblList.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getItemCode(), tm.getQtyOnHand(), tm.getUnitPrice())).collect(Collectors.toList()),Double.parseDouble(lblNetTotal.getText()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblOrderID.setText("Order Id: " + orderId);
        txtItemCode.setText("");
        tblList.getItems().clear();
        txtQty.clear();
        calTotal();
    }


    private void enableOrDisablePlaceOrderButton() {
       if((cmbCusID.getSelectionModel().getSelectedItem() != null)){
            btnConfirm.setDisable(true);
        }else{
            cmbCusID.requestFocus();
        }
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId,List<OrderDetailDTO> orderDetails, double amount) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId, orderDate, customerId, orderDetails,amount);
            return purchaseOrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/AddCustomer.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void calBalance(ActionEvent actionEvent) {
        double price= Double.parseDouble(lblNetTotal.getText());
        double amount=Double.parseDouble(txtAmount.getText());
        double balance=amount-price;
        if(balance>0) {
            txtBalance.setText(String.valueOf(balance));
        }else{
            new Alert(Alert.AlertType.ERROR,"Insufficient!").showAndWait();
            txtAmount.clear();
            txtAmount.requestFocus();
        }
        btnPrint.setDisable(false);
        btnConfirm.setDisable(false);
    }

    public void printBill(ActionEvent actionEvent) {
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/BillReport.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList <OrderDetailTM>list = tblList.getItems();

                double total= Double.parseDouble(lblNetTotal.getText());
                double amount=Double.parseDouble(txtAmount.getText());
                double bal=Double.parseDouble(txtBalance.getText());

                HashMap map=new HashMap();
                map.put("Total",total);
                map.put("Amount",amount);
                map.put("Balance",bal);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,map,new JRBeanArrayDataSource(list.toArray(new OrderDetailTM[0])));
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException e) {
                e.printStackTrace();
            }
    }

    public void disableFeilds(ActionEvent actionEvent) {
        txtBankName.setDisable(true);
        txtCardNum.setDisable(true);
    }

    public void enableFeilds(ActionEvent actionEvent) {
        txtBankName.setDisable(false);
        txtBankName.requestFocus();
        txtCardNum.setDisable(false);
        txtCardNum.requestFocus();
    }
}
