package webback.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webback.bean.*;
import webback.service.ActivityService;
import webback.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/User")
public class UserController {
    public static Gson gson = new Gson();

    @Autowired
    private UserService userService;
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    private ActivityService activityService;

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RequestMapping(value = "/showUsers.do")
    public String showUsers(Model model, HttpServletRequest request){
        ArrayList<User> users = (ArrayList<User>) userService.findUsers();
        model.addAttribute("users",users);
        return "/Back/users";
    }

    @RequestMapping(value = "/showUserAndActivities.do")
    public String showUserAndActivities(Model model, HttpServletRequest request){
        String userid = request.getParameter("userId");
        int userId = Integer.parseInt(userid);
        //传递该用户ID用于显示
        model.addAttribute("userId",userId);
        ArrayList<Action> actions = (ArrayList<Action>) userService.findActionsByUserId(userId);
        model.addAttribute("actions",actions);
        return "/Back/showactions";
    }

    @RequestMapping(value = "/showUserAndAttention.do")
    public String showUserAndAttention(Model model,HttpServletRequest request){
        String userid = request.getParameter("userId");
        int userId = Integer.parseInt(userid);
        ArrayList<Attention> attentions = (ArrayList<Attention>) userService.findAttentionsByUserId(userId);
        model.addAttribute("attentions",attentions);
        return "/Back/showattentions";
    }


    @RequestMapping(value = "/showDiscussByActionId.do")
    public String showDiscussByActionId(Model model, HttpServletRequest request){
        String actionid = request.getParameter("actionId");
        int actionId = Integer.parseInt(actionid);
        ArrayList<Discuss> discusses = (ArrayList<Discuss>)userService.findDiscussesByActionId(actionId);
        model.addAttribute("discusses",discusses);
        return "/Back/showdiscussbyactionid";
    }

    //用户注册
    @RequestMapping(value = "/userregister.do",method = RequestMethod.POST)
    public void registerUser(Model model,HttpServletRequest request,HttpServletResponse response)throws IOException{
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
        User newuser=gson.fromJson(stringBuffer.toString(),User.class);
        Boolean aBoolean=userService.userregister(newuser);
        if (aBoolean){
            String result = gson.toJson(true);
            response.getWriter().append(result);
        }else {
            String result = gson.toJson(false);
            response.getWriter().append(result);
        }
    }


    //用户登录
    @RequestMapping(value = "/userlogin.do",method = RequestMethod.POST)
    public void loginUser(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        User user=gson.fromJson(stringBuffer.toString(),User.class);
        String usernum=user.getUserNum();
        String userpassword=user.getUserPassword();
        User user1=userService.userLogin(usernum,userpassword);
        Result result=new Result();
        if (user1!=null){
            result.setCode(1);
            result.setUser(user1);
            String str1=gson.toJson(result);
            response.getWriter().append(str1);
        }else {
            result.setCode(0);
            String str2 = gson.toJson(result);
            response.getWriter().append(str2);
        }
    }

    //用户修改信息（头像、简介）
    @RequestMapping(value = "/updateusermsg.do",method = RequestMethod.POST)
    public void updateusermsg(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        User newuser=gson.fromJson(stringBuffer.toString(),User.class);
        Boolean aBoolean=userService.updateusermsg(newuser);
        if (aBoolean){
            String result = gson.toJson(true);
            response.getWriter().append(result);
        }else {
            String result = gson.toJson(false);
            response.getWriter().append(result);
        }
    }

    //用户关注其他用户,以及再次点击取消关注,关注1，取消2
    @RequestMapping(value = "/userAttendioninsert.do",method = RequestMethod.POST)
    public void userAttendioninsert(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream is = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String str = null;
        while ((str = reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        Gson gson = new Gson();
        int[] asd = gson.fromJson(stringBuffer.toString(), int[].class);
        int aq = userService.userAttentioninsert(asd);
        String result = gson.toJson(aq);
        response.getWriter().append(result);
    }

    //某用户关注的所有用户
    @RequestMapping(value = "/userAllAttention.do",method = RequestMethod.POST)
    public void userAllAttention(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        List<User> users=userService.userAllAttention(userId);
        Gson gson1=new Gson();
        String result=gson1.toJson(users);
        response.getWriter().append(result);
    }

    //通过电话查询用户
    @RequestMapping(value = "/selectUserbyPhone.do",method = RequestMethod.POST)
    public void selectUserbyPhone(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        String phone=gson.fromJson(stringBuffer.toString(),String.class);
        User user=userService.selectUserbyPhone(phone);
        Gson gson1=new Gson();
        String result=gson1.toJson(user);
        response.getWriter().append(result);
    }

    //通过学号查询用户
    @RequestMapping(value = "/selectUserbyuserNum.do",method = RequestMethod.POST)
    public void selectUserbyuserNum(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        String userNum=gson.fromJson(stringBuffer.toString(),String.class);
        User user=userService.selectUserbyuserNum(userNum);
        Gson gson1=new Gson();
        String result=gson1.toJson(user);
        response.getWriter().append(result);
    }


    //参加某活动的用户名
    @RequestMapping(value = "/selectAlluserNamebyactivityId.do",method = RequestMethod.POST)
    public void selectAlluserNamebyactivityId(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        int activityId=gson.fromJson(stringBuffer.toString(),int.class);
        List<String> userNamelist=userService.selectAlluserNamebyactivityId(activityId);
        Gson gson1=new Gson();
        String result=gson1.toJson(userNamelist);
        response.getWriter().append(result);
    }


    //根据用户Id回去用户
    @RequestMapping(value = "/selectAUserbyuserId.do",method = RequestMethod.POST)
    public void selectAUserbyuserId(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        User user=userService.selectAUserbyuserId(userId);
        Gson gson1=new Gson();
        String result=gson1.toJson(user);
        response.getWriter().append(result);
    }

    //判断用户是否参加了该活动
    @RequestMapping(value = "/userAttendactivityis.do",method = RequestMethod.POST)
    public void userAttendactivityis(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        Boolean aq=userService.userAttendactivityis(userId,activityId);
        if (aq){
            String result = gson.toJson(true);
            response.getWriter().append(result);
        }else {
            String result = gson.toJson(false);
            response.getWriter().append(result);
        }
    }

    //判断用户今天是否签到
    @RequestMapping(value = "/isSigninToday.do",method = RequestMethod.POST)
    public void isSigninToday(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
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
        Boolean aq=userService.isSigninToday(userId,activityId);
        if (aq){
            String result = gson.toJson(true);
            response.getWriter().append(result);
        }else {
            String result = gson.toJson(false);
            response.getWriter().append(result);
        }
    }




}
