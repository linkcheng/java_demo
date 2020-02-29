package cn.xyf.service;

import cn.xyf.pojo.Employee;

import java.util.Collection;


public interface EmployeeService {
    // 查询全部
    Collection<Employee> select();

    // 通过 id 查询
    Employee selectById(int id);

    // 增加
    int insert(Employee employee);

    // 删除
    int delete(int id);

    // 修改
    int update(Employee employee);
}
