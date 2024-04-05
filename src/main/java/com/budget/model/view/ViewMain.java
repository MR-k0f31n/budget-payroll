package com.budget.model.view;

/**
 * @author MR.k0F31n
 */

public enum ViewMain {
    DASHBOARD_MAIN("main/dashboardMain");
    private final String view;

    ViewMain(String view) {
        this.view = view;
    }

    private final static String PATH = "/view/";
    private final static String FORMAT = ".fxml";

    public String toPath() {
        return PATH + view + FORMAT;
    }
}
