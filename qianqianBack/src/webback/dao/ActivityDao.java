package webback.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import webback.bean.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActivityDao {
    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //查询所有活动
    public List<Activity> selectAllActivities(){
        List<Activity> activityList=new ArrayList<>();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Activity where activityIsPrivate=?");
        query.setParameter(0,1);
        List<Activity> activitys = query.list();
        for (int i=0;i<activitys.size();i++){
            Boolean res=date.before(activitys.get(i).getActivityEndTime());
            if (res){
                activityList.add(activitys.get(i));
            }
        }
        return activityList;
    }

    //查询该userId创建的活动
    public List<Activity> selectActivitiesByUserId(int userId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Activity where activityStartUser.userId = ?");
        query.setParameter(0, userId);
        List<Activity> activities = query.list();
        return activities;
    }

    //官方发布活动
    public Boolean insertnewoffcialActivity(Activity activity,int userId,int punishId){
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            String sql="\n" +
                    "insert into Activity(activityTitle,activityIntroduce,activityImageUrl,activityStartTime,activityEndTime,activityModel,activityUserNumber,activityStartUser,activityAttendNum,punishId)\n" +
                    "values ('"+activity.getActivityTitle()+"','"+activity.getActivityIntroduce()+"','"+activity.getActivityImageUrl()+"','"+activity.getActivityStartTime()+"','"+activity.getActivityEndTime()+"',1,"+activity.getActivityUserNumber()+","+userId+",0,"+punishId+")";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    //用户创建活动
    public Boolean insertnewActivity(Activity activity){
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            if(activity.getActivityModel().equals(1)) {
                String sql = "\n" +
                        "insert into Activity(activityTitle,activityIntroduce,activityImageUrl,activityStartTime,activityEndTime,activityModel,activityUserNumber,activityStartUser,activityAttendNum,punishId,activityIsPrivate)" +
                        "values ('" + activity.getActivityTitle() + "','" + activity.getActivityIntroduce() + "','" + activity.getActivityImageUrl() + "','" + activity.getActivityStartTime() + "','" + activity.getActivityEndTime() + "'," + activity.getActivityModel() + "," + activity.getActivityUserNumber() + "," + activity.getActivityStartUser().getUserId() + ",1,1,"+activity.getActivityIsPrivate()+")";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                tx.commit();
                Query query3 = session.createQuery("from Activity where activityTitle=?");
                query3.setParameter(0,activity.getActivityTitle());
                Activity activity1=(Activity) query3.uniqueResult();
                session.close();
                updateAttendNum(activity.getActivityStartUser().getUserId(),activity1.getActivityId());
                return true;
            }else {
                String sql = "\n" +
                        "insert into Activity(activityTitle,activityIntroduce,activityImageUrl,activityStartTime,activityEndTime,activityModel,activityStartUser,activityAttendNum,activityIsPrivate)" +
                        "values ('" + activity.getActivityTitle() + "','" + activity.getActivityIntroduce() + "','" + activity.getActivityImageUrl() + "','" + activity.getActivityStartTime() + "','" + activity.getActivityEndTime() + "'," + activity.getActivityModel() + "," + activity.getActivityStartUser().getUserId() + ",0,"+activity.getActivityIsPrivate()+")";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                tx.commit();
                Query query3 = session.createQuery("from Activity where activityTitle=?");
                query3.setParameter(0,activity.getActivityTitle());
                Activity activity1=(Activity) query3.uniqueResult();
                session.close();
                updateAttendNum(activity.getActivityStartUser().getUserId(),activity1.getActivityId());
                return true;
            }
        }catch (Exception e){
            session.close();
            return false;
        }

    }

    //参加人数变更
    public int updateAttendNum(int userId,int activityId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=new java.util.Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        Date date1=new Date(date.getTime());
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
            int asd=0;
        Query a1 = session.createQuery("from Blacklist where userId.userId=? and activityId.activityId=?");
        a1.setParameter(0,userId);
        a1.setParameter(1,activityId);
        List<Blacklist> blacklists=a1.list();
        System.out.println(blacklists.size());
        if (blacklists.size()==0) {
            System.out.println("------------------>aaa");
            try {
                Query query1 = session.createQuery("from Action where userId.userId=? and activityId.activityId=?");
                query1.setParameter(0, userId);
                query1.setParameter(1, activityId);
                Action action = (Action) query1.uniqueResult();
                if (action != null) {
                    Activity activity1 = session.get(Activity.class, activityId);
                    activity1.setActivityAttendNum(activity1.getActivityAttendNum() - 1);
                    session.update(activity1);
                    String sql1 = "DELETE FROM Everysignin WHERE userId=" + userId + " and activityId=" + activityId + "";
                    Query query2 = session.createSQLQuery(sql1);
                    query2.executeUpdate();
                    String sql = "DELETE FROM Action WHERE userId=" + userId + " and activityId=" + activityId + "";
                    Query query = session.createSQLQuery(sql);
                    query.executeUpdate();
                    asd = 1;
                } else {
                    Activity activity1 = session.get(Activity.class, activityId);
                    activity1.setActivityAttendNum(activity1.getActivityAttendNum() + 1);
                    session.update(activity1);
                    String sql = "insert into action(userId,activityId,signInTime)" +
                            "values(" + userId + "," + activityId + ",0)";
                    Query query = session.createSQLQuery(sql);
                    query.executeUpdate();
                    String b1="INSERT INTO everysignin(userId,activityId,signinDate,succeedSignin)" +
                            "VALUES("+userId+","+activityId+",'"+date1+"',1)";
                    Query ac = session.createSQLQuery(b1);
                    ac.executeUpdate();
                    asd = 2;
                }
                tx.commit();
                session.close();

            } catch (Exception e) {
                session.close();
                asd=0;
            }
        }else {
            asd=3;
        }

        return asd;

    }

    //活动签到天数变更
    public Boolean updateSigninTime(int userId,int activityId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());

        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            Query query1 = session.createQuery("from Everysignin where userId.userId=? and activityId.activityId=? and signinDate=?");
            query1.setParameter(0,userId);
            query1.setParameter(1,activityId);
            query1.setParameter(2,date);
            Everysignin everysignin=(Everysignin) query1.uniqueResult();
            if (everysignin !=null){

            }else {
                Query query = session.createQuery("from Action where userId.userId=? and activityId.activityId=?");
                query.setParameter(0,userId);
                query.setParameter(1,activityId);
                Action action=(Action) query.uniqueResult();
                action.setSignInTime(action.getSignInTime()+1);
                session.update(action);
                String sql="INSERT INTO everysignin(userId,activityId,signinDate,succeedSignin)" +
                        "VALUES("+userId+","+activityId+",'"+date+"',1)";
                Query query2 = session.createSQLQuery(sql);
                query2.executeUpdate();
            }

            tx.commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    //某用户对某活动发表的评论
    public Boolean userpulishDiscuss(String userName, String activityTitle, String discussStr){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        try {
            Query query = session.createQuery("from Action where userId.userName=? and activityId.activityTitle=?");
            query.setParameter(0,userName);
            query.setParameter(1,activityTitle);
            Action action=(Action) query.uniqueResult();
            String sql="insert into Discuss(actionId,discussIntroduce,discussImageUrl,discussPraise,discussTime)" +
                    "values ("+action.getActionId()+",'"+discussStr+"','"+null+"',"+
                    "0,'"+date+"')";
            Query query1 = session.createSQLQuery(sql);
            query1.executeUpdate();
            tx.commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    //判断用户是否点赞
    public Boolean userdoPraiseIs(int userId,int discussId){
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        Query query1 = session.createQuery("from Userdopraise where userId.userId=? and discussId.discussId=?");
        query1.setParameter(0,userId);
        query1.setParameter(1,discussId);
        Userdopraise userdopraise=(Userdopraise) query1.uniqueResult();
        if (userdopraise ==null){
            return false;
        }else {
            return true;
        }
    }

    //某用户对某活动发表的某条评论点赞数的变更
    public int discussPraiseUpdate(int userId,int discussId){
        int asd=0;
        Session session = sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        Query query1 = session.createQuery("from Userdopraise where userId.userId=? and discussId.discussId=?");
        query1.setParameter(0,userId);
        query1.setParameter(1,discussId);
        Userdopraise userdopraise=(Userdopraise) query1.uniqueResult();
        try{
            if (userdopraise ==null) {
                Query query = session.createQuery("from Discuss where discussId=?");
                query.setParameter(0, discussId);
                Discuss discuss = (Discuss) query.uniqueResult();
                discuss.setDiscussPraise(discuss.getDiscussPraise() + 1);
                session.update(discuss);
                String sql="INSERT INTO Userdopraise(userId,discussId,doPraise)" +
                        "VALUES("+userId+","+discussId+",2)";
                Query query2 = session.createSQLQuery(sql);
                query2.executeUpdate();
                asd=1;
            }else {
                Query query = session.createQuery("from Discuss where discussId=?");
                query.setParameter(0, discussId);
                Discuss discuss = (Discuss) query.uniqueResult();
                discuss.setDiscussPraise(discuss.getDiscussPraise() - 1);
                session.update(discuss);
                String sql="DELETE FROM Userdopraise WHERE userPraiseId="+userdopraise.getUserPraiseId()+"";
                Query query2 = session.createSQLQuery(sql);
                query2.executeUpdate();
                asd=2;
            }
            tx.commit();
            session.close();
            return asd;
        }catch (Exception e){
            session.close();
            return 0;
        }

    }

    //某用户参加的所有活动
    public List<Activity> selectAuserActivity(int userId){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Action where userId.userId=?");
        query.setParameter(0,userId);
        List<Action> actions=query.list();
        System.out.println("actions的长度"+actions.size());
        List<Activity> activities=new ArrayList<>();
        for (int i=0;i<actions.size();i++){
           activities.add(actions.get(i).getActivityId());
        }
        session.close();
        return activities;
    }

    //查找某活动,条件输入的信息在活动名中存在
    public List<Activity> findActivityByStr(String activityStr){
        List<Activity> activityList=new ArrayList<>();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Activity WHERE activityTitle LIKE '%"+activityStr+"%' and activityIsPrivate=?");
        query.setParameter(0,1);
        List<Activity> activities=query.list();
        System.out.println(activities.size());
        for (int i=0;i<activities.size();i++){
            Boolean res=date.before(activities.get(i).getActivityEndTime());
            if (res){
                activityList.add(activities.get(i));
            }
        }
        session.close();
        return activityList;
    }

    //查询对于某活动发表的所有评论
    public List<Dresult> selectAllDisscussByactivityId(int userId,int activityId){
        List<Dresult> discussList=new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query1 = session.createQuery("from Discuss where actionId.activityId.activityId=?");
        query1.setParameter(0,activityId);
        List<Discuss> discusses=query1.list();
        for (int i=0;i<discusses.size();i++){
            Dresult dresult=new Dresult();
            dresult.setDiscuss(discusses.get(i));
            Query query2 = session.createQuery("from Userdopraise where userId.userId=? and discussId.discussId=?");
            query2.setParameter(0,userId);
            query2.setParameter(1,discusses.get(i).getDiscussId());
            Userdopraise userdopraise=(Userdopraise) query2.uniqueResult();
            if (userdopraise==null){
                dresult.setIslike(0);
            }else {
                dresult.setIslike(1);
            }
            discussList.add(dresult);
        }
        session.close();
        return discussList;

    }

    //排行榜1（最热活动）
    public List<Activity> hotActivity(){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Activity where activityIsPrivate=? order by activityAttendNum desc ");
        query.setParameter(0,1);
        List<Activity> activitys = query.list();
        session.close();
        return activitys;
    }

    //排行榜2（签到数）
    public List<Action> getSigninmostUser(){
        List<Action> actionList=new ArrayList<>();
        List<User> userList=new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User ");
        List<User> users=query.list();
        for (int i=0;i<users.size();i++){
           // Query query2 = session.createQuery("FROM Action where userId.userId=?");
            //List<Action> asd=query2.list();
            Query query1 = session.createQuery("FROM Action where userId.userId=? order by signInTime desc ");
            query1.setParameter(0,users.get(i).getUserId());
            List<Action> actions=query1.list();
            if (actions.size()>0) {
                actionList.add(actions.get(0));
            }

        }

            Collections.sort(actionList, new Comparator<Action>() {
                @Override
                public int compare(Action o1, Action o2) {
                    if (o1.getSignInTime() > o2.getSignInTime()) {
                        return -1;
                    }
                    if (o1.getSignInTime() == o2.getSignInTime()) {
                        return 0;
                    }
                    return 1;
                }
            });

        session.close();
        return actionList;
    }

    //排行榜3（点赞数）
    public List<User> getPraisemostUser(){
        List<User> userList=new ArrayList<>();
        List<int[]> praiseSum=new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User ");
        List<User> users=query.list();
        for (int i=0;i<users.size();i++){
            int signSum=0;
            int[] asd=new int[2];
            Query query1 = session.createQuery("from Discuss where actionId.userId.userId=?");
            query1.setParameter(0,users.get(i).getUserId());
            List<Discuss> discusses=query1.list();
            if(discusses.size()>0) {
                asd[0] = users.get(i).getUserId();
                for (int a = 0; a < discusses.size(); a++) {
                    signSum = signSum + discusses.get(a).getDiscussPraise();
                }
                asd[1] = signSum;
                praiseSum.add(asd);
            }
        }
        Collections.sort(praiseSum, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1]>o2[1]){
                    return -1;
                }
                if (o1[1]==o2[1]){
                    return 0;
                }
                return 1;
            }
        });
        for (int b=0;b<praiseSum.size();b++){
            Query query2 = session.createQuery("from User where userId=?");
            query2.setParameter(0,praiseSum.get(b)[0]);
            User user=(User) query2.uniqueResult();
            userList.add(user);
        }
        session.close();
        return userList;
    }

    //用户发表的评论
    public List<Discuss> selectAllDiscussbyuserId(int userId){
        Session session = sessionFactory.openSession();
        Query query1 = session.createQuery("from Discuss where actionId.userId.userId=?");
        query1.setParameter(0,userId);
        List<Discuss> discusses=query1.list();
        System.out.println(discusses.size());
        session.close();
        return discusses;
    }

    //用户参加的活动、签到数
    public List<Action> selectAllActionbyuserId(int userId){
        Session session = sessionFactory.openSession();
        Query query1 = session.createQuery("from Action where userId.userId=?");
        query1.setParameter(0,userId);
        List<Action> actions=query1.list();
        session.close();
        return actions;
    }

    //用户淘汰
    public Boolean usergetoutAactivity(int userId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=new java.util.Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date=calendar.getTime();
        Date date1=new Date(date.getTime());
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from Everysignin where userId.userId=? and activityId.activityModel=? and signinDate=?");
        query.setParameter(0,userId);
        query.setParameter(1,1);
        query.setParameter(2,date1);
        List<Everysignin> everysignins=query.list();
        //查到的有惩罚模式的活动
        Query query1 = session.createQuery("from Action where userId.userId=? and activityId.activityModel=? ");
        query1.setParameter(0,userId);
        query1.setParameter(1,1);
        List<Action> actions=query1.list();
        for (int i=0;i<actions.size();i++){
            for (int a=0;a<everysignins.size();a++){
                if (actions.get(i).getActivityId().getActivityId().equals(everysignins.get(a).getActivityId().getActivityId())){
                    actions.remove(actions.get(i));
                }
            }
        }
        System.out.println(actions.size());
        try {
            for (int b=0;b<actions.size();b++){

                Activity activity1=session.get(Activity.class,actions.get(b).getActivityId().getActivityId());
                activity1.setActivityAttendNum(activity1.getActivityAttendNum()-1);
                session.update(activity1);
                Query query2 = session.createQuery("from Discuss where actionId.actionId=?");
                query2.setParameter(0,actions.get(b).getActionId());
                List<Discuss> discusses=query2.list();
                if (discusses.size()==0){
                    Transaction tx=session.beginTransaction();
                    String sql="DELETE FROM Action WHERE userId="+userId+" and activityId="+actions.get(b).getActivityId().getActivityId()+"";
                    Query query3 = session.createSQLQuery(sql);
                    query3.executeUpdate();
                    String a1="INSERT into blacklist(userId,activityId) VALUES("+userId+","+actions.get(b).getActivityId().getActivityId()+")";
                    Query b1= session.createSQLQuery(a1);
                    b1.executeUpdate();
                    tx.commit();
                }else {
                    for (int i=0;i<discusses.size();i++){
                        Query query4 = session.createQuery("from Userdopraise where discussId.discussId=?");
                        query4.setParameter(0,discusses.get(i).getDiscussId());
                       List<Userdopraise> userdopraises=query4.list();
                       if (userdopraises.size()==0){
                           Transaction tx=session.beginTransaction();
                           String sql1="DELETE FROM Discuss WHERE actionId="+actions.get(b).getActionId()+"";
                           Query query5 = session.createQuery(sql1);
                           query5.executeUpdate();
                           String sql="DELETE FROM Action WHERE userId="+userId+" and activityId="+actions.get(b).getActivityId().getActivityId()+"";
                           Query query3 = session.createSQLQuery(sql);
                           query3.executeUpdate();
                           String a1="INSERT into blacklist(userId,activityId) VALUES("+userId+","+actions.get(b).getActivityId().getActivityId()+")";
                           Query b1= session.createSQLQuery(a1);
                           b1.executeUpdate();
                           tx.commit();
                       }else {
                           Transaction tx=session.beginTransaction();
                           String sql2="DELETE from userdopraise where discussId="+discusses.get(i).getDiscussId()+"";
                           Query query6 = session.createSQLQuery(sql2);
                           query6.executeUpdate();
                           String sql1="DELETE FROM discuss WHERE actionId="+actions.get(b).getActionId()+"";
                           Query query5 = session.createSQLQuery(sql1);
                           query5.executeUpdate();
                           String sql="DELETE FROM Action WHERE userId="+userId+" and activityId="+actions.get(b).getActivityId().getActivityId()+"";
                           Query query3 = session.createSQLQuery(sql);
                           query3.executeUpdate();
                           String a1="INSERT into blacklist(userId,activityId) VALUES("+userId+","+actions.get(b).getActivityId().getActivityId()+")";
                           Query b1= session.createSQLQuery(a1);
                           b1.executeUpdate();
                           tx.commit();
                       }
                    }
                }

            }
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }



    }

}
