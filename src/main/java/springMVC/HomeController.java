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
public class HomeController {
    @RequestMapping("/")
    public String display(HttpServletRequest req, Model m){
        return "homePage";
    }
}