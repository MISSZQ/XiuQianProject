package webback.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import webback.bean.AdminS;

@Repository
public class AdminDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //验证管理员，返回该对象。
    public AdminS selectAdminFromLogin(String adminName, String adminPassword){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM AdminS as a where a.adminName=? and a.adminPassword=?");
        query.setParameter(0,adminName);
        query.setParameter(1,adminPassword);
        AdminS result = (AdminS) query.uniqueResult();
        session.close();
        return result;
    }
}
