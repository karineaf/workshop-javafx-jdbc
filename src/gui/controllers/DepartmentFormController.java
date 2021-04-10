package gui.controllers;

import java.net.URL;
import java.util.*;

import db.DbException;
import gui.listeners.DataChangeListener;
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
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

    private Department department;
    private DepartmentService departmentService;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if(department == null)
            throw new IllegalStateException("Entity was null");

        if(departmentService == null)
            throw new IllegalStateException("Service was null");

        try {
            department = getFormData();
            departmentService.saveOrUpdate(department);
            notifyDataChangeListener();
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Error saving object", null,  e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListener() {
        for(DataChangeListener listener : dataChangeListeners)
            listener.onDataChanged();

    }

    private Department getFormData() {
        Department dep = new Department();

        ValidationException exception = new ValidationException("Validation error");

        dep.setId(Utils.tryParseToInt(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().equals(""))
            exception.addError("name", "Field can't be empty");

        dep.setName(txtName.getText());

        if(exception.getErrors().size() > 0)
            throw exception;

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

    public void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if(fields.contains("name"))
            labelErrorName.setText(errors.get("name"));
    }
}