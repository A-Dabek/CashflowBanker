package com.example.dombeg.cashflowbanker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;

public class ACT_Menu_Load extends ACT_Parent {
    //==============================================================================================
    //==============================================================================================
    private final int act_file_number = 4;
    private final String[] FileID = new String[act_file_number];
    private RelativeLayout vg_menu;
    private RelativeLayout vg_icons;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent;
            for(int i=0; i<vg_menu.getChildCount(); i++)
            {
                if(arg0 == vg_menu.getChildAt(i)) {
                    String text;
                    text = readFromFile(FileID[i], 1, false);
                    writeToFile(FileID[i], "pusty zapis");
                    if (!text.equals("pusty zapis")) {
                        act_set_gamefile(i);
                        intent = new Intent(getApplicationContext(), ACT_Game_Main.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("savefile", i);
                        startActivity(intent);
                    }
                }
                else if(arg0 == vg_icons.getChildAt(i))
                {
                    File path, file;
                    path = getApplicationContext().getFilesDir();
                    file = new File(path, FileID[i-act_file_number]);
                    if(file.exists()) file.delete();
                    act_manage_files();
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_manage_files()
    {
        File path, file;
        String text;
        for(int i=0; i<vg_menu.getChildCount(); i++)
        {
            TextView TxVw = (TextView)vg_menu.getChildAt(i);
            FileID[i] = "save0" + String.valueOf(i) + ".txt";
            path = getApplicationContext().getFilesDir();
            file = new File(path, FileID[i]);
            if(!file.exists())
            {
                writeToFile(FileID[i],"pusty zapis");
            }
            text = readFromFile(FileID[i], 1, false);
            if(text.equals("pusty zapis"))
            {
                TxVw.setTextColor(Color.GRAY);
            }
            else
            {
                TxVw.setTextColor(Color.WHITE);
            }
            TxVw.setText(text);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void act_set_gamefile(int savefileID)
    {
        File path, savefile, gamefile;
        path = getApplicationContext().getFilesDir();
        savefile = new File(path, FileID[savefileID]);
        gamefile = new File(path, "game00.txt");
        if(gamefile.exists()) gamefile.delete();
        writeToFile(gamefile.getPath(), readFromFile(savefile.getPath(),0,true));
    }
    //==============================================================================================
    //==============================================================================================
    private void act_initialize()
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Menu_Load);
        vg_menu = new RelativeLayout(this);
        vg_icons = new RelativeLayout(this);
        for(int i=0; i<act_file_number; i++)
        {
            TextView temp = new TextView(this);
            vg_menu.addView(temp);
        }
        for(int i=0; i<act_file_number; i++)
        {
            ImageView temp = new ImageView(this);
            vg_icons.addView(temp);
        }

        for(int i=0; i<act_file_number; i++)
        {
            TextView tx_temp  = (TextView)vg_menu.getChildAt(i);
            ImageView im_temp = (ImageView)vg_icons.getChildAt(i);

            tx_temp.setVisibility(View.INVISIBLE);
            tx_temp.setAllCaps(true);
            tx_temp.setTextColor(Color.WHITE);
            tx_temp.setTextSize(40.0f);
            tx_temp.setOnClickListener(click_main);

            im_temp.setVisibility(View.INVISIBLE);
            im_temp.setImageResource(R.drawable.xmark);
            im_temp.setAdjustViewBounds(true);
            im_temp.setOnClickListener(click_main);
        }
        layout.addView(vg_icons);
        layout.addView(vg_menu);
        act_manage_files();
        handle.post(act_init_2);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_init_2 = new Runnable()
    {
        @Override
        public void run()
        {
            if(vg_menu.getChildAt(0).getWidth()==0)handle.post(this);
            else
            {
                ImageView t_IM;
                for(int i=0; i<vg_icons.getChildCount(); i++)
                {
                    t_IM = (ImageView)vg_icons.getChildAt(i);
                    t_IM.setMaxHeight(vg_menu.getChildAt(i).getHeight());
                }
                handle.post(act_draw);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable()
    {
        @Override
        public void run()
        {
            if(vg_icons.getChildAt(0).getWidth()==0)handle.post(this);
            else
            {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                vg_menu.setMinimumWidth(size.x);
                vg_menu.setMinimumHeight(size.y);

                vg_icons.setMinimumWidth(size.x);
                vg_icons.setMinimumHeight(size.y);

                View temp;
                View t_mark;
                for(int i=0; i<vg_menu.getChildCount(); i++)
                {
                    temp = vg_menu.getChildAt(i);
                    t_mark = vg_icons.getChildAt(i);

                    t_mark.setX(size.x / 2 - t_mark.getWidth());
                    t_mark.setY(size.x/act_file_number*i + (size.x / act_file_number - t_mark.getHeight())/2);

                    temp.setX(t_mark.getRight());
                    temp.setY(t_mark.getY());
                    temp.setVisibility(View.VISIBLE);
                    t_mark.setVisibility(View.VISIBLE);
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
        setContentView(R.layout.activity_act__load);
        init_variables();
        act_initialize();
    }
    //==============================================================================================
    //==============================================================================================
}
