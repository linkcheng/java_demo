package cn.xyf.service;

import cn.xyf.pojo.Book;

import java.util.List;


public interface BookService {
    // 查询全部
    List<Book> select();

    // 通过 id 查询
    Book selectById(int id);

    // 增加一本书
    int insert(Book book);

    // 删除一本书
    int delete(int id);

    // 修改一本书
    int update(Book book);
}
