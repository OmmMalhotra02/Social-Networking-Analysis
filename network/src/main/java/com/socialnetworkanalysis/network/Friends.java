package com.socialnetworkanalysis.network;

// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Component;

// import io.micrometer.common.util.StringUtils;

public class Friends {

    public Friends(){}

    public int User_Id;
    public String Friend_Ids;
    //public String Friend_Names;

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public String getFriend_Ids() {
        return Friend_Ids;
    }

    public void setFriend_Ids(String friend_Ids) {
        Friend_Ids = friend_Ids;
    }

    /*public String getFriend_Names() {
        return Friend_Names;
    }

    public void setFriend_Names(String friend_Names) {
        Friend_Names = friend_Names;
    }*/


    public String toString(){
        return (Integer.toString(User_Id)+" "+Friend_Ids);
    }

    public String [] separateId()
    {
        return Friend_Ids.split("[,]");
    }

}

