package com.budget.controllers.dashboard;

import com.budget.model.View;
import javafx.fxml.FXML;
import com.budget.App;

import java.io.IOException;

/**
 * @author MR.k0F31n
 */
public class DashboardMainController {

    @FXML
    private void switchToDashboardEmployee() throws IOException {
        App.setRoot(View.DASHBOARD_EMPLOYEE.toPath());
    }

    @FXML
    private void switchToDashboardExpenses() throws IOException {
        App.setRoot(View.DASHBOARD_EXPENSES.toPath());
    }

    @FXML
    private void switchToDashboardIncome() {
    }
}
