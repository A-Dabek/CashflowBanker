package com.example.dombeg.cashflowbanker;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Player extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_left_player;
    private RelativeLayout vg_right_billings;
    private RelativeLayout vg_middle_billing;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0) {
            if(arg0 == vg_left_player.getChildAt(0)) finish();
            else if (arg0 == vg_right_billings.getChildAt(1)) {
               act_billing_details(true,true);
            } else if (arg0 == vg_right_billings.getChildAt(3) || arg0 == vg_right_billings.getChildAt(2)) {
                act_billing_details(false, true);
            } else if (arg0 == vg_middle_billing.getChildAt(0) || arg0 == vg_right_billings.getChildAt(1)) {
                act_billing_details(false,false);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_initialize_views()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Player);
        String text;
        int IconID = 0;

        vg_left_player = new RelativeLayout(this);
        vg_middle_billing = new RelativeLayout(this);
        vg_right_billings = new RelativeLayout(this);

        ImageView imvw_temp;
        TextView txvw_temp;
        //==========================================================================================
        for (int i = 0; i < 1; i++) {
            imvw_temp = new ImageView(this);
            vg_left_player.addView(imvw_temp);
        }
        for (int i = 0; i < 2; i++) {
            txvw_temp = new TextView(this);
            vg_right_billings.addView(txvw_temp);

            imvw_temp = new ImageView(this);
            vg_right_billings.addView(imvw_temp);
        }
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                imvw_temp = new ImageView(this);
                vg_middle_billing.addView(imvw_temp);
            } else {
                txvw_temp = new TextView(this);
                vg_middle_billing.addView(txvw_temp);
            }
        }
        //==========================================================================================
        for (int i = 0; i < vg_right_billings.getChildCount()/2; i++) {
            txvw_temp = (TextView) vg_right_billings.getChildAt(i*2);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(20.0f);
            switch (i) {
                case 0:
                    text = "przychody";
                    break;
                case 1:
                    text = "rozchody";
                    break;
                default:
                    text = "default";
            }
            txvw_temp.setText(text);

            imvw_temp = (ImageView) vg_right_billings.getChildAt(i*2+1);
            imvw_temp.setVisibility(View.INVISIBLE);
            imvw_temp.setImageResource(R.drawable.billing);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxWidth(size.x / 3);
        }
        layout.addView(vg_right_billings);
        //==========================================================================================
        for(int i=0; i<vg_left_player.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_left_player.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            imvw_temp.setImageResource(MiceDrawID[act_game_player.get_color()]);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxHeight(size.y);
        }
        layout.addView(vg_left_player);
        //==========================================================================================
        for(int i=0; i<vg_middle_billing.getChildCount(); i++) {
            if(i==0)
            {
                imvw_temp = (ImageView) vg_middle_billing.getChildAt(i);
                imvw_temp.setVisibility(View.INVISIBLE);
                imvw_temp.setImageResource(R.drawable.billing);
                imvw_temp.setAdjustViewBounds(true);
                imvw_temp.setMaxHeight(size.y);
            }
            else
            {
                txvw_temp = (TextView) vg_middle_billing.getChildAt(i);
                txvw_temp.setVisibility(View.INVISIBLE);
                txvw_temp.setAllCaps(true);
                txvw_temp.setTextColor(Color.BLACK);
                txvw_temp.setTextSize(20.0f);
            }
        }
        layout.addView(vg_middle_billing);
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
            int count = vg_left_player.getChildCount();
            if (vg_left_player.getChildAt(count - 1).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                for (int j = 0; j < 2; j++) {
                    RelativeLayout rl_current = null;
                    switch (j) {
                        case 0:
                            rl_current = vg_left_player;
                            break;
                        case 1:
                            rl_current = vg_right_billings;
                            break;
                    }
                    if(rl_current == null) break;
                    count = rl_current.getChildCount();
                    rl_current.setMinimumWidth(size.x / 2);
                    rl_current.setMinimumHeight(size.y);
                    rl_current.setX(size.x / 2 * j);
                    for (int i = 0; i < count; i++) {
                        temp = rl_current.getChildAt(i);
                        temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);
                        if (j == 0)
                            temp.setY((rl_current.getMinimumHeight() - temp.getHeight()) / 2);
                        else if (i % 2 == 0) temp.setY(i * size.y / 4);
                        else
                            temp.setY((i - 1) / 2 * size.y / 2 + (size.y / 2 - temp.getHeight()) / 2);
                        temp.setOnClickListener(click_main);
                        temp.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_show_text(boolean income)
    {
        String text;
        View tx_vw;
        Work temp_job = cashflow_works[act_game_player.get_job()];
        for(int i=1; i<vg_middle_billing.getChildCount(); i++)
        {
            tx_vw = vg_middle_billing.getChildAt(i);
            if(i==0)continue;
            if(income)
            {
                switch(i)
                {
                    case 1:
                        text = "przychody";
                        break;
                    case 2:
                        text = "zarobki z zawodu " + temp_job.get_name() + ": $" + temp_job.get_salary();
                        break;
                    case 3:
                        text = "przychody pasywne: $" + act_game_player.buyable_income();
                        break;
                    case 4:
                        text = "oszczędności: $" + temp_job.get_savings();
                        break;
                    default:
                        text = "default";
                }
            }
            else
            {
                switch(i)
                {
                    case 1:
                        text = "rozchody";
                        break;
                    case 2:
                        text = "koszty kredytów: $" + (-1)*(temp_job.loan_outcome(act_game_player.get_loan_vector()) + act_game_player.get_bankloan_instal());
                        break;
                    case 3:
                        text = "koszt utrzymania dzieci: $" + temp_job.get_per_kid() +" x " + act_game_player.get_kids();
                        break;
                    case 4:
                        text = "podatki: $" + temp_job.overall_outcome(0);
                        break;
                    default:
                        text = "default";
                }
            }
            ((TextView)tx_vw).setText(text);
            ((TextView)tx_vw).setTextSize(15.0f);
        }
        handle.post(act_draw_details);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw_details = new Runnable()
    {
        @Override
        public void run()
        {
            if(vg_middle_billing.getChildAt(0).getWidth()==0)handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                View temp_vw = vg_middle_billing.getChildAt(0);
                temp_vw.setOnClickListener(click_main);

                vg_middle_billing.setMinimumHeight(temp_vw.getHeight());
                vg_middle_billing.setMinimumWidth(temp_vw.getWidth());

                vg_middle_billing.setX((size.x - vg_middle_billing.getMinimumWidth()) / 2);
                vg_middle_billing.setY((size.y - vg_middle_billing.getMinimumHeight()) / 2);

                temp_vw.setVisibility(View.VISIBLE);
                for(int i=1; i<vg_middle_billing.getChildCount(); i++) {
                    temp_vw = vg_middle_billing.getChildAt(i);
                    switch(i)
                    {
                        case 1:
                            temp_vw.setX((vg_middle_billing.getMinimumWidth() - temp_vw.getWidth())/2);
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.12f);
                            break;
                        case 2:
                            temp_vw.setX(vg_middle_billing.getMinimumWidth() * 0.1f);
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.32f);
                            break;
                        case 3:
                            temp_vw.setX(vg_middle_billing.getMinimumWidth() * 0.1f);
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.52f);
                            break;
                        case 4:
                            temp_vw.setX(vg_middle_billing.getMinimumWidth() * 0.1f);
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.72f);
                            break;
                    }
                    temp_vw.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_billing_details(boolean income, boolean show)
    {
        View tx_vw;
        if(show)
        {
            act_show_text(income);
            for(int i=0; i<vg_right_billings.getChildCount(); i++)
            {
                tx_vw = vg_right_billings.getChildAt(i);
                tx_vw.setAlpha(0.5f);
            }
        }
        else
        {
            for(int i=0; i<vg_right_billings.getChildCount(); i++)
            {
                tx_vw = vg_right_billings.getChildAt(i);
                tx_vw.setAlpha(1f);
            }
            for(int i=0; i<vg_middle_billing.getChildCount(); i++)
            {
                tx_vw = vg_middle_billing.getChildAt(i);
                tx_vw.setVisibility(View.INVISIBLE);
            }
        }
    }
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__player);
        init_variables();//przychody + 4, rozchody + 4, 3 obrazki
        act_game_player = act_gamefile_read_player();
        act_initialize_views();
    }
}
