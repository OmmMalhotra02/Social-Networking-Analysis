package com.socialnetworkanalysis.network;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<Users>{
    

    @Override
    public Users mapRow(ResultSet rs, int rowNum)
    throws SQLException {
        Users user = new Users();

        user.setUserId(rs.getInt("UserId"));
        user.setfName(rs.getString("FName"));
        user.setlName(rs.getString("LName"));
        user.setUserName(rs.getString("UserName"));
        user.setPassword(rs.getString("Password"));
        user.setDob(rs.getDate("DOB"));
        user.setPhoneNum(rs.getInt("PhoneNum"));

        return user;
    }
}
