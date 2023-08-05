package com.socialnetworkanalysis.network;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Graph implements ApplicationListener < ApplicationReadyEvent >{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int vertices;
    private int[] [] adjacencyList;
    private boolean[] visited;
    private boolean[] possibleRecommends; 

    Map<Integer,Integer> userIdToIntMapping;
    Map<Integer,Integer> intToUserIdMapping;

    private void edge(int source, int destination)
    {
        adjacencyList[source][destination]=1;
        adjacencyList[destination][source]=1;
    }

    private void dfsTraverse(int initialV)
    {
        visited[initialV]=true;
        
        for(int i=0;i<vertices;i++)
        {
            if(adjacencyList[initialV][i]==1 && !visited[i])
            {
                possibleRecommends[i]= true;
                dfsTraverse(i);
            }
        }
    }

    private void futureFriend(int initialV)
    {
        for(int i=0;i<vertices;i++)
        {
            if(adjacencyList[initialV][i]==1 && possibleRecommends[i]==true)
            {
                possibleRecommends[i]=false;
            }
        }
    }

    private List<Integer> friendRecommendation()
    {
        List<Integer> recommend =new ArrayList<Integer>();
        for(int i=0;i<vertices;i++)
        {
            if(possibleRecommends[i])
            {
                recommend.add(intToUserIdMapping.get(i));
            }
        }
        return recommend;
    }

    /*private void printAdjacencyList()
    {
        for(int i=0;i<vertices;i++)
        {
            System.out.println("Vertices "+i+" is connected to : ");
            for(int j=0;j<vertices;j++)
            {
                if(adjacencyList[i][j]==1)
                {
                    System.out.println(j+" ");
                }
            }
            System.out.println();
        }
    }*/

    private int countUser()
    {
        String countFriends= "SELECT COUNT(*) from Users";
        Integer count = jdbcTemplate.queryForObject(countFriends, Integer.class);
        return count;
    }

    @Override 
    public void onApplicationEvent(final ApplicationReadyEvent event) 
    {
        
        System.out.println("Application started!");
        int v=countUser();
        vertices=v;
        adjacencyList = new int [v][v];
        visited = new boolean[v];
        possibleRecommends = new boolean [v];
        userIdToIntMapping=new HashMap<Integer,Integer>();
        intToUserIdMapping=new HashMap<Integer,Integer>();
        //how to write into a table using jdbc template
        String friendData = "Select * from Friends";
        List<Friends> friendList = jdbcTemplate.query(friendData, BeanPropertyRowMapper.newInstance(Friends.class));

        int i=0;
        for(Friends friend : friendList)
        {
            userIdToIntMapping.put(friend.User_Id,i);
            intToUserIdMapping.put(i,friend.User_Id);
            i++;
        }

        for(Friends friend : friendList)
        {
            if(friend.getFriend_Ids()==null)
            {
                continue;
            }
            if(friend.getFriend_Ids().length()==0)
            {
                continue;
            }
            if(friend.getFriend_Ids().length()==1)
            {
                edge(userIdToIntMapping.get(friend.User_Id),userIdToIntMapping.get(Integer.parseInt(friend.getFriend_Ids())));
            }
            for(int j=0;j<friend.separateId().length;j++)
            {
                edge(userIdToIntMapping.get(friend.User_Id),userIdToIntMapping.get(Integer.parseInt(friend.separateId()[j])));
            }
        }

        return;
    }

    public List<Integer> getRecommendations(int userid)
    {
        int index = userIdToIntMapping.get(userid);
        for(int i=0;i<vertices;i++)
        {
            visited[i]=false;
            possibleRecommends[i]=false;
        }
        dfsTraverse(index);
        futureFriend(index);
        return friendRecommendation();
    }

    private static String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 8;

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private static Date generateRandomDateOfBirth() {
        Random random = new Random();
        long minDate = Date.valueOf("1950-01-01").getTime();
        long maxDate = Date.valueOf("2005-12-31").getTime();
        long randomDate = minDate + random.nextLong() % (maxDate - minDate + 1);
        return new Date(randomDate);
    }

    private static long generateRandomPhoneNumber() {
        Random random = new Random();
        long phoneNumber = 0;
        for (int i = 0; i < 10; i++) {
            phoneNumber=phoneNumber*10+random.nextInt(10);
        }
        return phoneNumber;
    }

    public void generateUser()
    {
        int userLength=Math.min(name.firstNames.length, name.lastNames.length);
        for(int i=0;i<userLength;i++)
        {
            int userId=i+400;
            String firstName=name.firstNames[i];
            String lastName=name.lastNames[i];
            String userName=name.firstNames[i]+name.lastNames[i];
            String password=generateRandomPassword();
            Date dob=generateRandomDateOfBirth();
            long phoneNumber=generateRandomPhoneNumber();

            String sql="INSERT INTO Users VALUES(?,?,?,?,?,?,?)";

            jdbcTemplate.update(sql,userId,firstName,lastName,userName,password,dob,phoneNumber);
        }
    }

    public void generateEdges()
    {
        Map<Integer,List<Integer>> newFriends= new HashMap<>();
        Random randFriend=new Random();

        for(int i=400;i<=503;i++)
        {
            int numberOfFriends=randFriend.nextInt(5);
            
            for(int j=0;j<numberOfFriends;j++)
            {
                int friendId=randFriend.nextInt(104)+400;
                if(friendId==i)
                    continue;
                
                if(!newFriends.containsKey(i))
                {
                    newFriends.put(i,new ArrayList<Integer>());   
                }
                if(newFriends.get(i).size()>=5)
                    continue;
                if(!newFriends.get(i).contains(friendId))
                        newFriends.get(i).add(friendId);  
                
                if(!newFriends.containsKey(friendId))
                {
                    newFriends.put(friendId,new ArrayList<Integer>());   
                }
                if(!newFriends.get(friendId).contains(i))
                        newFriends.get(friendId).add(i);  
            }

        }

        for(int i=400;i<=503;i++)
        {
            List<Integer> friends= newFriends.get(i);
            String friendString="";
            if(friends!=null)
            {
                for(int j : friends)  
                {
                friendString+=(Integer.toString(j))+",";
                } 
            }    
            if(friendString.length()>0)
                friendString=friendString.substring(0, (friendString.length()-1));

            String sqlFriend="INSERT INTO Friends VALUES(?,?,NULL)";
            jdbcTemplate.update(sqlFriend,i,friendString);
        }
    }

}


