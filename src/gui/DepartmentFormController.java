package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

    private Department department;
    private DepartmentService departmentService;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department department){
        this.department = department;
    }

    public void setDepartmentService(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if(department == null)
            throw new IllegalStateException("Entity was null");

        if(departmentService == null)
            throw new IllegalStateException("Service was null");

        try {
            department = getFormData();
            departmentService.sarOrUpdate(department);
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Error saving object", null,  e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Department getFormData() {
        Department dep = new Department();
        dep.setId(Utils.tryParseToInt(txtId.getText()));
        dep.setName(txtName.getText());
        return dep;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData(){

        if(department == null)
            throw new IllegalStateException("Entity was null");

        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(department.getName());
    }
}