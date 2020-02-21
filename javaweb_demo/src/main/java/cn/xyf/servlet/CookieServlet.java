package cn.xyf.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();

        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("lastLoginTime")) {
                    out.println(cookie.getName()+":"+new Date(Long.valueOf(cookie.getValue())).toLocaleString());
                } else {
                    out.println(cookie.getName()+":"+cookie.getValue());
                }
            }
        } else {
            out.println("This is the first visiting!");
        }

        Cookie cookie = new Cookie("lastLoginTime", System.currentTimeMillis()+"");
        resp.addCookie(cookie);

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
