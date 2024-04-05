package com.budget.controllers.dashboard;

import com.budget.App;
import com.budget.controllers.service.ExpensesServiceController;
import com.budget.model.Expenses;
import com.budget.model.ExpensesItem;
import com.budget.model.view.ViewExpense;
import com.budget.model.view.ViewMain;
import com.budget.repository.ExpensesRepository;
import com.budget.util.ExpensesObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author MR.k0F31n
 */
public class DashboardExpensesController implements ExpensesObserver {
    private static Stage stage;
    private static final ExpensesRepository expensesRepository = new ExpensesRepository();

    @FXML
    private TableView<Expenses> tableExpenses;
    @FXML
    private TableColumn<Expenses, LocalDate> tableColumnDateExpenses;
    @FXML
    private TableColumn<Expenses, Double> tableColumnSum;
    @FXML
    private TableColumn<Expenses, String> tableColumnDescription;
    @FXML
    private TableColumn<Expenses, ExpensesItem> tableColumnItem;
    @FXML
    private TableColumn<Expenses, String> tableColumnIncomeNumber;
    @FXML
    private TableColumn<Expenses, LocalDate> tableColumnIncomeDate;
    @FXML
    private DatePicker filterDateStart;
    @FXML
    private DatePicker filterDateEnd;
    @FXML
    private TextField filterOnDescription;
    @FXML
    private TextField filterOnSum;
    @FXML
    private Label errorDate;
    @FXML
    private Label errorSum;

    @FXML
    private void initialize() {
        LocalDate currentDate = LocalDate.now();
        if (filterDateStart.getValue() == null) {
            LocalDate startDate = currentDate.withDayOfMonth(1);
            filterDateStart.setValue(startDate);
        }
        if (filterDateEnd.getValue() == null) {
            LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
            filterDateEnd.setValue(endDate);
        }
        fillTable();
        setTable();
    }

    private void fillTable() {
        tableColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));
        tableColumnDateExpenses.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        tableColumnIncomeNumber.setCellValueFactory(new PropertyValueFactory<>("incomeNumber"));
        tableColumnIncomeDate.setCellValueFactory(new PropertyValueFactory<>("incomeDate"));
        tableExpenses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void setTable() {
        ObservableList<Expenses> expensesList = FXCollections.observableArrayList();

        String description = null;
        if (!filterOnDescription.getText().isEmpty()) {
            description = filterOnDescription.getText();
        }

        Double sum = null;
        if (!filterOnSum.getText().isBlank()) {
            try {
                sum = Double.parseDouble(filterOnSum.getText());
                errorSum.setVisible(false);
            } catch (NumberFormatException e) {
                sum = null;
                errorSum.setVisible(true);
            }
        }

        if (filterDateStart.getValue() != null && filterDateEnd.getValue() != null) {
            expensesList.addAll(
                    expensesRepository.getExpenseDataWithDifferentParameters(
                            filterDateStart.getValue(),
                            filterDateEnd.getValue(),
                            description,
                            sum));
        }
        tableExpenses.setItems(expensesList);
    }

    @FXML
    private void switchToAddExpenses() {
        loadNewWindow(null, ViewExpense.ADD_OR_UPDATE_EXPENSES.toPath());
    }

    @FXML
    private void updateExpenses() {
        if (tableExpenses.getSelectionModel().getSelectedItem() != null) {
            loadNewWindow(tableExpenses.getSelectionModel().getSelectedItem(), ViewExpense.ADD_OR_UPDATE_EXPENSES.toPath());
        }
    }

    private void loadNewWindow(Expenses expenses, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ExpensesServiceController controller = loader.getController();
            controller.addObserver(this);

            if (path.equals(ViewExpense.ADD_OR_UPDATE_EXPENSES.toPath())) {
                if (expenses != null) {
                    controller.init(expenses);
                } else {
                    controller.initDate();
                }
            } else if (path.equals(ViewExpense.DELETE_EXPENSE.toPath())) {
                controller.initBeforeDeletion(expenses.getDate(), expenses.getSum(), expenses.getDescription(), expenses.getId());
            }

            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteExpenses() {
        if (tableExpenses.getSelectionModel().getSelectedItem() != null) {
            loadNewWindow(tableExpenses.getSelectionModel().getSelectedItem(), ViewExpense.DELETE_EXPENSE.toPath());
        }
    }

    @FXML
    private void switchToMainBoard() throws IOException {
        App.setRoot(ViewMain.DASHBOARD_MAIN.toPath());
    }

    public static void closeScene() {
        stage.close();
    }

    @Override
    public void updateExpensesTable() {
        setTable();
    }

    @FXML
    private void typedFilterDateStart() {
        isDateAfter();
    }

    @FXML
    private void typedFilterDateEnd() {
        isDateAfter();
    }


    private void isDateAfter() {
        errorDate.setVisible(filterDateStart.getValue().isAfter(filterDateEnd.getValue()));
    }

    @FXML
    private void filterButtonAction() {
        setTable();
    }
}
