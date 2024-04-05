package com.budget.model.view;

/**
 * @author MR.k0F31n
 */
public enum ViewEmployee {
    ADD_EMPLOYEE("employee/addEmployee"),
    DASHBOARD_EMPLOYEE("employee/dashboardEmployee"),
    UPDATE_EMPLOYEE("employee/updateEmployee");
    private final String view;

    ViewEmployee(String view) {
        this.view = view;
    }

    private final static String PATH = "/view/";
    private final static String FORMAT = ".fxml";

    public String toPath() {
        return PATH + view + FORMAT;
    }
}
