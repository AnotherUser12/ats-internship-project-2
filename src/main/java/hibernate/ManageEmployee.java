package hibernate;

import java.util.*;

import data.Department;
import data.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ManageEmployee {
    private static SessionFactory factory;
    public ManageEmployee(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public static void main (String[] args){
//        ManageEmployee me = new ManageEmployee();
//        me.listEmployees();
//    }

    public ArrayList<Employee> getEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            ArrayList<Employee> empArray = new ArrayList<Employee>();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee employee = (Employee) iterator.next();
                empArray.add(employee);
            }
            tx.commit();
            return empArray;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String name, String email, double salary, int department_id){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();

            //Create ValidatorFactory which returns validator
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            //It validates bean instances
            Validator validator = factory.getValidator();

            Employee employee = new Employee(name, email, salary, department_id);

            //Validate bean
            Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

            //Show errors
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<Employee> violation : constraintViolations) {
                    System.out.println(violation.getMessage());
                }
                return -1;
            }

            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to  READ all the employees */
    public void listEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("from Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("ID: " + employee.getId());
                System.out.print("; ");
                System.out.print("Name: " + employee.getName());
                System.out.print("; ");
                System.out.print("Email Name: " + employee.getEmail());
                System.out.print("; ");
                System.out.print("Salary: " + employee.getSalary());
                System.out.println("");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployeeName(Integer EmployeeID, String name){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            //Create ValidatorFactory which returns validator
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            //It validates bean instances
            Validator validator = factory.getValidator();

            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setName(name);

            //Validate bean
            Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

            //Show errors
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<Employee> violation : constraintViolations) {
                    System.out.println(violation.getMessage());
                }
                return;
            }

            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateEmployeeEmail(Integer EmployeeID, String email){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            //Create ValidatorFactory which returns validator
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            //It validates bean instances
            Validator validator = factory.getValidator();

            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setEmail(email);

            //Validate bean
            Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

            //Show errors
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<Employee> violation : constraintViolations) {
                    System.out.println(violation.getMessage());
                }
                return;
            }
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateEmployeeSalary(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            //Create ValidatorFactory which returns validator
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            //It validates bean instances
            Validator validator = factory.getValidator();

            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );

            Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);
            //Show errors
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<Employee> violation : constraintViolations) {
                    System.out.println(violation.getMessage());
                }
                return;
            }

            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateEmployeeDepartment(Integer EmployeeID, int departmentID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setDepartment_id(departmentID);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}