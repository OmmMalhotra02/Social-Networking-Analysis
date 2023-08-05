package com.socialnetworkanalysis.network;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.springframework.stereotype.Component;

// import io.micrometer.common.util.StringUtils;

public class Users {
    public int userId;
    public String fName;
    public String lName;
    @JsonIgnore
    public String userName;
    @JsonIgnore
    public String password;
    public Date dob;
    public long phoneNum;

    public Users(){
    }
    public String toString(){
        
        return (Integer.toString(userId)+" "+fName+" "+lName+" "+userName+" "+password+" "+dob+" "+Long.toString(phoneNum));
    }    

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getfName() {
        return fName;
    }
    public void setfName(String fName) {
        this.fName = fName;
    }
    public String getlName() {
        return lName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public long getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}

