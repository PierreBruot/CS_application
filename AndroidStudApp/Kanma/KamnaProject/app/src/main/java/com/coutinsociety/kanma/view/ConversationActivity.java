package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Conversation;
import com.coutinsociety.kanma.staticVar.internData.ConversationData;
import com.coutinsociety.kanma.view.modelViewControler.MessageListAdapter;

public class ConversationActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    private Conversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        conversation = ConversationData.findConversationById(getIntent().getIntExtra("Conversation",-1));
        if (conversation == null) Toast.makeText(this, R.string.loading_error,Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Log.d("RecyclerView","onCreate");

        mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerview_message_list);

        mMessageAdapter = new MessageListAdapter(this, conversation);

        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        mMessageRecycler.setAdapter(mMessageAdapter);

    }

    public void onClickSettings(View view) {

    }

    public void onClickBack(View view) {
        this.finish();

    }

    public void onClickSent(View view) {

        String texteMessage =((EditText)findViewById(R.id.editText)).getText().toString();
        if (!texteMessage.isEmpty()){
            //TODO
            /*
            if(MessageFactory.sendNewMessage(conversation.getId(),texteMessage)){
                mMessageAdapter.notifyItemInserted(conversation.getAllMessage().size() - 1);
            }else
                Toast.makeText(this,"Impossible d'envoyer le message",Toast.LENGTH_LONG).show();

             */
        }

    }
}
