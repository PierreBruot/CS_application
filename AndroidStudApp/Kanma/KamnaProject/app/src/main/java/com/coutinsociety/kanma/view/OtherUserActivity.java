package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.User;
import com.coutinsociety.kanma.staticVar.internData.ExternUserData;

public class OtherUserActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        user = ExternUserData.findUserById(getIntent().getIntExtra("User",-1));
        if (user == null) Toast.makeText(this,"erreur lors du chargement",Toast.LENGTH_SHORT).show();
        else {
            onCreateUser();
        }

    }

    //Page Profils
    private void onCreateUser() {

        if(user.getPicture()!=null)
            ((ImageView)findViewById(R.id.profilPicture)).setImageBitmap(user.getPicture());
        else
            ((ImageView)findViewById(R.id.profilPicture)).setImageResource(R.drawable.ic_user);

        //TODO String have to be const
        ((TextView)findViewById(R.id.genreTxt)).setText(user.getGenre()?"Homme":"Femme");
        ((TextView)findViewById(R.id.ageTxt)).setText("age a faire");
        updateUserInfo();
    }

    private void updateUserInfo() {
        ((TextView)findViewById(R.id.titleTxt)).setText(user.getTitle());
        ((TextView)findViewById(R.id.descriptionTxt)).setText(user.getDescription());
    }

    public void startMainPage(View view) {
        this.finish();
    }
}
