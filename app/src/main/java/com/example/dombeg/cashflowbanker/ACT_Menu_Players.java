package com.example.dombeg.cashflowbanker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class ACT_Menu_Players extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private int img_selected = 0b1;
    private RelativeLayout vg_titles;
    private RelativeLayout vg_mice;
    private RelativeLayout vg_names;
    //==============================================================================================
    //==============================================================================================
    private View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent;
            for(int i=0; i<vg_mice.getChildCount(); i++)
            {
                if(arg0 == vg_mice.getChildAt(i))
                {
                    act_switch_state(i);
                }
            }
            if(arg0 == vg_titles.getChildAt(1))
            {
                intent = new Intent(getApplicationContext(),ACT_Game_Main.class);
                String gm_data = "";
                Random rand = new Random();
                int jobID;
                for(int i=0; i<6; i++)
                {
                    if((img_selected & 1<<i) == 0) continue;
                    gm_data += ((EditText)vg_names.getChildAt(i)).getText()+"\n";
                    gm_data += String.valueOf(i)+"\n";
                    jobID = Math.abs(rand.nextInt(12));
                    gm_data += String.valueOf(jobID)+"\n";
                    gm_data += "0\n";
                    gm_data += String.valueOf(cashflow_works[jobID].get_self_loanvector())+"\n";
                    gm_data += "0\n";
                    gm_data += "true\n";
                    gm_data += "0\n";
                    gm_data += "OVER\n";
                }
                gm_data += "END";
                writeToFile("game00.txt", gm_data);
                startActivity(intent);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_switch_state(int ID)
    {
        img_selected = img_selected == (img_selected  |  (1<<ID)) ? (img_selected  &  (~(1<<ID))) : (img_selected  |  (1<<ID));
        int temp_vector = img_selected;
        for(int i=0; i < vg_mice.getChildCount(); i++)
        {
            if(temp_vector % 2 == 1)
            {
                vg_names.getChildAt(i).setVisibility(View.VISIBLE);
                ((ImageView)vg_mice.getChildAt(i)).clearColorFilter();
            }
            else
            {
                vg_names.getChildAt(i).setVisibility(View.INVISIBLE);
                ((ImageView)vg_mice.getChildAt(i)).setColorFilter(Color.BLACK);
            }
            temp_vector /= 2;
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void act_initialize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Menu_Players);
        vg_mice = new RelativeLayout(this);
        vg_names = new RelativeLayout(this);
        vg_titles = new RelativeLayout(this);
        for(int i=0; i< 6; i++)
        {
            EditText ed_temp = new EditText(this);
            ImageView im_temp = new ImageView(this);
            vg_names.addView(ed_temp);
            vg_mice.addView(im_temp);
        }
        for(int i=0; i<2; i++)
        {
            TextView temp = new TextView(this);
            vg_titles.addView(temp);
        }

        String text;
        for(int i=0; i<vg_titles.getChildCount();i++)
        {
            TextView temp = (TextView)vg_titles.getChildAt(i);
            if(i == 0)
            {
                temp.setTextSize(50.0f);
                text = "wybierz graczy";
            }
            else
            {
                temp.setTextSize(30.0f);
                text = "rozpocznij grę";
                temp.setOnClickListener(click_main);
            }
            temp.setVisibility(View.INVISIBLE);
            temp.setAllCaps(true);
            temp.setText(text);
            temp.setTextColor(Color.WHITE);
        }
        for(int i=0; i<vg_mice.getChildCount(); i++)
        {
            EditText ed_temp = (EditText)vg_names.getChildAt(i);
            ImageView im_temp = (ImageView)vg_mice.getChildAt(i);

            ed_temp.setVisibility(View.INVISIBLE);
            ed_temp.setAllCaps(true);
            ed_temp.setTextColor(Color.WHITE);
            ed_temp.setTextSize(15.0f);
            text = "podaj imię";
            ed_temp.setImeOptions(EditorInfo.IME_ACTION_DONE);
            ed_temp.setSingleLine();
            ed_temp.setText(text);
            ed_temp.setOnClickListener(click_main);

            im_temp.setImageResource(MiceDrawID[i]);
            im_temp.setAdjustViewBounds(true);
            im_temp.setOnClickListener(click_main);
            im_temp.setMaxWidth(size.x / (vg_mice.getChildCount()));
        }
        layout.addView(vg_mice);
        layout.addView(vg_names);
        layout.addView(vg_titles);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable() {
        @Override
        public void run() {
            View temp;
            int count = vg_names.getChildCount();
            if (vg_names.getChildAt(count - 1).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                for (int j = 0; j < 3; j++) {
                    RelativeLayout rl_current = null;
                    switch (j) {
                        case 0:
                            rl_current = vg_titles;
                            break;
                        case 1:
                            rl_current = vg_mice;
                            break;
                        case 2:
                            rl_current = vg_names;
                            break;
                    }
                    if(rl_current == null) break;
                    count = rl_current.getChildCount();
                    rl_current.setMinimumWidth(size.x);
                    rl_current.setMinimumHeight(size.y);
                    for (int i = 0; i < count; i++) {
                        temp = rl_current.getChildAt(i);
                        if (j == 0) {
                            temp.setX((size.x - temp.getWidth())/2);
                            temp.setY((i==0) ? 0 : size.y-temp.getHeight());
                        } else {
                            temp.setX(size.x/vg_mice.getChildCount()*i+(size.x/vg_mice.getChildCount()-temp.getWidth())/2);
                            temp.setY((j==1) ? (size.y-temp.getHeight())/2 : ((i%2==0) ? vg_mice.getChildAt(i).getY()-temp.getHeight() : vg_mice.getChildAt(i).getY()+vg_mice.getChildAt(i).getHeight()));
                        }
                        temp.setVisibility(View.VISIBLE);
                    }
                }
                act_switch_state(0);
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
        setContentView(R.layout.activity_act__menu__number);
        init_variables();
        act_initialize();
    }
    //==============================================================================================
    //==============================================================================================
}
