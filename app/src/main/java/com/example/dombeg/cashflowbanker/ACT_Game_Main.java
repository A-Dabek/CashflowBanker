package com.example.dombeg.cashflowbanker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Main extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_left_menu;
    private RelativeLayout vg_right_menu;
    private RelativeLayout vg_left_icons;
    private RelativeLayout vg_right_icons;
    private RelativeLayout vg_middle;
    //==============================================================================================
    //==============================================================================================
    private Animation act_blinking;
    private int act_saveslot = -1;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_left = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent;
            for(int i=0; i<vg_left_icons.getChildCount(); i++) {
                if ((arg0  == vg_left_icons.getChildAt(i)) || (arg0 == vg_left_menu.getChildAt(i))) {
                    switch (i) {
                        case 0:
                            intent = new Intent(getApplicationContext(), ACT_Game_Player.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), ACT_Game_Owned.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(), ACT_Game_Bank.class);
                            startActivity(intent);
                            break;
                    }
                    break;
                }
            }
        }
    };
    private final View.OnClickListener click_right = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent;
            for(int i=0; i<vg_left_icons.getChildCount(); i++) {
                if ((arg0 == vg_right_menu.getChildAt(i)) || (arg0 == vg_right_icons.getChildAt(i))) {
                    switch (i) {
                        case 0:
                            intent = new Intent(getApplicationContext(), ACT_Game_Special.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), ACT_Game_Transfer.class);
                            startActivity(intent);
                            break;
                        case 2:
                            act_update_views();
                            break;
                    }
                    break;
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_initialize()
    {
        act_blinking = new AlphaAnimation(1f, 0.5f);
        act_blinking.setInterpolator(new AccelerateInterpolator());
        act_blinking.setDuration(750);
        act_blinking.setRepeatCount(Animation.INFINITE);
        act_blinking.setRepeatMode(Animation.REVERSE);
    }
    //==============================================================================================
    //==============================================================================================
    private void act_initialize_views() {
        /*==============
        Mouse in the center with 8 options around pivot
        1. Billing info -> Mouse with diploma
        2. All Properties -> drawer with (dreams, shares, properties, biznesses)
        3. Loan management -> bank with (bank loan, pay other loan off)
        4. Special fields -> changing segment with (salary, sack, kid, charity)
        5. Money transfer
        6. End turn
        ==============*/
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Main);
        String text;
        int IconID = 0;

        vg_left_menu = new RelativeLayout(this);
        vg_right_menu = new RelativeLayout(this);
        vg_left_icons = new RelativeLayout(this);
        vg_right_icons = new RelativeLayout(this);
        vg_middle = new RelativeLayout(this);

        ImageView imvw_temp;
        TextView txvw_temp;
        //==========================================================================================
        for (int i = 0; i < 3; i++) {
            txvw_temp = new TextView(this);
            vg_left_menu.addView(txvw_temp);
        }
        for (int i = 0; i < 3; i++) {
            txvw_temp = new TextView(this);
            vg_right_menu.addView(txvw_temp);
        }
        for (int i = 0; i < 3; i++) {
            imvw_temp = new ImageView(this);
            vg_left_icons.addView(imvw_temp);
        }
        for (int i = 0; i < 3; i++) {
            imvw_temp = new ImageView(this);
            vg_right_icons.addView(imvw_temp);
        }
        for (int i = 0; i < 1; i++) {
            txvw_temp = new TextView(this);
            vg_middle.addView(txvw_temp);

            imvw_temp = new ImageView(this);
            vg_middle.addView(imvw_temp);
        }
        //==========================================================================================
        for (int i = 0; i < vg_left_menu.getChildCount(); i++) {
            txvw_temp = (TextView) vg_left_menu.getChildAt(i);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(20.0f);
            switch (i) {
                case 0:
                    text = "rachunek";
                    break;
                case 1:
                    text = "własności";
                    break;
                case 2:
                    text = "kredyty";
                    break;
                default:
                    text = "default";
            }
            txvw_temp.setText(text);

        }
        layout.addView(vg_left_menu);
        //==========================================================================================
        for (int i = 0; i < vg_right_menu.getChildCount(); i++) {
            txvw_temp = (TextView) vg_right_menu.getChildAt(i);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(20.0f);
            switch (i) {
                case 0:
                    text = "specjalne";
                    break;
                case 1:
                    text = "przelewy";
                    break;
                case 2:
                    text = "następny";
                    break;
                default:
                    text = "default";
            }
            txvw_temp.setText(text);
        }
        layout.addView(vg_right_menu);
        //==========================================================================================
        for(int i=0; i<vg_left_icons.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_left_icons.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            switch (i) {
                case 0:
                    IconID = R.drawable.main_icon0;
                    break;
                case 1:
                    IconID = R.drawable.main_icon1;
                    break;
                case 2:
                    IconID = R.drawable.main_icon2;
                    break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxHeight(size.x / 10);
        }
        layout.addView(vg_left_icons);
        //==========================================================================================
        for(int i=0; i<vg_right_icons.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_right_icons.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            switch (i) {
                case 0:
                    IconID = R.drawable.main_icon3;
                    break;
                case 1:
                    IconID = R.drawable.main_icon4;
                    break;
                case 2:
                    IconID = R.drawable.main_icon5;
                    break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxHeight(size.x / 10);
        }
        layout.addView(vg_right_icons);
        //==========================================================================================
        for(int i=0; i<vg_middle.getChildCount(); i++) {
            switch (i) {
                case 0:
                    txvw_temp = (TextView) vg_middle.getChildAt(i);
                    txvw_temp.setVisibility(View.INVISIBLE);
                    txvw_temp.setAllCaps(true);
                    txvw_temp.setTextColor(Color.WHITE);
                    txvw_temp.setTextSize(20.0f);
                    txvw_temp.setText(String.valueOf(act_game_player.get_name()));
                    break;
                case 1:
                    imvw_temp = (ImageView) vg_middle.getChildAt(i);
                    imvw_temp.setVisibility(View.INVISIBLE);
                    imvw_temp.setImageResource(MiceDrawID[act_game_player.get_color()]);
                    imvw_temp.setAdjustViewBounds(true);
                    imvw_temp.setMaxWidth(size.x / 5);
                    break;
            }
        }
        layout.addView(vg_middle);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable() {
        @Override
        public void run() {
            View temp;
            int count = vg_left_menu.getChildCount();
            if (vg_left_menu.getChildAt(count - 1).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                for (int j = 0; j < 5; j++) {
                    RelativeLayout rl_current = null;
                    switch (j) {
                        case 0:
                            rl_current = vg_left_menu;
                            break;
                        case 1:
                            rl_current = vg_left_icons;
                            break;
                        case 2:
                            rl_current = vg_middle;
                            break;
                        case 3:
                            rl_current = vg_right_icons;
                            break;
                        case 4:
                            rl_current = vg_right_menu;
                            break;
                    }
                    if(rl_current == null) break;
                    count = rl_current.getChildCount();
                    rl_current.setMinimumWidth(size.x / 5);
                    rl_current.setMinimumHeight(size.y);
                    rl_current.setX(size.x / 5 * j);
                    for (int i = 0; i < count; i++) {
                        temp = rl_current.getChildAt(i);
                        temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);
                        if (j != 2)
                            temp.setY((i + 1) * ((rl_current.getMinimumHeight() - (temp.getHeight() * count)) / (count + 1)) + i * temp.getHeight());
                        else
                            temp.setY((i == 0) ? 0 : (vg_middle.getMinimumHeight() - temp.getHeight()) / 2);

                        if (j % 2 == 1) temp.setAnimation(act_blinking);
                        if(j<3)temp.setOnClickListener(click_left);
                        else temp.setOnClickListener(click_right);
                        temp.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_update_views()
    {
        act_gamefile_update(act_game_player);
        act_game_player = act_gamefile_read_player();
        String text = String.valueOf(act_game_player.get_name());

        ((TextView)vg_middle.getChildAt(0)).setText(text);
        ((ImageView)vg_middle.getChildAt(1)).setImageResource(MiceDrawID[act_game_player.get_color()]);

        /*act_gamefile_overwrite(act_game_player);
        String data = player_data();
        data = data + "\n" + read_other_players("game00.txt");
        ((TextView) vg_middle.getChildAt(0)).setTextSize(5.0f);
        ((TextView)vg_middle.getChildAt(0)).setText(data);
        vg_middle.getChildAt(1).setVisibility(View.INVISIBLE);*/
    }
    //==============================================================================================
    //==============================================================================================
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onResume() {
        super.onResume();
        act_game_player = act_gamefile_read_player();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__main);
        init_variables();
        act_initialize();
        act_game_player = act_gamefile_read_player();
        act_initialize_views();
    }
}
