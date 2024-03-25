package com.budget.model;

/**
 * @author MR.k0F31n
 */

public enum View {
    ADD_EMPLOYEE("employee/addEmployee"),
    ADD_OR_UPDATE_EXPENSES("expenses/addOrUpdateExpenses"),
    DASHBOARD_EMPLOYEE("employee/dashboardEmployee"),
    DASHBOARD_EXPENSES("expenses/dashboardExpenses"),

    DASHBOARD_MAIN("main/dashboardMain"),

    UPDATE_EMPLOYEE("employee/updateEmployee");

    private final String view;

    View(String view) {
        this.view = view;
    }

    private final static String PATH = "/view/";
    private final static String FORMAT = ".fxml";

    public String toPath() {
        return PATH + view + FORMAT;
    }
}
