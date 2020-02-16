package com.coutinsociety.kanma.view.modelViewControler;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Entity;


//TODO
public class EntityInListForConversation extends CoordinatorLayout {
    private ImageView img;
    public EntityInListForConversation(Context context,Entity e) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,70));

        this.addView(img=new ImageView(context));
        if(e.getPicture()!=null)
        img.setImageBitmap(e.getPicture());
        else img.setImageResource(R.drawable.ic_user);
        img.setLayoutParams(new LayoutParams(70,70));
        //img.getLayoutParams().gravity= Gravity.START;


    }
}
