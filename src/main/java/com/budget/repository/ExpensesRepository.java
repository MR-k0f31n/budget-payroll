package com.budget.repository;

import com.budget.App;
import com.budget.model.Expenses;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public class ExpensesRepository {

    public void AddOrUpdateExpense(Expenses expenses) {
        if (expenses != null) {
            try (Session session = App.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                session.saveOrUpdate(expenses);
                tx.commit();
                session.close();
            }
        }
    }

    public List<Expenses> getExpensesFromBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<Expenses> expensesList;
        try (Session session = App.getSessionFactory().openSession()) {
            Query<Expenses> query = session.createQuery("from Expenses e where date between :startDate and :endDate", Expenses.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            expensesList = new ArrayList<>(query.list());
            session.close();
        }
        return expensesList;
    }

    public List<Expenses> getAllExpenses() {
        List<Expenses> expensesList = new ArrayList<>();
        try (Session session = App.getSessionFactory().openSession()) {
            expensesList = session.createQuery("from Expenses e", Expenses.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expensesList;
    }

    public void deleteExpense(Long id) {
        try (Session session = App.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Expenses expenses = session.get(Expenses.class, id);
            if (expenses != null) {
                session.delete(expenses);
                tx.commit();
            } else {
                System.out.printf("Обьект с ID %s не найден%n", id);
            }
            session.close();
        }
    }

    public List<Expenses> getExpenseDataWithDifferentParameters(LocalDate startDate, LocalDate endDate, String description, Double sum) {
        List<Expenses> expensesList;
        try (Session session = App.getSessionFactory().openSession()) {
            String hql = "FROM Expenses e WHERE e.date BETWEEN :startDate AND :endDate";

            if (description != null) {
                hql += " AND LOWER(e.description) LIKE :description";
            }

            if (sum != null) {
                hql += " AND e.sum LIKE (:sum)";
            }
            Query<Expenses> query = session.createQuery(hql, Expenses.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            if (description != null) {
                query.setParameter("description", "%" + description.toLowerCase() + "%");
            }

            if (sum != null) {
                query.setParameter("sum", sum);
            }

            expensesList = query.list();
            session.close();
        }
        return expensesList;
    }
}
