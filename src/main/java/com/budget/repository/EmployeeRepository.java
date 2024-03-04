package com.budget.repository;

import com.budget.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MR.k0F31n
 */

public class EmployeeRepository {
    private final Configuration configuration = new Configuration().configure();
    private final SessionFactory sessionFactory = configuration.buildSessionFactory();


    public void addOrUpdateEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(employee);
            tx.commit();
            session.close();
        }
    }

    public List<Employee> getAllEmployee(String isFired) {
        List<Employee> employees = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            employees = session.createQuery("from Employee e where e.isFired = null or e.isFired = " + isFired, Employee.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> getNextBirthday(String month) {
        List<Employee> employees = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            employees = session.createQuery("FROM Employee e WHERE MONTH(e.birthDay) = " + month +
                    " and e.isFired = false", Employee.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
}
