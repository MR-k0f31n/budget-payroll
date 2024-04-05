package com.budget;

import com.budget.model.view.ViewMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Objects;

/**
 * @author MR.k0F31n
 */
public class App extends Application {

    private static Scene scene;

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Создаем SessionFactory из hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(ViewMain.DASHBOARD_MAIN.toPath()), 900, 600);
        stage.setTitle("Budget and Payroll");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/ico-app.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }
}
