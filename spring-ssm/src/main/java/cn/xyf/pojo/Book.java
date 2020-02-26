package cn.xyf.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String bookName;
    private int bookCount;
    private String detail;
}