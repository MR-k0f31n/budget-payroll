package com.budget.repository;

import com.budget.App;
import com.budget.model.Employee;
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

public class EmployeeRepository {

    public void addOrUpdateEmployee(Employee employee) {
        try (Session session = App.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(employee);
            tx.commit();
            session.close();
        }
    }

    public List<Employee> getAllEmployee(String isFired) {
        List<Employee> employees = new ArrayList<>();
        try (Session session = App.getSessionFactory().openSession()) {
            employees = session.createQuery("from Employee e where e.isFired = null or e.isFired = " + isFired, Employee.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> getNextBirthday() {
        List<Employee> employees = new ArrayList<>();
        try (Session session = App.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee e WHERE MONTH(e.birthDay) = :month and e.isFired = false", Employee.class);
            query.setParameter("month", LocalDate.now().getMonthValue());
            employees = query.list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
}
