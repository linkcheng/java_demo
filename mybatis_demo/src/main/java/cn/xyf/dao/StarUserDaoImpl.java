package cn.xyf.dao;

import cn.xyf.pojo.StarUser;
import cn.xyf.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class StarUserDaoImpl implements StarUserDao {

    public void insert() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "insert into StarUser (name, mobile, email, created_time) values (?, ?, ?, ?)";

        try {
            conn = JdbcUtils.getConnection();
            st = conn.prepareStatement(sql);

            st.setString(1, "hello");
            st.setString(2, "123456");
            st.setString(3, "123456@qq.com");
            st.setDate(4, new java.sql.Date(new Date().getTime()));

            int i = st.executeUpdate();
            System.out.println(i+"==========");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, conn);
        }
    }

    public List<StarUser> selectUserList() {
        String sql = "select * from mybatis.StarUser";
        return null;
    }
}
