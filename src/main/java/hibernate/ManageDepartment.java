package hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import data.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import data.Department;

public class ManageDepartment {
    private static SessionFactory factory;
    public ManageDepartment(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public static void main(String[] args){
//        ManageDepartment md = new ManageDepartment();
//
//        md.listDepartments();
//    }
    /* Method to CREATE an employee in the database */
    public Integer addDepartment(String name){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer departmentID = null;

        try {
            tx = session.beginTransaction();
            Department department = new Department(name);
            departmentID = (Integer) session.save(department);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return departmentID;
    }

    /* Method to  READ all the employees */
    public void listDepartments( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List departments = session.createQuery("FROM Department").list();
            for (Iterator iterator = departments.iterator(); iterator.hasNext();){
                Department department = (Department) iterator.next();
                System.out.print("Name: " +department.getName() + "\n");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<Department> getDepartments( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List departments = session.createQuery("FROM Department").list();
            ArrayList<Department> depArray = new ArrayList<Department>();
            for (Iterator iterator = departments.iterator(); iterator.hasNext();) {
                Department department = (Department) iterator.next();
                depArray.add(department);
            }
            tx.commit();
            return depArray;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    /* Method to UPDATE salary for an employee */
    public void updateDepartment(Integer departmentID, String name ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Department department = (Department)session.get(Department.class, departmentID);
            department.setName( name );
            session.update(department);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public String getDepartmentName(Integer departmentID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Department department = (Department)session.get(Department.class, departmentID);
            String name = department.getName();
            tx.commit();
            return name;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public int getDepartmentId(String name){
        List<Department> departments = getDepartments();
        for(Department department: departments){
            if(department.getName().equals(name)){
                return department.getId();
            }
        }
        return -1;
    }

    /* Method to DELETE an employee from the records */
    public void deleteDepartment(Integer departmentID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Department department = (Department)session.get(Department.class, departmentID);
            session.delete(department);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean hasEmployees(int departmentID, ManageEmployee manageEmployee){
        List<Employee> employees = manageEmployee.getEmployees();
        for(Employee employee: employees){
            if(employee.getDepartment_id() == departmentID){
                return true;
            }
        }

        return false;
    }

    public List<Employee> getEmployees(int departmentID, ManageEmployee manageEmployee){
        List<Employee> allEmployees = manageEmployee.getEmployees();
        List<Employee> employees = new ArrayList<Employee>();
        for(Employee employee: allEmployees){
            if(employee.getDepartment_id() == departmentID){
                employees.add(employee);
            }
        }

        return employees;
    }
}
