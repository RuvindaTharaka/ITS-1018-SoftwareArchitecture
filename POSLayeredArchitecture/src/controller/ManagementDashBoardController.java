package controller;

import bo.BoFactory;
import bo.custom.ManagementDashBoardBO;
import db.DBConnection;
import dto.ItemDTO;
import dto.OrderDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tdm.ItemTM;
import view.tdm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ManagementDashBoardController implements Initializable {
    public AnchorPane root;
    public Label lblMostMoving;
    public Label lblLeastMoving;
    private final ManagementDashBoardBO managementDashBoardBO = (ManagementDashBoardBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGEMENT);
    public TableView tblStockOut;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public AnchorPane salesReport;
    public TableColumn colOrderID;
    public TableColumn colOrderDate;
    public TableColumn colCusID;
    public TableColumn colAmount;
    public TableView tblIncome;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        try {
            loadMostMovingItem();
            loadLeastMovingItem();
            loadStockOut();
            loadDataToIncomeTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDataToIncomeTable() {
        try {
            ArrayList<OrderDTO> allItems = managementDashBoardBO.getAllIncome();
            for (OrderDTO item : allItems) {
                tblIncome.getItems().add(new OrderTM(item.getOrderId(),item.getOrderDate(),item.getCustomerId(), item.getAmount()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadStockOut() {
        try {
            ArrayList<ItemDTO> allItems = managementDashBoardBO.getStockOut();
            for (ItemDTO item : allItems) {
                tblStockOut.getItems().add(new ItemTM(item.getItemCode(), item.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadLeastMovingItem() throws SQLException, ClassNotFoundException {
        try {
            String item = managementDashBoardBO.getLeastMovingItem() ;
            this.lblLeastMoving.setText(item);
        }catch(Exception ex){
        }
    }

    private void loadMostMovingItem() throws SQLException, ClassNotFoundException {
        try {
            String item = managementDashBoardBO.getMostMovingItem() ;
            this.lblMostMoving.setText(item);
        }catch(Exception ex){
        }
    }


    public void NavigateHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/LogInForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void manageItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManageItem.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void printIncomeReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/IncomeReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printSalesReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/SalesReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getDbConnection().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
