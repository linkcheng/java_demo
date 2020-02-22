package cn.xyf.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CounterListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("sessionCreated："+session.getId());
        ServletContext servletContext = session.getServletContext();

        Integer count = (Integer) servletContext.getAttribute("count");
        count = count == null ? 1 : count+1;
        servletContext.setAttribute("count", count);
        System.out.println("当前session数量="+count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        System.out.println("sessionDestroyed："+session.getId());

        Integer count = (Integer) servletContext.getAttribute("count");
        count = count == null ? 0 : count-1;
        servletContext.setAttribute("count", count);
        System.out.println("当前session数量="+count);
    }
}
