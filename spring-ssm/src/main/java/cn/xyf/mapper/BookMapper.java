package cn.xyf.mapper;

import cn.xyf.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    // 查询全部
    List<Book> select();

    // 通过 id 查询
    Book selectById(@Param("bid") int id);

    // 增加一本书
    int insert(Book book);

    // 删除一本书
    int delete(@Param("bid") int id);

    // 修改一本书
    int update(Book book);
}
