package webback.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import webback.bean.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //查询所有用户
    public List<User> selectAllUsers(){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User ");
        List<User> users = query.list();
        return users;
    }

    //通过userId查找Action表中的记录
    public List<Action> selectActionsByUserId(int userId){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Action where userId.userId=?");
        query.setParameter(0,userId);
        List<Action> actions = query.list();
        return actions;
    }

    //通过userId查找Attention表的内容
    public List<Attention> selectAttentionByUserId(int userId){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Attention where userId.userId=?");
        query.setParameter(0,userId);
        List<Attention> attentions=query.list();
        return attentions;
    }

    //通过ActionID查找评论的内容。
    public List<Discuss> selectDiscussByActionId(int discussId){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Discuss where actionId.actionId=?");
        query.setParameter(0,discussId);
        List<Discuss> discusses = query.list();
        return discusses;
    }

    //通过学号和密码，登录
    public User selectUserLogin(String usernum,String userpassword){
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from User where userNum=? and userPassword=?");
        query.setParameter(0,usernum);
        query.setParameter(1,userpassword);
        User result=(User) query.uniqueResult();
        session.close();
        return result;
    }

    //用户注册
    public Boolean registernewUser(User user){
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            User auser=new User();
            //验证学号在数据库中存在
            Query query = session.createQuery("from User where userNum=? and userName=?");
            query.setParameter(0,user.getUserNum());
            query.setParameter(1,user.getUserName());
            auser = (User)query. uniqueResult();
            if (auser!=null) {
                auser.setUserEmail(user.getUserEmail());
                auser.setUserPassword(user.getUserPassword());
                auser.setUserEmail(user.getUserEmail());
                session.update(auser);
                tx.commit();
                session.close();

                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            session.close();
            return false;
        }
    }



    //用户修改信息
    public Boolean updateusermsg(User user){
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            User user1=session.get(User.class,user.getUserId());
            user1.setUserName(user.getUserName());
            user1.setUserPassword(user.getUserPassword());
            user1.setUserPhone(user.getUserPhone());
            user1.setUserImageUrl(user.getUserImageUrl());
            user1.setUserIntroduce(user.getUserIntroduce());
            session.update(user1);
            tx.commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    //用户关注其他用户,以及再次点击取消关注
    public int userAttentioninsert(int[] asd){
        int zzz=0;
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            Query query = session.createQuery("from Attention where userId.userId=? and attentionuserId.userId=?");
            query.setParameter(0,asd[0]);
            query.setParameter(1,asd[1]);
            Attention attention=(Attention) query.uniqueResult();
            if (attention==null) {
                String sql = "insert into Attention(userId,attentionuserId) values(" + asd[0] + "," + asd[1] + ")";
                Query query1 = session.createSQLQuery(sql);
                query1.executeUpdate();
                zzz=1;
            }else {
                String sql="delete from Attention where userId="+asd[0]+" and attentionuserId=" +asd[1];
                Query query1 = session.createSQLQuery(sql);
                query1.executeUpdate();
                zzz=2;
            }
            tx.commit();
            session.close();
            return zzz;
        }catch (Exception e){
            session.close();
            return 0;
        }
    }



    //某用户关注的所有用户
    public List<User> userAllAttention(int userId){
        List<User> users=new ArrayList<>();
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from Attention where userId.userId=?");
        query.setParameter(0,userId);
        List<Attention> attentions=query.list();
        for (int i=0;i<attentions.size();i++){
            users.add(attentions.get(i).getAttentionuserId());
        }
        System.out.println(users.size());
        session.close();
        return users;

    }

    //通过电话搜索用户
    public User selectUserbyPhone(String phone){
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from User where userPhone=?");
        query.setParameter(0,phone);
        User user=(User) query.uniqueResult();
        session.close();
        return user;
    }

    //根据userNum查找User
    public User selectUserbyuserNum(String userNum){
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from User where userNum=?");
        query.setParameter(0,userNum);
        User user=(User) query.uniqueResult();
        session.close();
        return user;
    }

    //用户对于某活动今天是否签到
    public List<String> selectAlluserNamebyactivityId(int activityId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        List<String> userNamelist=new ArrayList<>();
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from Everysignin where  activityId.activityId=? and signinDate=?");
        query.setParameter(0,activityId);
        query.setParameter(1,date);
        List<Everysignin> everysignins=query.list();
        for (int i=0;i<everysignins.size();i++){
            userNamelist.add(everysignins.get(i).getUserId().getUserName());
        }
        session.close();
        return userNamelist;
    }

    //根据用户Id回去用户
    public User selectAUserbyuserId(int userId){
        Session session=sessionFactory.openSession();
        User user=session.get(User.class,userId);
        session.close();
        return user;
    }

    //判断用户是否参加了该活动
    public Boolean userAttendactivityis(int userId,int activityId){
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from Action where userId.userId=? and activityId.activityId=?");
        query.setParameter(0,userId);
        query.setParameter(1,activityId);
        Action action=(Action) query.uniqueResult();
        session.close();
        if (action != null){
            return true;
        }else {
            return false;
        }
    }

    //判断用户今天是否签到
    public Boolean isSigninToday(int userId,int activityId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        Session session=sessionFactory.openSession();
        Query query = session.createQuery("from Everysignin where userId.userId=? and activityId.activityId=? and signinDate=?");
        query.setParameter(0,userId);
        query.setParameter(1,activityId);
        query.setParameter(2,date);
        Everysignin everysignin=(Everysignin) query.uniqueResult();
        if (everysignin != null){
            return true;
        }else {
            return false;
        }
    }

}
