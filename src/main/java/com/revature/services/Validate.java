package com.revature.services;

import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.revature.util.ConnectionUtil.getConnectionUtil;

public class Validate {

    static ConnectionUtil cu = getConnectionUtil();

    public static boolean checkUser(String email, String password)
    {
        boolean st = false;
        try ( Connection con = cu.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from user where email=? and password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            st = rs.next();
        }
        catch(SQLException e) {
            e.printStackTrace();
            }
            return st;
    }
}
