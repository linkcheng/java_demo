package cn.xyf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Node2 {

    @Value("123")
    private int data;

    private String name;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    @Value("root")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", name=" + name +
                '}';
    }
}

