package cn.xyf.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ContextPath="+req.getContextPath());
        resp.sendRedirect(req.getContextPath()+"/hello");
        // 等效为
//        resp.setHeader("Location", "/javaweb/hello");
//        resp.setStatus(HttpServletResponse.SC_FOUND);
    }
}
