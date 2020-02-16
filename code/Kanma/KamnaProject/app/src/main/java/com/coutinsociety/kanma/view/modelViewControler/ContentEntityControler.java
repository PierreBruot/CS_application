package com.coutinsociety.kanma.view.modelViewControler;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Entity;

public class ContentEntityControler {

    public static View getContentEntity(Entity e, Context c){
        CoordinatorLayout layout=new CoordinatorLayout(c);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, c.getResources().getDisplayMetrics()));
        layout.setLayoutParams(params);
        layout.setId(e.getId());

        ImageView img=new ImageView(c);
        img.setLayoutParams(new CoordinatorLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, c.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, c.getResources().getDisplayMetrics())));

        ((CoordinatorLayout.LayoutParams)img.getLayoutParams()).gravity= Gravity.START|Gravity.CENTER_VERTICAL;
        if(e.getPicture()!=null){
            img.setImageBitmap(e.getPicture());
        }else {
            img.setImageResource(R.drawable.ic_user);
        }


        TextView title=new TextView(c);
        title.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((CoordinatorLayout.LayoutParams)title.getLayoutParams()).gravity=Gravity.END|Gravity.CENTER_VERTICAL;
        ((CoordinatorLayout.LayoutParams) title.getLayoutParams()).leftMargin=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, c.getResources().getDisplayMetrics());
        title.setText(e.getTitle());
        title.setTextSize(20);

        //Image
        layout.addView(img);
        layout.addView(title);

return layout;

    }

    public static View getContentEntityWithRemoveOption(Entity e, Context c){
        CoordinatorLayout layout=new CoordinatorLayout(c);
        layout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, c.getResources().getDisplayMetrics())));




        CoordinatorLayout left=((CoordinatorLayout)getContentEntity(e, c));
        layout.addView(left);
        left.getLayoutParams().width=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics()));

        ((CoordinatorLayout.LayoutParams)left.getLayoutParams()).gravity=Gravity.START|Gravity.CENTER_VERTICAL;

        TextView txt=new TextView(c);
        layout.addView((View)txt);
        txt.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT,CoordinatorLayout.LayoutParams.WRAP_CONTENT));
        ((CoordinatorLayout.LayoutParams)txt.getLayoutParams()).gravity=Gravity.END|Gravity.CENTER_VERTICAL;

        txt.setText("Remove");
        txt.setTextColor(Color.RED);



        return layout;
    }

    /*

    //TODO
    public static RelativeLayout getContentEntitySearch(Entity e, Context c){

        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout layout= getContentEntity(e,c);

        //layout.setLayoutParams(params);
        return layout;

    }

*/
}
