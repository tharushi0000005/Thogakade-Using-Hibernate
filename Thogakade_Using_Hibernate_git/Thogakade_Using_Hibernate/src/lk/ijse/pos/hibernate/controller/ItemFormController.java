package lk.ijse.pos.hibernate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.hibernate.bo.BOFactory;
import lk.ijse.pos.hibernate.bo.BOType;
import lk.ijse.pos.hibernate.bo.custom.impl.ItemBOImpl;
import lk.ijse.pos.hibernate.dto.ItemDTO;
import lk.ijse.pos.hibernate.view.tm.ItemTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ItemFormController {
    public TableView <ItemTM>tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOperate;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnSearch;
    public AnchorPane root;

    ItemBOImpl itemBO = BOFactory.getInstance().getBO(BOType.ITEM);

    public void initialize(){
        findAll();
        tableListener();
        setCellValueFactory();
    }

    private void tableListener(){
        tblItem.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });
    }

    private void setData(ItemTM tm) {
        try {
            txtCode.setText(tm.getCode());
            txtDescription.setText(tm.getDescription());
            txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(tm.getQty()));
        }catch (Exception e){

        }
    }

    private void setCellValueFactory(){
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("qty"));
        colOperate.setCellValueFactory(new PropertyValueFactory("btn"));
    }

    public void findAll(){
        try {
            ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
            List<ItemDTO> all = itemBO.findAll();
            for(ItemDTO dto : all){
                Button btn = new Button("Delete");
                ItemTM tm = new ItemTM(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        btn
                );
                tmList.add(tm);
                btn.setOnAction((e) -> {
                    try {

                        ButtonType ok = new ButtonType("OK",
                                ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("NO",
                                ButtonBar.ButtonData.CANCEL_CLOSE);

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are You Sure ?", ok, no);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.orElse(no) == ok) {
                            if (itemBO.delete(tm.getCode())) {
                                new Alert(Alert.AlertType.CONFIRMATION,
                                        "Deleted", ButtonType.OK).show();
                                txtCode.setText(null);
                                txtDescription.setText(null);
                                txtUnitPrice.setText(null);
                                txtQtyOnHand.setText(null);
                                findAll();
                                return;
                            }
                            new Alert(Alert.AlertType.WARNING,
                                    "Try Again", ButtonType.OK).show();
                        } else {
                            //no
                        }


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            }
            tblItem.setItems(tmList);
        } catch (Exception e) {
            //  new Alert(Alert.AlertType.WARNING,"oops! cannot load data to the table").show();
        }
    }


    public void backOnAction(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("../view/DashBoardForm.fxml")));

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQtyOnHand.getText());

        try {
            if(itemBO.add(new ItemDTO(
                    code,
                    description,
                    unitPrice,
                    qty
            ))){
                txtCode.setText(null);
                txtDescription.setText(null);
                txtUnitPrice.setText(null);
                txtQtyOnHand.setText(null);
                new Alert(Alert.AlertType.INFORMATION, "Item Added..!").show();
                findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQtyOnHand.getText());

        try {
            if(itemBO.update(new ItemDTO(
                    code,
                    description,
                    unitPrice,
                    qty
            ))){
                findAll();
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                txtCode.setText(null);
                txtDescription.setText(null);
                txtUnitPrice.setText(null);
                txtQtyOnHand.setText(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        try {
            ItemDTO item = itemBO.getItem(code);
            if (item != null){
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                txtQtyOnHand.setText(String.valueOf(item.getQty()));
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
