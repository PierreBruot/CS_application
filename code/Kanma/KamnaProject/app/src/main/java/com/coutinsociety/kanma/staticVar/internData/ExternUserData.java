package com.coutinsociety.kanma.staticVar.internData;

import com.coutinsociety.kanma.data.User;

import java.util.ArrayList;

public class ExternUserData {

    private static ArrayList<User> usersInStock=new ArrayList<>();

    public static void add(User u){
        for (User user :
                usersInStock) {
            if (u.getId() == user.getId()){
                return;
            }
        }
        usersInStock.add(u);
    }

    public static User findUserById(int user) {
        for (User u :
                usersInStock) {
            if (u.getId() == user){
                return u;
            }
        }
        return null;
    }
}
