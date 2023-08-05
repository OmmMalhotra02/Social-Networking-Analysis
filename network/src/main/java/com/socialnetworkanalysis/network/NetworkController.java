package com.socialnetworkanalysis.network;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class NetworkController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private Graph graph;

    @RequestMapping("/recommendations/{uid}")
    public String social(@PathVariable Integer uid) throws JsonProcessingException
    {
        // String userdata = "Select * from Users";
        // List<Users> user = jdbcTemplate.query(userdata, BeanPropertyRowMapper.newInstance(Users.class));
        // user.forEach(System.out :: println);
        // return (user.toString());
        // friend.forEach(System.out :: println);
        // return (friend.toString());
        String recommendedData = "Select * from Users where UserId = ?";
        List<Integer> recommendedUserIds = graph.getRecommendations(uid);
        List<String> userInfo = new ArrayList<>();

        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        for(int i : recommendedUserIds)
        {
            Users user = jdbcTemplate.queryForObject(recommendedData, new UserRowMapper(), i);
            userInfo.add(objectMapper.writeValueAsString(user));
        }
        return userInfo.toString();
    }
}
