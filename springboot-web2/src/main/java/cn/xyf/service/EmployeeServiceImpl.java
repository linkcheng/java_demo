package cn.xyf.service;

import cn.xyf.dao.EmployeeDao;
import cn.xyf.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Collection<Employee> select() {
        return employeeDao.getAll();
    }

    @Override
    public Employee selectById(int id) {
        return employeeDao.get(id);
    }

    @Override
    public int insert(Employee employee) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(Employee employee) {
        return 0;
    }
}
