package com.lianxi.web; /**
 * @author fq
 * @create 2022-11-17 13:55
 */

import com.lianxi.pojo.User;
import com.lianxi.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService= new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        User user = userService.login(username, password);
        if (user != null) {
            if ("1".equals(remember)){
                //已勾选remember,将cookie发送到客户端
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                c_username.setMaxAge(60*60*24*7);
                c_password.setMaxAge(60*60*24*7);
                response.addCookie(c_username);
                response.addCookie(c_password);
            }
            //登陆成功,将user对象存储到session中，跳转查询所有
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/selectAllServlet");
        }else {
            //登录失败,储存错误信息到req域,跳转到login.jsp
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
