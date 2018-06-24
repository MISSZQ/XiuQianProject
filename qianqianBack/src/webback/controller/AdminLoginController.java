package webback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webback.bean.AdminS;
import webback.service.AdminService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminLoginController {
    @Autowired
    private AdminService adminService;
    public AdminService getAdminService() {
        return adminService;
    }
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(){
        return "/Back/index";
    }

    @RequestMapping(value = "/adminLogin.do", method = RequestMethod.POST)
    public String adminLogin(Model model, String adminUsername, String adminPassword, HttpServletRequest request){
        AdminS admin = adminService.loginCheck(adminUsername,adminPassword);
        if(admin != null) {
            return "/Back/main";
        } else {
            return "/Back/index";
        }
    }
}
