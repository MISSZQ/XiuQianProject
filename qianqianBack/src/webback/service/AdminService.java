package webback.service;

import org.springframework.beans.factory.annotation.Autowired;
import webback.bean.AdminS;
import webback.dao.AdminDao;

public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    //管理员登陆检查
    public AdminS loginCheck(String username, String password){
        return adminDao.selectAdminFromLogin(username,password);
    }
}
