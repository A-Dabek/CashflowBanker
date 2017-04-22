package com.example.dombeg.cashflowbanker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Menu_Main extends ACT_Parent {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_menu;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent = null;
            if(arg0 == vg_menu.getChildAt(0))
            {
                intent = new Intent(getApplicationContext(), ACT_Menu_Game.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            else if(arg0 == vg_menu.getChildAt(1))
            {
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            if(intent != null) startActivity(intent);
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_initialize()
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Menu_Main);
        vg_menu = new RelativeLayout(this);
        for(int i=0; i<3; i++)
        {
            TextView temp = new TextView(this);
            vg_menu.addView(temp);
        }

        String text = "";
        for(int i=0; i<vg_menu.getChildCount()-1; i++)
        {
            TextView temp = (TextView)vg_menu.getChildAt(i);
            temp.setAllCaps(true);
            temp.setVisibility(View.INVISIBLE);
            temp.setTextColor(Color.WHITE);
            switch(i)
            {
                case 0:
                    text = "graj";
                    temp.setTextSize(200.0f);
                    break;
                case 1:
                    text = "wyjdź";
                    temp.setTextSize(50.0f);
                    break;
                case 2:
                    text = "credits";
                    temp.setTextSize(10.0f);
                    break;
            }
            temp.setText(text);
            temp.setOnClickListener(click_main);
        }
        layout.addView(vg_menu);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable()
    {
        @Override
        public void run()
        {
            if(vg_menu.getChildAt(0).getWidth()==0)handle.post(this);
            else
            {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                vg_menu.setMinimumWidth(size.x);
                vg_menu.setMinimumHeight(size.y);

                View temp;
                float interval=0;
                float intervalStep;
                for(int i=0; i<vg_menu.getChildCount(); i++) {
                    temp = vg_menu.getChildAt(i);
                    interval += temp.getHeight();
                }
                interval = (size.y - interval)/(vg_menu.getChildCount()+1);
                intervalStep = interval;
                for(int i=0; i<vg_menu.getChildCount(); i++)
                {
                    temp = vg_menu.getChildAt(i);
                    temp.setX((size.x - temp.getWidth()) / 2);
                    temp.setY(interval);
                    temp.setVisibility(View.VISIBLE);
                    interval += temp.getHeight() + intervalStep;
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_act__menu_main);
        init_variables();
        act_initialize();
    }
    //==============================================================================================
    //==============================================================================================
}
