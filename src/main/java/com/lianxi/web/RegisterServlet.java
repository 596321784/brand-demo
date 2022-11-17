package com.lianxi.web; /**
 * @author fq
 * @create 2022-11-16 16:38
 */

import com.lianxi.pojo.Brand;
import com.lianxi.pojo.User;
import com.lianxi.service.BrandService;
import com.lianxi.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService userService= new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收数据
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        if ("".equals(userName)||"".equals(password)||userName==null||password==null){
            request.setAttribute("register_msg","用户名、密码不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            return;
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        //验证码判断
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
        if (! checkCodeGen.equalsIgnoreCase(checkCode)){
            request.setAttribute("register_msg","验证码错误");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            return;
        }
        //注册
        boolean flag = userService.register(user);
        if (flag){
            request.setAttribute("register_msg","注册成功,请登录");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }else {
            request.setAttribute("register_msg","用户名已存在");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
