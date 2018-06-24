package webback.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream is = request.getInputStream();

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date = df.format(new Date());
        String imageUrl = date+".jpg";
        System.out.println("path:"+imageUrl);

        try {
            //TODO 修改成相对路径
            File file = new File(getServletContext().getRealPath("Back/images/")+imageUrl);
            System.out.println(file.getPath());
            FileOutputStream fos = new FileOutputStream(file);

            int len;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer))!=-1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            response.getWriter().append("/Back/images/"+imageUrl
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
