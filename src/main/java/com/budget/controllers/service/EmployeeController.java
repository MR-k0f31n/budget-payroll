package com.budget.controllers.service;


import com.budget.App;
import com.budget.controllers.dashboard.DashboardEmployeeController;
import com.budget.model.Employee;
import com.budget.model.View;
import com.budget.repository.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author MR.k0F31n
 */
public class EmployeeController {

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

    @FXML
    private CheckBox checkIsFired;

    private String name;
    private Float rate;

    private LocalDate getDateBirthDay;

    private LocalDate getDateEmployment;

    private Employee employeeBeforeUpdate;


    public void init(Employee employee) {
        employeeBeforeUpdate = employee;
        try {
            name = employee.getName();
            nameField.setText(name);
            rate = employee.getRate();
            rateField.setText(rate.toString());
            getDateEmployment = employee.getDateEmployment();
            if (employee.getBirthDay() != null) {
                getDateBirthDay = employee.getBirthDay();
                dateBirthDay.setValue(getDateBirthDay);
            }
            dateEmployment.setValue(getDateEmployment);
            checkIsFired.setSelected(employee.getIsFired());
        } catch (NullPointerException e) {
            checkError();
            criticalError.setText("Перед сохарением проверьте ошибки");
            criticalError.setVisible(true);
        }
        checkPointFromSaveEmployer();
    }

    private void checkError() {
        if (nameField.getText() == null) {
            errorName.setText("Ф.И.О пусто");
            errorName.setVisible(true);
        }

        if (rateField.getText() == null) {
            errorRate.setText("Измените ставку");
            errorRate.setVisible(true);
        }

        if (dateEmployment.getValue() == null) {
            errorDate.setText("Обязательно укажите дату трудоустройства");
            errorDate.setVisible(true);
        }
    }

    private void checkPointFromSaveEmployer() {
        buttonSave.setVisible(name != null && rate != null & getDateEmployment != null);
    }


    @FXML
    private void updateEmployee() throws IOException {
        Employee newEmployee = new Employee();
        if (employeeBeforeUpdate != null) {
            newEmployee.setId(employeeBeforeUpdate.getId());
            newEmployee.setIsFired(checkIsFired.isSelected());
        } else {
            newEmployee.setIsFired(false);
        }

        newEmployee.setName(name);
        newEmployee.setRate(rate);
        newEmployee.setDateEmployment(getDateEmployment);

        if (getDateBirthDay != null) {
            newEmployee.setBirthDay(getDateBirthDay);
        }
        
        repository.addOrUpdateEmployee(newEmployee);
        DashboardEmployeeController.closeScene();
        App.setRoot(View.DASHBOARD_EMPLOYEE.toPath());
    }

    @FXML
    private void returnToEmployeeDashboard() {
        DashboardEmployeeController.closeScene();
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

    @FXML
    private void eventFired() {
        checkPointFromSaveEmployer();
    }
}
