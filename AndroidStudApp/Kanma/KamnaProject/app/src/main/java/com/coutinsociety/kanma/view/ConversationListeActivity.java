package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Conversation;
import com.coutinsociety.kanma.view.modelViewControler.ConversationInList;

import static com.coutinsociety.kanma.staticVar.internData.ConversationData.conversations;

public class ConversationListeActivity extends AppCompatActivity {

    private LinearLayout conversationScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_liste);



        getUI();
        showConversations();


    }

    private void getUI() {
        this.conversationScroll=findViewById(R.id.converstionScroll);

    }


    private void showConversations() {
        //TODO useFactory here or in controller?
        if(conversations!=null){
            for (Conversation c:conversations) {
                ConversationInList conversationContainer = new ConversationInList(this, c);
                conversationContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openConversation(v.getId());
                    }
                });
                conversationScroll.addView(conversationContainer);
            }
        }else{
            findViewById(R.id.scrollView).setVisibility(View.GONE);
            findViewById(R.id.txtNoConversation).setVisibility(View.VISIBLE);
        }
    }


    //Start other activity
    private void openConversation(int id) {
        Intent myIntent = new Intent(this,
                ConversationActivity.class);
        myIntent.putExtra("Conversation", id);
        startActivity(myIntent);
    }

    public void startNewConversation(View view) {
        Intent myIntent = new Intent(this,
                NewConversationActivity.class);

        startActivity(myIntent);
    }

    public void startMainPage(View view) {
        this.finish();
    }
}
