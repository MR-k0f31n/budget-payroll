package com.budget.model;

/**
 * @author MR.k0F31n
 */

public enum View {
    UPDATEEMPLOYEE("employee/updateEmployee"),
    DASHBOARDMAIN("main/dashboardMain"),
    DASHBOARDEMPLOYEE("employee/dashboardEmployee"),

    ADDEMPLOYEE("employee/addEmployee");

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
