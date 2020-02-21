package cn.xyf.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取要下载的文件的绝对路径
        String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/a.png");
//        String realPath = "/Users/zhenglong/proj/java_demo/javaweb_demo/src/main/resources/a.png";
        System.out.println(realPath);
        // 2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("/")+1);
        // 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        resp.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
        // 4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        BufferedInputStream bufferedIn = new BufferedInputStream(in);
        // 5.创建数据缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
        // 6.通过response对象获取OutputStream流
        OutputStream out = resp.getOutputStream();
        // 7.将FileInputStream流写入到buffer缓冲区
        while((len=bufferedIn.read(buffer)) != -1) {
            // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer, 0, len);
        }
        // 9. 关闭资源
        out.close();
        bufferedIn.close();
        in.close();
    }
}
