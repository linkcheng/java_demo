package cn.xyf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarUser {
    private int id;
    private String name;
    private String pwd;
}
