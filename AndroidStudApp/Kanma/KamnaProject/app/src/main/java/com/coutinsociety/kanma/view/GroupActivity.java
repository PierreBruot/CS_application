package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Group;
import com.coutinsociety.kanma.staticVar.internData.ConversationData;
import com.coutinsociety.kanma.staticVar.internData.GroupData;
import com.coutinsociety.kanma.staticVar.internData.UserData;

public class GroupActivity extends AppCompatActivity {

    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        group = GroupData.findGrouById(getIntent().getIntExtra("Group",-1));
        if (group == null) Toast.makeText(this,"erreur lors du chargement",Toast.LENGTH_SHORT).show();
        else {
       // Log.d("RecyclerView","onCreate");
        ((TextView)findViewById(R.id.ConsultGroupName)).setText(group.getTitle());
        ((TextView)findViewById(R.id.ConsultGroupDesc)).setText(group.getDescription());
        if(group.getPicture()!=null){
            ((ImageView)findViewById(R.id.ConsultGroupLogo)).setImageBitmap(group.getPicture());
        }
        else {
            ((ImageView)findViewById(R.id.ConsultGroupLogo)).setImageResource(R.drawable.ic_user);
        }

        updateUserRightOnGroup();
        }
    }

    private void updateUserRightOnGroup() {
        if(UserData.isRegistrer()){

        if(UserData.isMemberOfGroup(group.getId())){

            ((Button)findViewById(R.id.joinBtn)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.leaveBtn)).setVisibility(View.VISIBLE);

            if(UserData.isAdminOfGroup(group.getId())){
                ((Button)findViewById(R.id.btnSetDesc)).setVisibility(View.VISIBLE);
            }
        }
        else{

            ((Button)findViewById(R.id.leaveBtn)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btnSetDesc)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.joinBtn)).setVisibility(View.VISIBLE);
        }
        }
    }


    public void startMemberList(View view) {
//TODO
    }

    public void startMainPage(View view) {

        //TODO
        this.finish();
    }

    public void startCalendar(View view) {
//TODO
    }

    public void sendJoinRequest(View view) {
//TODO
    }

    public void sendLeaveRequest(View view) {
//TODO
    }


    public void editDesc(View view) {
//TODO
    }

    @Override
    protected void onDestroy() {
        GroupData.remove(group);
        super.onDestroy();
    }
}
