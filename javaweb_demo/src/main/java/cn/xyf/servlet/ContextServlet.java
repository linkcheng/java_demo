package cn.xyf.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取 ServletContext 的值
        ServletContext context = this.getServletContext();
        String key = (String) context.getAttribute("key");
        resp.getWriter().println("key:"+key);

        // 获取配置参数
        String url = context.getInitParameter("url");
        resp.getWriter().println("url:"+url);
    }
}
