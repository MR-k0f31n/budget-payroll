package com.budget.controllers.dashboard;

import com.budget.App;
import com.budget.controllers.service.ExpensesServiceController;
import com.budget.model.Expenses;
import com.budget.model.View;
import com.budget.repository.ExpensesRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author MR.k0F31n
 */
public class DashboardExpensesController {
    private static Stage stage;

    private final ExpensesRepository expensesRepository = new ExpensesRepository();

    @FXML
    private TableView<Expenses> tableExpenses;
    @FXML
    private TableColumn<Expenses, LocalDate> tableColumnDateExpenses;
    @FXML
    private TableColumn<Expenses, Double> tableColumnSum;
    @FXML
    private TableColumn<Expenses, String> tableColumnDescription;
    @FXML
    private TableColumn<Expenses, String> tableColumnItem;
    @FXML
    private DatePicker filterDateStart;
    @FXML
    private DatePicker filterDateEnd;
    @FXML
    private TextField filterOnDescription;
    @FXML
    private TextField filterOnSum;


    @FXML
    private void initialize() {
        LocalDate dateNow = LocalDate.now();
        fillTable();
    }

    private void fillTable() {
        tableColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));
        tableColumnDateExpenses.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        tableExpenses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setTable();
    }

    private void setTable() {
        ObservableList<Expenses> expensesList = FXCollections.observableArrayList();
        expensesList.addAll(expensesRepository.getAllExpenses());
        tableExpenses.setItems(expensesList);
    }

    @FXML
    private void switchToAddExpenses() {
        loadNewWindow(null);
    }

    @FXML
    private void updateExpenses() {
        if (tableExpenses.getSelectionModel().getSelectedItem() != null) {
            loadNewWindow(tableExpenses.getSelectionModel().getSelectedItem());
        }
    }

    private void loadNewWindow(Expenses expenses) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(View.ADD_OR_UPDATE_EXPENSES.toPath()));
            Parent root = loader.load();
            ExpensesServiceController controller = loader.getController();

            if (expenses != null) {
                controller.init(expenses);
            } else {
                controller.initDate();
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
            loadNewWindow(tableExpenses.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void switchToMainBoard() throws IOException {
        App.setRoot(View.DASHBOARD_MAIN.toPath());
    }

    public static void closeScene() {
        stage.close();
    }
}
