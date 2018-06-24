package webback.service;

import org.springframework.beans.factory.annotation.Autowired;
import webback.bean.Action;
import webback.bean.Discuss;
import webback.bean.User;
import webback.dao.UserDao;
import webback.bean.Attention;

import java.util.List;

public class UserService {
    @Autowired
    private UserDao userDao;
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findUsers(){
        return userDao.selectAllUsers();
    }
    public List<Action> findActionsByUserId (int userId) {
        return userDao.selectActionsByUserId(userId);
    }
    public List<Discuss> findDiscussesByActionId(int actionId){
        return userDao.selectDiscussByActionId(actionId);
    }
    public List<Attention> findAttentionsByUserId(int userId){return userDao.selectAttentionByUserId(userId);}
    public User userLogin(String username,String usernum){return userDao.selectUserLogin(username,usernum);}
    public Boolean userregister(User user){return userDao.registernewUser(user);}
    public Boolean updateusermsg(User user){return userDao.updateusermsg(user);}
    public int userAttentioninsert(int[] asd){return userDao.userAttentioninsert(asd);}
    public List<User> userAllAttention(int userId){return userDao.userAllAttention(userId);}
    public User selectUserbyPhone(String phone){return userDao.selectUserbyPhone(phone);}
    public User selectUserbyuserNum(String userNum){return userDao.selectUserbyuserNum(userNum);}
    public List<String> selectAlluserNamebyactivityId(int activityId){return userDao.selectAlluserNamebyactivityId(activityId);}
    public User selectAUserbyuserId(int userId){return userDao.selectAUserbyuserId(userId);}
    public Boolean userAttendactivityis(int userId,int activityId){return userDao.userAttendactivityis(userId,activityId);}
    public Boolean isSigninToday(int userId,int activityId){return userDao.isSigninToday(userId,activityId);}


}
