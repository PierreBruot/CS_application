package com.coutinsociety.kanma.view.modelViewControler;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Conversation;


public class ConversationInList extends CoordinatorLayout {


    public ConversationInList(Context context, Conversation conversation){
        super(context);
        int id = conversation.getId();
        Bitmap profilPicture = conversation.getPicture();
        String title = conversation.getTitre();
        String lastMessageHour = conversation.getLastMessageHour();


        this.setId(id);

        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, context.getResources().getDisplayMetrics())));

        ImageView profilPictureView= new ImageView(context);
        CoordinatorLayout.LayoutParams profilPictureViewParams=new CoordinatorLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, context.getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, context.getResources().getDisplayMetrics()));
        ((LayoutParams) profilPictureViewParams).gravity= Gravity.START;
        profilPictureView.setLayoutParams(profilPictureViewParams);
        if (profilPicture!=null)
        profilPictureView.setImageBitmap(profilPicture);
        else profilPictureView.setImageResource(R.drawable.ic_user);

        TextView txtTilte=new TextView(context);
        txtTilte.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((LayoutParams)txtTilte.getLayoutParams()).gravity=Gravity.CENTER;
        txtTilte.setTextSize(20);
        txtTilte.setText(title);

        TextView txtTime=new TextView(context);
        txtTime.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((LayoutParams)txtTime.getLayoutParams()).gravity=Gravity.END|Gravity.CENTER_VERTICAL;
        txtTime.setTextSize(20);
        txtTime.setText(lastMessageHour);

        this.addView(profilPictureView);
        this.addView(txtTilte);
        this.addView(txtTime);

    }

}
