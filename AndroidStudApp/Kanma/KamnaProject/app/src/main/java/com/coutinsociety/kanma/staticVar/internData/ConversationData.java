package com.coutinsociety.kanma.staticVar.internData;

import com.coutinsociety.kanma.data.Conversation;
import com.coutinsociety.kanma.data.Message;
import com.coutinsociety.kanma.data.User;

import java.sql.Date;
import java.util.ArrayList;

public class ConversationData {

    public static ArrayList<Conversation> conversations=new ArrayList<Conversation>();
    public static ArrayList<User> users=new ArrayList<>();
    private static ArrayList<Message> messages=new ArrayList<>();

    public static void initialenizeData(){
/*
        users.add(currentUser);
        User u;
        users.add((u=new User(2,"TITITOTO","tata","Tete",null,null)));

        messages.add(new Message(1,"Bonjour !",new Date(2323223232L),currentUser));
        messages.add(new Message(2,"Salut toi :)",new Date(2323223232L),u));
        conversations.add(new Conversation(1,"Conversation",users,messages));

 */
    }






//TODO
    public static Conversation findConversationById(int conversationID) {

        for (Conversation conversation:conversations
             ) {
            if (conversation.getId()==conversationID){
                return conversation;
            }
        }
        return null;
    }
}
