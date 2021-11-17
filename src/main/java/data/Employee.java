package data;

public class Employee {
    private int id;
    private String name;
    private String email;
    private double salary;
    private int department_id;

    public Employee(){}
    public Employee(int id, String name, String email,double salary, int department_id){
        this.id = id;
        this.name=name;
        this.email=email;
        this.salary=salary;
        this.department_id=department_id;
    }
    public Employee(String name, String email,double salary, int department_id){
        this.name=name;
        this.email=email;
        this.salary=salary;
        this.department_id=department_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
}
