package webback.service;

import org.springframework.beans.factory.annotation.Autowired;
import webback.bean.*;
import webback.dao.ActivityDao;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Map;


public class ActivityService {
    @Autowired
    private ActivityDao activityDao;

    public ActivityDao getActivityDao() {
        return activityDao;
    }

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public List<Activity> findActivities(){
        return activityDao.selectAllActivities();
    }

    public List<Activity> selectActivitiesByUserId(int userId){
        return activityDao.selectActivitiesByUserId(userId);
    }

    public Boolean insertnewActivity(Activity activity){ return activityDao.insertnewActivity(activity);}

    public int updateAttendNum(int userId,int activityId){ return activityDao.updateAttendNum(userId,activityId);}

    public Boolean updateSigninTime(int userId,int activityId){return activityDao.updateSigninTime(userId,activityId);}

    public Boolean userpulishDiscuss(String userName, String activityTitle, String discussStr){ return activityDao.userpulishDiscuss(userName,activityTitle,discussStr);}

    public int discussPraiseUpdate(int userId,int discussId){return activityDao.discussPraiseUpdate(userId,discussId);}

    public Boolean userdoPraiseIs(int userId,int discussId){return activityDao.userdoPraiseIs(userId, discussId);}

    public List<Activity> selectAuserActivity(int userId){return activityDao.selectAuserActivity(userId);}

    public List<Activity> findActivityByStr(String activityStr){return activityDao.findActivityByStr(activityStr);}

    public List<Dresult> selectAllDisscussByactivityId(int userId, int activityId){return activityDao.selectAllDisscussByactivityId(userId,activityId);}

    public List<Activity> hotActivity(){return activityDao.hotActivity();}

    public List<Action> getSigninmostUser(){return activityDao.getSigninmostUser();}

    public List<User> getPraisemostUser(){return activityDao.getPraisemostUser();}

    public Boolean insertnewoffcialActivity(Activity activity,int userId,int punishId){return activityDao.insertnewoffcialActivity(activity,userId,punishId);}

    public List<Discuss> selectAllDiscussbyuserId(int userId){return activityDao.selectAllDiscussbyuserId(userId);}

    public List<Action> selectAllActionbyuserId(int userId){return activityDao.selectAllActionbyuserId(userId);}

    public Boolean usergetoutAactivity(int userId){return activityDao.usergetoutAactivity(userId);}
}
