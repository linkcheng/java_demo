package cn.xyf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Hello2 {
    private String name;

    public String getName() {
        return name;
    }

    @Value("spring")
    public void setName(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println("Hello2, "+ name );
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}