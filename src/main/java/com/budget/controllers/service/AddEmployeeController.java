package com.budget.controllers.service;

import com.budget.App;

import com.budget.model.Employee;
import com.budget.model.View;
import com.budget.repository.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;


/**
 * @author MR.k0F31n
 */
public class AddEmployeeController {

    private final EmployeeRepository repository = new EmployeeRepository();

    @FXML
    private TextField nameField;

    @FXML
    private TextField rateField;

    @FXML
    private Button buttonSave;

    @FXML
    private Label errorRate;

    @FXML
    private Label errorName;

    @FXML
    private Label errorDate;

    @FXML
    private DatePicker dateBirthDay;

    @FXML
    private DatePicker dateEmployment;

    @FXML
    private Label criticalError;

    private String name;
    private Float rate;

    private LocalDate getDateBirthDay;

    private LocalDate getDateEmployment;

    @FXML
    private void saveNewEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setName(name);
        newEmployee.setRate(rate);
        newEmployee.setDateEmployment(getDateEmployment);
        newEmployee.setIsFired(false);
        if (getDateBirthDay != null) newEmployee.setBirthDay(getDateBirthDay);
        try {
            repository.addOrUpdateEmployee(newEmployee);
            App.setRoot(View.DASHBOARDEMPLOYEE.toPath());
            if (criticalError.isVisible()) criticalError.setVisible(false);
        } catch (Exception e) {
            criticalError.setText(e.getMessage());
            criticalError.setVisible(true);
        }
    }

    @FXML
    private void returnToEmployeeDashboard() throws IOException {
        App.setRoot(View.DASHBOARDEMPLOYEE.toPath());
    }

    @FXML
    private void checkBirthDayAndWrite() {
        if (dateBirthDay != null) {
            if (!dateBirthDay.getValue().isAfter(LocalDate.now())) {
                getDateBirthDay = dateBirthDay.getValue();
                if (errorDate.isVisible()) errorDate.setVisible(false);
            } else {
                errorDate.setText("Проверьте день рождения");
                errorDate.setVisible(true);
                getDateBirthDay = null;
            }
        }
    }

    @FXML
    private void checkDateEmploymentAndWrite() {
        if (dateEmployment != null) {
            if (!dateEmployment.getValue().isAfter(LocalDate.now())) {
                getDateEmployment = dateEmployment.getValue();
                if (errorDate.isVisible()) errorDate.setVisible(false);
            } else {
                errorDate.setText("Проверьте день приема на работу!");
                errorDate.setVisible(true);
                getDateEmployment = null;
            }
        }
        checkPointFromSaveEmployer();
    }

    @FXML
    private void validRateAndWrite() {
        if (rateField.getText() != null) {
            try {
                rate = Float.parseFloat(rateField.getText());
                if (errorRate.isVisible()) {
                    errorRate.setVisible(false);
                }
            } catch (NumberFormatException e) {
                errorRate.setText("Ставка - не верно");
                errorRate.setVisible(true);
                rate = null;
            }
        }
        checkPointFromSaveEmployer();
    }

    @FXML
    private void validNameAndWrite() {
        if (nameField.getText() != null) {
            try {
                name = nameField.getText();
                if (name.isBlank()) {
                    throw new ValidationException("Имя пустое");
                }
                if (errorName.isVisible()) {
                    errorName.setVisible(false);
                }
                checkNameFroNumbers();
            } catch (ValidationException e) {
                errorName.setText("Имя пустое");
                errorName.setVisible(true);
                name = null;
            }
        }
        checkPointFromSaveEmployer();
    }

    private void checkNameFroNumbers() {
        try {
            Integer.parseInt(name);
            errorName.setText("Имя состоит из цифр");
            errorName.setVisible(true);
            name = null;
        } catch (NumberFormatException ignored) {

        }
    }

    private void checkPointFromSaveEmployer() {
        buttonSave.setVisible(name != null && rate != null & getDateEmployment != null);
    }
}
