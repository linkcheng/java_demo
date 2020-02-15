package cn.xyf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarUser {
    private int id;
    private String name;
    private String pwd;
    private List<Blog> blogs;

    public StarUser(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
