package webback.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webback.bean.*;
import webback.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RequestMapping(value = "/showActivitys.do")
    public String showUsers(Model model, HttpServletRequest request){
        ArrayList<Activity> activities = (ArrayList<Activity>) activityService.findActivities();
        model.addAttribute("activities",activities);
        return "/Back/showactivities";
    }

    @RequestMapping(value = "/insertnewoffcialActivity.do")
    public String insertnewoffcialActivity(Model model, HttpServletRequest request,String activityTitle,String activityImgUrl,String activityIntroduce,String userId,String activityUserNum,
                                           String activityStartTime,String activityEndTime,String punishId){
        System.out.println("到达");
        System.out.println(activityTitle);
        System.out.println(activityStartTime);
        System.out.println(userId);
        return "";
    }


    ////////////////////////////////
    //展示所有活动
    @RequestMapping(value = "/appshowActivitys.do")
    public void appshowActivity(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ArrayList<Activity> activities = (ArrayList<Activity>) activityService.findActivities();
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String activityStr=gson.toJson(activities);
        response.getWriter().append(activityStr);
    }

    //用户发起活动
    @RequestMapping(value = "/userinsertActivitys.do",method = RequestMethod.POST)
    public void userInsertActivity(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        System.out.println(stringBuffer.toString());
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        Activity activity=gson.fromJson(stringBuffer.toString(),Activity.class);
        Boolean aBoolean=activityService.insertnewActivity(activity);
        if (aBoolean){
            String result = gson.toJson(true);
            response.getWriter().append(result);
        }else {
            String result = gson.toJson(false);
            response.getWriter().append(result);
        }

    }

    //变更活动参加人数，每请求一次参加人数+1,以及记录
    @RequestMapping(value = "/updateAttendNum.do",method = RequestMethod.POST)
    public void updateAttendNum(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int []asd=gson.fromJson(stringBuffer.toString(),int[].class);
        int userId=asd[0];
        int activityId=asd[1];
        int result=activityService.updateAttendNum(userId,activityId);
        String a = gson.toJson(result);
        response.getWriter().append(a);

    }


    //某用户某活动签到天数变更,并且将哪天签到的情况
    @RequestMapping(value = "/updateSigninTime.do",method = RequestMethod.POST)
    public void updateSigninTime(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        System.out.println("到达");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int []asd=gson.fromJson(stringBuffer.toString(),int[].class);
        int userId=asd[0];
        int activityId=asd[1];
        Boolean ab=activityService.updateSigninTime(userId,activityId);
        if (ab){
            String a = gson.toJson(true);
            response.getWriter().append(a);
        }else {
            String a = gson.toJson(false);
            response.getWriter().append(a);
        }
    }

    //某用户对某活动发表的评论
    @RequestMapping(value = "/userpublishDiscuss.do",method = RequestMethod.POST)
    public void userpublishDisuss(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        System.out.println("到达");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        String[] asd=gson.fromJson(stringBuffer.toString(),String[].class);
        String userName=asd[0];
        String activityTitle=asd[1];
        String discussStr=asd[2];
        Boolean ab=activityService.userpulishDiscuss(userName,activityTitle,discussStr);
        if (ab){
            String a = gson.toJson(true);
            response.getWriter().append(a);
        }else {
            String a = gson.toJson(false);
            response.getWriter().append(a);
        }
    }

    //判断用户是否点赞，点赞true，没点false
    @RequestMapping(value = "/userdoPraiseIs.do",method = RequestMethod.POST)
    public void userdoPraiseIs(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int[] asd=gson.fromJson(stringBuffer.toString(),int[].class);
        int userId=asd[0];
        int discussId=asd[1];
        Boolean ab=activityService.userdoPraiseIs(userId,discussId);
        if (ab){
            String a = gson.toJson(true);
            response.getWriter().append(a);
        }else {
            String a = gson.toJson(false);
            response.getWriter().append(a);
        }
    }

    //某用户对某活动发表的某条评论点赞数的变更,点赞返回1，取消返回2
    @RequestMapping(value = "/discusspraiseUpdate.do",method = RequestMethod.POST)
    public void discusspraiseUpdate(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int[] asd=gson.fromJson(stringBuffer.toString(),int[].class);
        int userId=asd[0];
        int discussId=asd[1];
        int aq=activityService.discussPraiseUpdate(userId,discussId);
        String a = gson.toJson(aq);
        response.getWriter().append(a);

    }

    //某用户参加的所有活动
    @RequestMapping(value = "/selectAuserActivity.do",method = RequestMethod.POST)
    public void selectAuserActivity(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println("AAAAAAAAAAAAAAAAA");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        int userId=gson.fromJson(stringBuffer.toString(),int.class);
        List<Activity> activities=activityService.selectAuserActivity(userId);
        String result=gson.toJson(activities);
        response.getWriter().append(result);

    }

    //查找某活动,条件输入的信息在活动名中存在
    @RequestMapping(value = "/findActivityByStr.do",method = RequestMethod.POST)
    public void findActivityByStr(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        String activityStr=gson.fromJson(stringBuffer.toString(),String.class);
        System.out.println(activityStr);
        List<Activity> activities=activityService.findActivityByStr(activityStr);
        Gson gson1=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String result=gson1.toJson(activities);
        response.getWriter().append(result);
    }

    //查询对于某活动发表的所有评论
    @RequestMapping(value = "/selectAllDisscussByactivityId.do",method = RequestMethod.POST)
    public void selectAllDisscussByactivityId(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int[] asd=gson.fromJson(stringBuffer.toString(),int[].class);
        int userId=asd[0];
        int activityId=asd[1];
        List<Dresult> discusses=activityService.selectAllDisscussByactivityId(userId,activityId);
        Gson gson1=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String result=gson1.toJson(discusses);
        response.getWriter().append(result);
    }

    //排行榜（热门活动）
    @RequestMapping(value = "/hotActivity.do")
    public void hotActivity(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ArrayList<Activity> activities = (ArrayList<Activity>) activityService.hotActivity();
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        System.out.println(activities.get(0).getActivityTitle());
        String activityStr=gson.toJson(activities);
        response.getWriter().append(activityStr);
    }

    //排行榜（签到）
    @RequestMapping(value = "/getSigninmostUser.do")
    public void getSigninmostUser(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println("到达");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ArrayList<Action> actions = (ArrayList<Action>) activityService.getSigninmostUser();
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String activityStr=gson.toJson(actions);
        response.getWriter().append(activityStr);
    }

    //排行榜（点赞）
    @RequestMapping(value = "/getPraisemostUser.do")
    public void getPraisemostUser(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println("到达");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ArrayList<User> users = (ArrayList<User>) activityService.getPraisemostUser();
        Gson gson=new Gson();
        String activityStr=gson.toJson(users);
        response.getWriter().append(activityStr);
    }

    //查询某用户发表的所有评论
    @RequestMapping(value = "/selectAllDiscussbyuserId.do",method = RequestMethod.POST)
    public void selectAllDiscussbyuserId(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int userId=gson.fromJson(stringBuffer.toString(),int.class);
        List<Discuss> discusses=activityService.selectAllDiscussbyuserId(userId);
        Gson gson1=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String result=gson1.toJson(discusses);
        response.getWriter().append(result);
    }

    //某用户发布的所有活动
    @RequestMapping(value = "/selectActivitiesByUserId.do",method = RequestMethod.POST)
    public void selectActivitiesByUserId(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int userId=gson.fromJson(stringBuffer.toString(),int.class);
        List<Activity> activities=activityService.selectActivitiesByUserId(userId);
        Gson gson1=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String result=gson1.toJson(activities);
        response.getWriter().append(result);
    }

    //某用户发布的所有活动
    @RequestMapping(value = "/selectAllActionbyuserId.do",method = RequestMethod.POST)
    public void selectAllActionbyuserId(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int userId=gson.fromJson(stringBuffer.toString(),int.class);
        List<Action> actions=activityService.selectAllActionbyuserId(userId);
        Gson gson1=new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
        String result=gson1.toJson(actions);
        response.getWriter().append(result);
    }


    //用户淘汰
    @RequestMapping(value = "/usergetoutAactivity.do",method = RequestMethod.POST)
    public void usergetoutAactivity(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuffer stringBuffer=new StringBuffer();
        String str=null;
        while((str=reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson=new Gson();
        int userId=gson.fromJson(stringBuffer.toString(),int.class);
        Boolean qw=activityService.usergetoutAactivity(userId);
        Gson gson1=new Gson();
        if (qw){
            String a = gson1.toJson(true);
            response.getWriter().append(a);
        }else {
            String a = gson1.toJson(false);
            response.getWriter().append(a);
        }
    }

}
