package springMVC;
import hibernate.ManageDepartment;
import hibernate.ManageEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManagementController {
    @RequestMapping("/ManageDepartments")
    public String display(HttpServletRequest req, Model m)

    {
        System.out.println("Request has been received");
        System.out.println("Deleted Dep Id is: " + req.getParameter("deletedDepId"));
        System.out.println("Added Dep Name is: " + req.getParameter("addedDepName"));

        ManageDepartment manageDepartments = new ManageDepartment();
        ManageEmployee manageEmployees = new ManageEmployee();
        List<Integer> depIds = manageDepartments.getDepartments().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        List<String> depNames = manageDepartments.getDepartments().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        String deletedDepId = req.getParameter("deletedDepId");
        if(deletedDepId != null){
            if(depIds.indexOf(Integer.parseInt(deletedDepId)) > -1){
                if(!manageDepartments.hasEmployees(Integer.parseInt(deletedDepId), manageEmployees)){
                    System.out.println("Deleted Dep Id is: " + req.getParameter("deletedDepId"));
                    manageDepartments.deleteDepartment(Integer.parseInt(req.getParameter("deletedDepId")));
                }else{
                    System.out.println("Can't delete department with employees.");
                }
            }
        }

        depIds = manageDepartments.getDepartments().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        depNames = manageDepartments.getDepartments().stream().map((e1)->e1.getName()).collect(Collectors.toList());

        String addedDepName = req.getParameter("addedDepName");
        if(addedDepName!=null && addedDepName.length()>0 && depNames.indexOf(addedDepName) ==-1){
            System.out.println("Added Dep Name is: " + req.getParameter("addedDepName"));
            manageDepartments.addDepartment(addedDepName);
        }

        depIds = manageDepartments.getDepartments().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        depNames = manageDepartments.getDepartments().stream().map((e1)->e1.getName()).collect(Collectors.toList());

        String editedDepName = req.getParameter("editedDepName");
        int editedDepId = req.getParameter("editedDepId") != null? Integer.parseInt(req.getParameter("editedDepId")):-1000;
        if(editedDepName!=null && editedDepName.length()>0 && depIds.indexOf(editedDepId) > -1){
            System.out.println("Edited Dep Id is: " + editedDepId + " and Name is: " + editedDepName);
            manageDepartments.updateDepartment(editedDepId, editedDepName);
        }
        depIds = manageDepartments.getDepartments().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        depNames = manageDepartments.getDepartments().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        m.addAttribute("depNames", depNames);
        m.addAttribute("depIds", depIds);

        List<Integer> depEmployeeCounts = new ArrayList<Integer>();
        for(int depId : depIds){
            depEmployeeCounts.add(manageDepartments.getEmployees(depId, manageEmployees).size());
        }
        m.addAttribute("depEmployeeCounts", depEmployeeCounts);

        List<Double> depAverageSalaries = new ArrayList<Double>();
        depAverageSalaries = manageDepartments.getDepartments().stream().mapToDouble(d->manageDepartments.getEmployees(d.getId(), manageEmployees).stream().map(e->e.getSalary()).reduce((e1,e2)->e1+e2).orElse(0.0)/(double) (manageDepartments.getEmployees(d.getId(), manageEmployees).size() > 0.0 ? manageDepartments.getEmployees(d.getId(), manageEmployees).size():1)).boxed().collect(Collectors.toList());
        m.addAttribute("depAverageSalaries", depAverageSalaries);
        return "ManageDepartments";
    }

    @RequestMapping("/ManageEmployees")
    public String redirect(HttpServletRequest req, Model m)
    {
        ManageDepartment manageDepartments = new ManageDepartment();
        ManageEmployee manageEmployees = new ManageEmployee();
        List<Integer> empIds = manageEmployees.getEmployees().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        List<String> empNames = manageEmployees.getEmployees().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        List<String> empEmails = manageEmployees.getEmployees().stream().map((e1)->e1.getEmail()).collect(Collectors.toList());
        List<Double> empSalaries = manageEmployees.getEmployees().stream().map((e1)->e1.getSalary()).collect(Collectors.toList());
        List<String> empDepartments = manageEmployees.getEmployees().stream().map((e1)->manageDepartments.getDepartmentName(e1.getDepartment_id())).collect(Collectors.toList());

        if(req.getParameter("deletedEmpId")!=null){
            if(empIds.indexOf(Integer.parseInt(req.getParameter("deletedEmpId"))) > -1){
                System.out.println("Deleted Emp Id is: " + req.getParameter("deletedEmpId"));
                manageEmployees.deleteEmployee(Integer.parseInt(req.getParameter("deletedEmpId")));
            }
        }

        empIds = manageEmployees.getEmployees().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        empNames = manageEmployees.getEmployees().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        empEmails = manageEmployees.getEmployees().stream().map((e1)->e1.getEmail()).collect(Collectors.toList());
        empSalaries = manageEmployees.getEmployees().stream().map((e1)->e1.getSalary()).collect(Collectors.toList());
        empDepartments = manageEmployees.getEmployees().stream().map((e1)->manageDepartments.getDepartmentName(e1.getDepartment_id())).collect(Collectors.toList());

        int editedEmpId = req.getParameter("editedEmpId") != null? Integer.parseInt(req.getParameter("editedEmpId")):-1000;
        String editedEmpName = req.getParameter("editedEmpName");
        String editedEmpEmail = req.getParameter("editedEmpEmail");
        int editedEmpSalary = req.getParameter("editedEmpSalary") != null? Integer.parseInt(req.getParameter("editedEmpSalary")):-1000;
        String editedEmpDepartment = req.getParameter("editedEmpDepartment");
        if(empIds.indexOf(editedEmpId) > -1){
            if(editedEmpName != null && editedEmpName.length() > 0){
                manageEmployees.updateEmployeeName(editedEmpId, editedEmpName);
            }
            if(editedEmpEmail != null && editedEmpEmail.length() > 0 && empEmails.indexOf(editedEmpEmail) == -1){
                manageEmployees.updateEmployeeEmail(editedEmpId, editedEmpEmail);
            }
            if(editedEmpSalary >= 0){
                manageEmployees.updateEmployeeSalary(editedEmpId, editedEmpSalary);
            }
            if(editedEmpDepartment != null && manageDepartments.getDepartmentId(editedEmpDepartment) != -1){
                int departmentId = manageDepartments.getDepartmentId(editedEmpDepartment);
                manageEmployees.updateEmployeeDepartment(editedEmpId, departmentId);
            }else{
                System.out.println("The issue is here");
            }
        }

        empIds = manageEmployees.getEmployees().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        empNames = manageEmployees.getEmployees().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        empEmails = manageEmployees.getEmployees().stream().map((e1)->e1.getEmail()).collect(Collectors.toList());
        empSalaries = manageEmployees.getEmployees().stream().map((e1)->e1.getSalary()).collect(Collectors.toList());
        empDepartments = manageEmployees.getEmployees().stream().map((e1)->manageDepartments.getDepartmentName(e1.getDepartment_id())).collect(Collectors.toList());

        String addedEmpName = req.getParameter("addedEmpName");
        String addedEmpEmail = req.getParameter("addedEmpEmail");
        int addedEmpSalary = req.getParameter("addedEmpSalary") != null? Integer.parseInt(req.getParameter("addedEmpSalary")):-1000;
        String addedEmpDepartment = req.getParameter("addedEmpDepartment");

            if(addedEmpName != null && addedEmpName.length() > 0 && addedEmpEmail != null && addedEmpEmail.length() > 0 && empEmails.indexOf(addedEmpEmail) == -1&&addedEmpSalary >= 0 && addedEmpDepartment != null && manageDepartments.getDepartmentId(addedEmpDepartment) != -1){
                manageEmployees.addEmployee(addedEmpName, addedEmpEmail,addedEmpSalary,manageDepartments.getDepartmentId(addedEmpDepartment));
            }else{
                System.out.println("Issue Here");
            }


        empIds = manageEmployees.getEmployees().stream().map((e1)->e1.getId()).collect(Collectors.toList());
        empNames = manageEmployees.getEmployees().stream().map((e1)->e1.getName()).collect(Collectors.toList());
        empEmails = manageEmployees.getEmployees().stream().map((e1)->e1.getEmail()).collect(Collectors.toList());
        empSalaries = manageEmployees.getEmployees().stream().map((e1)->e1.getSalary()).collect(Collectors.toList());
        empDepartments = manageEmployees.getEmployees().stream().map((e1)->manageDepartments.getDepartmentName(e1.getDepartment_id())).collect(Collectors.toList());

        m.addAttribute("empIds", empIds);
        m.addAttribute("empNames", empNames);
        m.addAttribute("empEmails", empEmails);
        m.addAttribute("empSalaries", empSalaries);
        m.addAttribute("empDepartments", empDepartments);
        return "ManageEmployees";
    }
}
