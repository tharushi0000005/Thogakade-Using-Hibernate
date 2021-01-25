package lk.ijse.pos.hibernate.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public JFXButton btnOrder;
    public AnchorPane root;

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/pos/hibernate/view/"+location))));
        stage.show();
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("CustomerForm.fxml");
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ItemForm.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
    }
}
