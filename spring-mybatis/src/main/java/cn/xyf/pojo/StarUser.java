package cn.xyf.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StarUser implements Serializable {
    private int id;
    private String name;
    private String pwd;

    public StarUser(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
