package com.budget.repository;

import com.budget.model.Expenses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public class ExpensesRepository {
    private final Configuration configuration = new Configuration().configure();
    private final SessionFactory sessionFactory = configuration.buildSessionFactory();

    public List<Expenses> getAllExpenses() {
        List<Expenses> expensesList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            expensesList = session.createQuery("from Expenses e", Expenses.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expensesList;
    }
}
