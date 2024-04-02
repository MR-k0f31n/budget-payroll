package com.budget.model.view;

/**
 * @author MR.k0F31n
 */
public enum ViewExpense {
    ADD_OR_UPDATE_EXPENSES("expenses/addOrUpdateExpenses"),

    DASHBOARD_EXPENSES("expenses/dashboardExpenses"),
    DELETE_EXPENSE("expenses/deleteExpenses");

    private final String view;

    ViewExpense(String view) {
        this.view = view;
    }

    private final static String PATH = "/view/";
    private final static String FORMAT = ".fxml";

    public String toPath() {
        return PATH + view + FORMAT;
    }
}
