package cn.xyf.controller;

import cn.xyf.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBookController {
    private ApplicationContext context;
    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testGetAllBooks() {
        BookController bookController = context.getBean("bookController", BookController.class);
        System.out.println(bookController.getAllBooks());
    }
}
