package com.example.dombeg.cashflowbanker;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Special extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_middle_player;
    private RelativeLayout vg_left_icons;
    private RelativeLayout vg_left_menu;
    private Animation act_blinking;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            for(int i=0; i<vg_left_menu.getChildCount(); i++)
            {
                if(arg0 == vg_left_menu.getChildAt(i) || arg0 == vg_left_icons.getChildAt(i)) {
                    switch (i) {
                        case 0:
                            act_game_player.change_wealth(act_game_player.income());
                            break;
                        case 1:
                            act_game_player.change_wealth(act_game_player.overall_outcome());
                            break;
                        case 2:

                            act_game_player.change_wealth(((-1) * act_game_player.overall_income() / 10));
                            break;
                        case 3:
                            act_game_player.add_kid();
                            break;
                    }
                    act_gamefile_overwrite(act_game_player);
                    finish();
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
    private void act_initialize_views()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Special);
        String text = "";
        int IconID = 0;

        vg_left_menu = new RelativeLayout(this);
        vg_middle_player = new RelativeLayout(this);
        vg_left_icons = new RelativeLayout(this);

        ImageView imvw_temp;
        TextView txvw_temp;
        //==========================================================================================
        for (int i = 0; i < 4; i++) {
            txvw_temp = new TextView(this);
            vg_left_menu.addView(txvw_temp);
        }
        for (int i = 0; i < 4; i++) {
            imvw_temp = new ImageView(this);
            vg_left_icons.addView(imvw_temp);
        }
        for (int i = 0; i < 1; i++) {
            imvw_temp = new ImageView(this);
            vg_middle_player.addView(imvw_temp);
        }
        //==========================================================================================
        for (int i = 0; i < vg_left_menu.getChildCount(); i++) {
            txvw_temp = (TextView) vg_left_menu.getChildAt(i);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextSize(25.0f);
            switch (i) {
                case 0:
                    text = "pobranie pensji";
                    txvw_temp.setTextColor(Color.rgb(255,210,161));
                    break;
                case 1:
                    text = "wymówienie";
                    txvw_temp.setTextColor(Color.rgb(187,194,223));
                    break;
                case 2:
                    text = "dobroczynność";
                    txvw_temp.setTextColor(Color.rgb(254,183,131));
                    break;
                case 3:
                    text = "narodziny";
                    txvw_temp.setTextColor(Color.rgb(187,194,223));
            }
            txvw_temp.setText(text);
        }
        layout.addView(vg_left_menu);
        //==========================================================================================
        for(int i=0; i<vg_left_icons.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_left_icons.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            switch (i) {
                case 0:
                    IconID = R.drawable.special0;
                    imvw_temp.setColorFilter(Color.rgb(255,210,161), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 1:
                    IconID = R.drawable.special2;
                    imvw_temp.setColorFilter(Color.rgb(187,194,223), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 2:
                    IconID = R.drawable.special1;
                    imvw_temp.setColorFilter(Color.rgb(254,183,131), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 3:
                    IconID = R.drawable.special3;
                    imvw_temp.setColorFilter(Color.rgb(187,194,223), PorterDuff.Mode.SRC_ATOP);
                    break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxHeight(size.x / 10);
        }
        layout.addView(vg_left_icons);
        //==========================================================================================
        for(int i=0; i<vg_middle_player.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_middle_player.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            imvw_temp.setImageResource(MiceDrawID[act_game_player.get_color()]);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxWidth(size.y / 3);
        }
        layout.addView(vg_middle_player);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable()
    {
        @Override
        public void run()
        {
            View temp;
            int count = vg_left_menu.getChildCount();
            if (vg_left_menu.getChildAt(count - 1).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                for (int j = 0; j < 3; j++) {
                    RelativeLayout rl_current = null;
                    switch (j) {
                        case 0:
                            rl_current = vg_left_menu;
                            break;
                        case 1:
                            rl_current = vg_left_icons;
                            break;
                        case 2:
                            rl_current = vg_middle_player;
                    }
                    count = rl_current != null ? rl_current.getChildCount() : 0;
                    rl_current.setMinimumWidth((j==0) ? size.x / 2 : size.x/4);
                    rl_current.setMinimumHeight(size.y);
                    rl_current.setX((j==0) ? 0 : size.x / 4 * (j+1));
                    for (int i = 0; i < count; i++) {
                        temp = rl_current.getChildAt(i);
                        if(j==0)temp.setX(rl_current.getMinimumWidth() - temp.getWidth());
                        else temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);
                        if (j != 2)
                            temp.setY((i + 1) * ((rl_current.getMinimumHeight() - (temp.getHeight() * count)) / (count + 1)) + i * temp.getHeight());
                        else
                            temp.setY((vg_middle_player.getMinimumHeight() - temp.getHeight()) / 2);

                        if (j % 2 == 1) temp.setAnimation(act_blinking);
                        if(j<3)temp.setOnClickListener(click_main);
                        temp.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__special);
        init_variables();
        act_game_player = act_gamefile_read_player();
        act_initialize();
        act_initialize_views();
    }
}
