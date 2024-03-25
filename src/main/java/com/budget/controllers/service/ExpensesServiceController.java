package com.budget.controllers.service;

import com.budget.controllers.dashboard.DashboardExpensesController;
import com.budget.model.Expenses;
import com.budget.model.ExpensesItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * @author MR.k0F31n
 */
public class ExpensesServiceController {

    @FXML
    private TextField expensesSum;

    @FXML
    private TextField expensesDescription;

    @FXML
    private ComboBox<ExpensesItem> expensesItem;

    @FXML
    private TextField incomeNumber;

    @FXML
    private DatePicker incomeDate;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker expensesDate;

    private Long idExpenses;

    public void init(Expenses oldExpenses) {
        if (oldExpenses != null) {
            idExpenses = oldExpenses.getId();
            expensesSum.setText(oldExpenses.getSum().toString());
            expensesDescription.setText(oldExpenses.getDescription());
            incomeNumber.setText(oldExpenses.getIncomeNumber());
            incomeDate.setValue(oldExpenses.getIncomeDate());
        }
    }

    public void initDate() {
        expensesDate.setValue(LocalDate.now());
    }


    @FXML
    private void saveOrUpdateExpenses() {

    }

    private void checkField() {
        saveButton.setVisible(expensesSum != null && expensesDescription != null);
    }

    @FXML
    private void cancel() {
        DashboardExpensesController.closeScene();
    }
}
