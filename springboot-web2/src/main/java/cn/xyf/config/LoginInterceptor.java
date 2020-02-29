package cn.xyf.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if(session.getAttribute("session_id") != null) {
            return true;
        } else {
            request.setAttribute("msg", "没有权限，请先登录");
            request.getRequestDispatcher("/index").forward(request, response);
            return false;
        }
    }
}
