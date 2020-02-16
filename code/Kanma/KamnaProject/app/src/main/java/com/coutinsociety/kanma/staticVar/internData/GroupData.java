package com.coutinsociety.kanma.staticVar.internData;

import com.coutinsociety.kanma.data.Group;

import java.util.ArrayList;

public class GroupData {

    private static ArrayList<Group> groups=new ArrayList<>();

    public static Group findGrouById(int idGroup) {
        for (Group group:groups) {
            if(group.getId()==idGroup)return group;
        }
        return null;
    }

    public static void add(Group g) {
        groups.add(g);
    }

    public static void remove(Group group) {
        groups.remove(group);
    }
}
