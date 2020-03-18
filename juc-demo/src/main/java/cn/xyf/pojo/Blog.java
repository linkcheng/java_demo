package cn.xyf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private int id;
    private String title;
    private String content;
    private int uid;
}
