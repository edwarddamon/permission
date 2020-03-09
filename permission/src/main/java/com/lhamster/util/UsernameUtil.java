package com.lhamster.util;

public class UsernameUtil {
    public static String name;
    public static ThreadLocal<String> user = new ThreadLocal<>();

    public static void setUserName(String username){
        user.set(username);
        name=username;
    }

    public static String getUserName(){
        if(user.get()==null){
            user.set(name);
        }
        return user.get();
    }
}
