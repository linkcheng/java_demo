package cn.xyf.controller;

import cn.xyf.pojo.Book;
import cn.xyf.service.BookService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public String getAllBooks() {
        List<Book> books = bookService.select();
        return JSON.toJSONString(books);
    }

    @GetMapping("/{bookId}")
    public String getBook(@PathVariable int bookId) {
        Book book = bookService.selectById(bookId);
        return JSON.toJSONString(book); }
}
