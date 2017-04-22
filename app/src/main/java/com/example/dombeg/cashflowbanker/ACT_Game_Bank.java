package com.example.dombeg.cashflowbanker;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Bank extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private int active_biling = 0;
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_left_bills;
    private RelativeLayout vg_right_bills;
    private RelativeLayout vg_middle_cash;
    private RelativeLayout vg_middle_billing;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0) {
            for (int i = 0; i < vg_left_bills.getChildCount(); i++) {
                if (arg0 == vg_left_bills.getChildAt(i)) {
                    active_biling = i/2;
                    act_fade_billings(false);
                    act_show_text(i/2);
                    break;
                } else if (arg0 == vg_right_bills.getChildAt(i)) {
                    active_biling = i/2 + vg_left_bills.getChildCount()/2;
                    act_fade_billings(false);
                    act_show_text(i/2 + vg_left_bills.getChildCount()/2);
                    break;
                }
            }
            if (arg0 == vg_middle_billing.getChildAt(0)) {
                act_fade_billings(true);
            } else if (arg0 == vg_middle_billing.getChildAt(vg_middle_billing.getChildCount() - 1)) {
                if (act_game_player.get_loan_amount(active_biling) == 0 && active_biling == 5)
                    act_game_player.take_up_loan(20000);
                else act_game_player.pay_off_loan(active_biling);
                act_gamefile_overwrite(act_game_player);
                act_fade_billings(true);
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
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Bank);
        String text;
        int IconID = 0;

        vg_left_bills = new RelativeLayout(this);
         vg_right_bills = new RelativeLayout(this);
         vg_middle_cash = new RelativeLayout(this);
         vg_middle_billing = new RelativeLayout(this);

        ImageView imvw_temp;
        TextView txvw_temp;
        //==========================================================================================
        for (int i = 0; i < 3; i++) {
            txvw_temp = new TextView(this);
            vg_left_bills.addView(txvw_temp);

            imvw_temp = new ImageView(this);
            vg_left_bills.addView(imvw_temp);
        }
        for (int i = 0; i < 3; i++) {
            txvw_temp = new TextView(this);
            vg_right_bills.addView(txvw_temp);

            imvw_temp = new ImageView(this);
            vg_right_bills.addView(imvw_temp);
        }
        for (int i = 0; i < 7; i++) {
            imvw_temp = new ImageView(this);
            vg_middle_cash.addView(imvw_temp);
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
        for (int i = 0; i < vg_left_bills.getChildCount()/2; i++) {
            txvw_temp = (TextView) vg_left_bills.getChildAt(i*2);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(15.0f);
            switch (i) {
                case 0:
                    text = "samochód";
                    break;
                case 1:
                    text = "hipoteka";
                    break;
                case 2:
                    text = "studencki";
                    break;
                default:
                    text = "default";
            }
            txvw_temp.setText(text);

            imvw_temp = (ImageView) vg_left_bills.getChildAt(i*2+1);
            imvw_temp.setVisibility(View.INVISIBLE);
            imvw_temp.setImageResource(R.drawable.billing);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxWidth(size.x / 4);
        }
        layout.addView(vg_left_bills);
        //==========================================================================================
        for (int i = 0; i < vg_right_bills.getChildCount()/2; i++) {
            txvw_temp = (TextView) vg_right_bills.getChildAt(2*i);
            txvw_temp.setVisibility(View.INVISIBLE);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(15.0f);
            switch (i) {
                case 0:
                    text = "karta kredytowa";
                    break;
                case 1:
                    text = "konsumencki";
                    break;
                case 2:
                    text = "bankowy";
                    break;
                default:
                    text = "default";
            }
            txvw_temp.setText(text);

            imvw_temp = (ImageView) vg_right_bills.getChildAt(2*i+1);
            imvw_temp.setVisibility(View.INVISIBLE);
            imvw_temp.setImageResource(R.drawable.billing);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxWidth(size.x / 4);
        }
        layout.addView(vg_right_bills);
        //==========================================================================================
        for(int i=0; i<vg_middle_cash.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_middle_cash.getChildAt(i);
            imvw_temp.setVisibility(View.INVISIBLE);
            switch (i) {
                case 0:
                    IconID = R.drawable.suitcase;
                    break;
                case 1:
                    IconID = R.drawable.loan_0;
                    break;
                case 2:
                    IconID = R.drawable.loan_1;
                    break;
                case 3:
                    IconID = R.drawable.loan_2;
                    break;
                case 4:
                    IconID = R.drawable.loan_3;
                    break;
                case 5:
                    IconID = R.drawable.loan_5;
                    break;
                case 6:
                    IconID = R.drawable.loan_6;
                    break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
            imvw_temp.setMaxHeight(size.x / 3);
        }
        layout.addView(vg_middle_cash);
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
    private final Runnable act_draw = new Runnable() {
        @Override
        public void run() {
            View temp;
            int count = vg_left_bills.getChildCount();
            if (vg_left_bills.getChildAt(count - 1).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                for (int j = 0; j < 3; j++) {
                    RelativeLayout rl_current = null;
                    switch (j) {
                        case 0:
                            rl_current = vg_left_bills;
                            break;
                        case 1:
                            rl_current = vg_middle_cash;
                            break;
                        case 2:
                            rl_current = vg_right_bills;
                            break;
                    }
                    count = (rl_current != null) ? rl_current.getChildCount() : 0;
                    rl_current.setMinimumWidth(size.x / 3);
                    rl_current.setMinimumHeight(size.y);
                    rl_current.setX(size.x / 3 * j);
                    for (int i = 0; i < count; i++) {
                        temp = rl_current.getChildAt(i);
                        temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);

                        if (j % 2 == 0) {
                            if (i == 0) temp.setY(0);
                            else
                                temp.setY(rl_current.getChildAt(i - 1).getY() + rl_current.getChildAt(i - 1).getHeight());
                            if(i % 2 == 1)temp.setOnClickListener(click_main);
                        } else {
                            temp.setY((rl_current.getMinimumHeight() - temp.getHeight()) / 2);
                        }
                        temp.setVisibility(View.VISIBLE);
                    }
                }
                act_check_payments();
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_hide_text()
    {
        View tx_vw;
        for(int i=0; i<vg_middle_billing.getChildCount(); i++)
        {
            tx_vw = vg_middle_billing.getChildAt(i);
            tx_vw.setOnClickListener(null);
            tx_vw.setVisibility(View.INVISIBLE);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void act_show_text(int index)
    {
        String text;
        View tx_vw;
        for(int i=0; i<vg_middle_billing.getChildCount(); i++)
        {
            tx_vw = vg_middle_billing.getChildAt(i);
            if(i==0)continue;
            switch(i)
            {
                case 1:
                    text = "Kredyt ";
                    switch (index)
                    {
                        case 0:
                            text += "za samochód";
                            break;
                        case 1:
                            text += "hipoteczny";
                            break;
                        case 2:
                            text += "studencki";
                            break;
                        case 3:
                            text += "za kartę";
                            break;
                        case 4:
                            text += "kosumencki";
                            break;
                        case 5:
                            text += "bankowy";
                            break;
                    }
                    break;
                case 2:
                    text = "Wielkość kredytu: $" + act_game_player.get_loan_amount(index);
                    break;
                case 3:
                    text = "Wysokość raty: $"  + act_game_player.get_loan_instalment(index);
                    break;
                case 4:
                    text = (act_game_player.get_loan_amount(index)!=0) ? "spłać" : "pobierz";
                    tx_vw.setOnClickListener(click_main);
                    break;
                default:
                    text = "default";
            }
            ((TextView)tx_vw).setText(text);
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
                for(int i=1; i<vg_middle_billing.getChildCount(); i++)
                {
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
    private void act_fade_billings(boolean reverse)
    {
        View im_vw;
        for(int i=0; i< vg_left_bills.getChildCount(); i++) {
            im_vw = vg_left_bills.getChildAt(i);
            if(reverse)im_vw.setAlpha(1.0f);
            else im_vw.setAlpha(0.5f);

            im_vw = vg_right_bills.getChildAt(i);
            if(reverse)im_vw.setAlpha(1.0f);
            else im_vw.setAlpha(0.5f);
        }
        if(reverse) {
            act_check_payments();
            act_hide_text();
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void act_check_payments() {
        View temp_vw;
        for (int i = 1; i < vg_middle_cash.getChildCount(); i++) {
            temp_vw = vg_middle_cash.getChildAt(i);
            if ((act_game_player.get_loan_vector() & (1 << i-1)) > 0) {
                temp_vw.setVisibility(View.INVISIBLE);
                if (i < vg_middle_cash.getChildCount() / 2 + 1) {
                    temp_vw = vg_left_bills.getChildAt(i * 2 - 1);
                } else {
                    temp_vw = vg_right_bills.getChildAt((i-3)*2 - 1);
                }
                temp_vw.setAlpha(1.0f);
            } else {
                temp_vw.setVisibility(View.VISIBLE);
                if (i < vg_middle_cash.getChildCount() / 2 + 1) {
                    temp_vw = vg_left_bills.getChildAt(i * 2 - 1);
                } else {
                    temp_vw = vg_right_bills.getChildAt((i-3)*2 - 1);
                }
                temp_vw.setAlpha(0.5f);
                temp_vw.setOnClickListener(null);
            }
        }
    }
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__money__debts);
        init_variables();
        act_game_player = act_gamefile_read_player();
        act_initialize_views();
    }
}
