package com.budget.controllers.service;

import com.budget.controllers.dashboard.DashboardExpensesController;
import com.budget.model.Expenses;
import com.budget.model.ExpensesItem;
import com.budget.repository.ExpensesRepository;
import com.budget.util.ExpenseItemStringConverter;
import com.budget.util.ExpensesObserver;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private TextField incomeNumberField;
    @FXML
    private DatePicker incomeDateField;
    @FXML
    private Button saveButton;
    @FXML
    private DatePicker expensesDate;
    @FXML
    private Label criticalMessage;
    @FXML
    private Label criticalMarkSum;
    @FXML
    private Label criticalMarkDescription;
    @FXML
    private Label labelIncomeDateField;
    @FXML
    private Label labelIncomeNumberField;
    @FXML
    private Label descriptionExpenseBeforeDeletion;

    private final ExpensesRepository expensesRepository = new ExpensesRepository();
    private final List<ExpensesObserver> observers = new ArrayList<>();

    private Double sum;
    private String description;
    private ExpensesItem item;
    private String incomeNumber;
    private LocalDate incomeDate;
    private Long idExpense;
    private LocalDate date;

    public void addObserver(ExpensesObserver observer) {
        observers.add(observer);
    }

    public void init(Expenses oldExpenses) {
        if (oldExpenses != null) {
            initializingDropdownList();
            idExpense = oldExpenses.getId();
            sum = oldExpenses.getSum();
            description = oldExpenses.getDescription();
            date = oldExpenses.getDate();

            if (oldExpenses.getItem() != null) {
                item = oldExpenses.getItem();
                incomeNumber = oldExpenses.getIncomeNumber();
                incomeDate = oldExpenses.getIncomeDate();
            }
            setVisibleFields();
            isSparePartsItem();
            checkPointsToSave();
        }
    }

    private void setVisibleFields() {
        expensesSum.setText(sum.toString());
        expensesDescription.setText(description);
        expensesItem.setValue(item);
        expensesDate.setValue(date);
        if (incomeNumber != null) {
            incomeNumberField.setText(incomeNumber);
        }
        if (incomeDate != null) {
            incomeDateField.setValue(incomeDate);
        }
    }

    public void initDate() {
        initDateExpenses();
        initializingDropdownList();
    }

    @FXML
    private void saveOrUpdateExpenses() {
        Expenses newExpense = new Expenses();
        newExpense.setDate(expensesDate.getValue());
        newExpense.setItem(item);
        newExpense.setSum(sum);
        newExpense.setDescription(description);
        newExpense.setIncomeNumber(incomeNumber);
        newExpense.setIncomeDate(incomeDate);
        newExpense.setId(idExpense);
        try {
            expensesRepository.AddOrUpdateExpense(newExpense);
            notifyObservers();
            DashboardExpensesController.closeScene();
        } catch (Exception e) {
            criticalMessageShow();
        }
    }

    @FXML
    private void typedSumExpense() {
        try {
            sum = Double.parseDouble(expensesSum.getText());
            criticalMessageVisibilityToggle();
            if (criticalMarkSum.isVisible()) criticalMarkSum.setVisible(false);
        } catch (NumberFormatException e) {
            sum = null;
            criticalMessageShow();
            criticalMarkSum.setVisible(true);
        }
        checkPointsToSave();
    }

    @FXML
    private void typedExpenseDescription() {
        if (expensesDescription != null) {
            if (expensesDescription.getText().isBlank()) {
                description = null;
                criticalMessageShow();
                criticalMessage.setText("Описание состоит из пробелов");
                criticalMarkDescription.setVisible(true);
            } else {
                description = expensesDescription.getText();
                criticalMessageVisibilityToggle();
                if (criticalMarkDescription.isVisible()) criticalMarkDescription.setVisible(false);
            }
            checkPointsToSave();
        }
    }

    @FXML
    private void typedIncomeNumber() {
        incomeNumber = incomeNumberField.getText();
    }

    @FXML
    private void incomeDateSelected() {
        incomeDate = incomeDateField.getValue();
    }

    @FXML
    private void expenseDateSelected() {
        date = expensesDate.getValue();
    }

    @FXML
    private void expenseItemSelected() {
        item = expensesItem.getValue();
        checkPointsToSave();
        isSparePartsItem();
    }

    private void checkPointsToSave() {
        saveButton.setVisible(sum != null && description != null && item != null);
    }

    private void isSparePartsItem() {
        if (item == ExpensesItem.SPARE_PARTS) {
            labelIncomeNumberField.setVisible(true);
            incomeNumberField.setVisible(true);
            incomeNumber = incomeNumberField.getText();
            labelIncomeDateField.setVisible(true);
            incomeDateField.setVisible(true);
            incomeDate = incomeDateField.getValue();
        } else {
            labelIncomeNumberField.setVisible(false);
            incomeNumberField.setVisible(false);
            incomeNumber = null;
            labelIncomeDateField.setVisible(false);
            incomeDateField.setVisible(false);
            incomeDate = null;
        }
    }

    private void criticalMessageVisibilityToggle() {
        if (criticalMessage.isVisible()) {
            criticalMessage.setVisible(false);
        }
    }

    private void criticalMessageShow() {
        if (!criticalMessage.isVisible()) {
            criticalMessage.setText("В поле ошибка, пожалуйста исправте");
            criticalMessage.setVisible(true);
        }
    }

    private void initializingDropdownList() {
        expensesItem.setItems(FXCollections.observableArrayList(ExpensesItem.values()));
        expensesItem.setConverter(new ExpenseItemStringConverter());
    }

    private void initDateExpenses() {
        expensesDate.setValue(LocalDate.now());
    }

    @FXML
    private void cancel() {
        DashboardExpensesController.closeScene();
    }

    @FXML
    private void deleteButton() throws IOException {
        expensesRepository.deleteExpense(idExpense);
        notifyObservers();
        DashboardExpensesController.closeScene();
    }

    public void initBeforeDeletion(LocalDate date, Double sum, String description, Long id) {
        idExpense = id;
        String formatDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formatSum = decimalFormat.format(sum);
        String setLabel = String.format("Расход от %s сумма %s описание: %s", formatDate, formatSum, description);
        descriptionExpenseBeforeDeletion.setText(setLabel);
    }

    private void notifyObservers() {
        for (ExpensesObserver observer : observers) {
            observer.updateExpensesTable();
        }
    }
}
