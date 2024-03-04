package com.budget.controllers.dashboard;

import com.budget.App;
import com.budget.controllers.service.UpdateEmployeeController;
import com.budget.model.Employee;
import com.budget.model.View;
import com.budget.repository.EmployeeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


/**
 * @author MR.k0F31n
 */

public class DashboardEmployeeController {

    private final EmployeeRepository repository = new EmployeeRepository();

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableView<Employee> tableBirthday;

    @FXML
    private TableColumn<Employee, LocalDate> dateBirthdayColumn;

    @FXML
    private TableColumn<Employee, String> nameBirthdayColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, LocalDate> birthdayColumn;

    @FXML
    private TableColumn<Employee, Float> rateColumn;

    @FXML
    private TableColumn<Employee, LocalDate> employmentDateColumn;

    @FXML
    private Button visibleIsFiredButton;

    private Boolean visibleIsFiredEmployee;

    private static Stage stage;

    @FXML
    private void initialize() {
        visibleIsFiredEmployee = false;
        fillTableWithData();
        fillTableBirthday();
    }

    private void fillTableBirthday() {
        nameBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        tableBirthday.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.addAll(repository.getNextBirthday(String.valueOf(LocalDate.now().getMonthValue())));
        tableBirthday.setItems(employees);
    }

    private void fillTableWithData() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        employmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmployment"));
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setTable();
    }

    private void setTable() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.addAll(repository.getAllEmployee(visibleIsFiredEmployee.toString()));
        setNameVisibleIsFiredButton();
        employeeTableView.setItems(employees);
        visibleIsFiredEmployee = !visibleIsFiredEmployee;
    }

    private void setNameVisibleIsFiredButton() {
        if(visibleIsFiredEmployee) {
            visibleIsFiredButton.setText("Показать действующих");
        } else {
            visibleIsFiredButton.setText("Показать уволеных");
        }
    }

    @FXML
    private void visibleIsFiredEmployee() {
        setTable();
    }

    @FXML
    private void updateItem() {
        if (employeeTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(View.UPDATEEMPLOYEE.toPath()));
                Parent root = loader.load();
                UpdateEmployeeController updateController = loader.getController();
                updateController.init(employeeTableView.getSelectionModel().getSelectedItem());
                stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void returnToMainDashboard() throws IOException {
        App.setRoot(View.DASHBOARDMAIN.toPath());
    }

    @FXML
    private void switchToAddNewEmployee() throws IOException {
        App.setRoot(View.ADDEMPLOYEE.toPath());
    }

    @FXML
    public static void closeScene() {
        stage.close();
    }
}
