package cn.xyf.service;

import cn.xyf.mapper.BookMapper;
import cn.xyf.pojo.Book;

import java.util.List;

public class BookSericeImpl implements BookService {
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> select() {
        return bookMapper.select();
    }

    @Override
    public Book selectById(int id) {
        return bookMapper.selectById(id);
    }

    @Override
    public int insert(Book book) {
        return bookMapper.insert(book);
    }

    @Override
    public int delete(int id) {
        return bookMapper.delete(id);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }
}
