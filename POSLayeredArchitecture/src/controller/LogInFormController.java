package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LogInFormController  {
    public AnchorPane root;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public RadioButton rbtnCashier;
    public RadioButton rbtnManagement;
    public JFXButton btnLogIn;


    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        if(txtUserName.getText().equalsIgnoreCase("1")&&txtPassword.getText().equalsIgnoreCase("1")) {
            if (rbtnCashier.isSelected()) {
                URL resource = this.getClass().getResource("/view/CashierDashBoard.fxml");
                Parent root = FXMLLoader.load(resource);
                Scene scene = new Scene(root);
                Stage primaryStage = (Stage) (this.root.getScene().getWindow());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                Platform.runLater(() -> primaryStage.sizeToScene());
            } else if (rbtnManagement.isSelected()) {
                URL resource = this.getClass().getResource("/view/ManagementDashBoard.fxml");
                Parent root = FXMLLoader.load(resource);
                Scene scene = new Scene(root);
                Stage primaryStage = (Stage) (this.root.getScene().getWindow());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                Platform.runLater(() -> primaryStage.sizeToScene());
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Select Either Cashier or Management").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Username  or Password").show();
        }
    }
}
