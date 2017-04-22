package com.example.dombeg.cashflowbanker;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Owned extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_drawer;
    private RelativeLayout vg_folder;
    private RelativeLayout vg_middle_billing;
    private RelativeLayout vg_child_billing;
    private RelativeLayout vg_paper_list;
    private RelativeLayout vg_right_menu;
    //==============================================================================================
    //==============================================================================================
    private boolean paperlist_in_front = false;
    private boolean billing_in_front  = false;
    private int item_to_sell = -1;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0) {
            if (billing_in_front) {
                if (arg0 == vg_middle_billing.getChildAt(0)) billing_show(false, false);
                else if (arg0 == vg_middle_billing.getChildAt(2)) {
                    if (((TextView) arg0).getText().toString().length() < 5){
                        buy_item();
                        billing_show(false, false);
                    }
                    else if (((TextView) arg0).getText().length() >= 5){
                        sell_item(item_to_sell);
                        billing_show(false, false);
                    }
                }
            } else if (paperlist_in_front) {
                if (arg0 == vg_paper_list.getChildAt(0)) paperlist_show(false);
                else {
                    for (int i = 2; i < vg_paper_list.getChildCount(); i++) {
                        if (arg0 == vg_paper_list.getChildAt(i)) {
                            item_to_sell = arg0.getId();
                            paperlist_show(false);
                            billing_show(true, false);
                        }
                    }
                }
            } else {
                for (int i = 1; i < vg_drawer.getChildCount(); i++) {
                    if (arg0 == vg_drawer.getChildAt(i)) {
                        if (i != 4) {
                            if (i > act_opened_drawer) act_open_drawer(i + 1);
                            else act_open_drawer(i);
                        }
                        break;
                    }
                }
                if (arg0 == vg_folder.getChildAt(2)) {
                    act_rearrange(!act_folder_is_empty);
                }
                if (arg0 == vg_folder.getChildAt(3)) {
                    if (act_folder_is_empty) {
                        paperlist_show(true);
                    } else {
                        billing_show(true, true);
                    }
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    void buy_item()
    {
        int slot = act_game_player.get_free_buyable_index();
        if (slot == -1) return;
        String arg1 = ((EditText)vg_child_billing.getChildAt(1)).getText().toString();
        int arg2 = Integer.parseInt(((EditText) vg_child_billing.getChildAt(3)).getText().toString());
        int arg3 = Integer.parseInt(((EditText) vg_child_billing.getChildAt(5)).getText().toString());
        int arg4 = Integer.parseInt(((EditText) vg_child_billing.getChildAt(7)).getText().toString());
        Buyable by_temp = new Buyable(en_code.values()[act_opened_drawer-1],arg1,arg2,arg3,arg4);
        act_game_player.set_buyable(slot,by_temp);
    }
    //==============================================================================================
    //==============================================================================================
    void sell_item(int index)
    {
        int money = act_game_player.sell_buyable(index);
        act_game_player.change_wealth(money);
    }
    //==============================================================================================
    //==============================================================================================
    private void act_initialize_views()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Owned);
        int IconID = 0;
        String text;

        vg_folder = new RelativeLayout(this);
        vg_middle_billing = new RelativeLayout(this);
        vg_drawer = new RelativeLayout(this);
        vg_paper_list = new RelativeLayout(this);
        vg_right_menu = new RelativeLayout(this);
        vg_child_billing = new RelativeLayout(this);

        ImageView imvw_temp;
        TextView txvw_temp;
        EditText edvw_temp;
        //==========================================================================================
        for (int i = 0; i < 4; i++) {
            imvw_temp = new ImageView(this);
            vg_folder.addView(imvw_temp);
        }
        for (int i = 0; i < 5; i++) {
            imvw_temp = new ImageView(this);
            vg_drawer.addView(imvw_temp);
        }
        for (int i = 0; i < 2; i++) {
            txvw_temp = new TextView(this);
            vg_right_menu.addView(txvw_temp);
        }
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                imvw_temp = new ImageView(this);
                vg_middle_billing.addView(imvw_temp);
            } else {
                txvw_temp = new TextView(this);
                vg_middle_billing.addView(txvw_temp);
            }
        }
        for (int i = 0; i < 4; i++) {
            txvw_temp = new TextView(this);
            vg_child_billing.addView(txvw_temp);
            edvw_temp = new EditText(this);
            vg_child_billing.addView(edvw_temp);
        }
        for(int i=0; i<act_game_player.get_buyable_size(); i++) {
            if (i == 0) {
                imvw_temp = new ImageView(this);
                vg_paper_list.addView(imvw_temp);
            }
            txvw_temp = new TextView(this);
            vg_paper_list.addView(txvw_temp);
        }
        //==========================================================================================
        for(int i=0; i<vg_drawer.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_drawer.getChildAt(i);
            imvw_temp.setMaxHeight(size.y / 5);
            switch(i)
            {
                case 0: IconID = R.drawable.drawer_0; imvw_temp.setMaxHeight(size.y); break;
                case 1: IconID = R.drawable.drawer_1; break;
                case 2: IconID = R.drawable.drawer_1; break;
                case 3: IconID = R.drawable.drawer_1; break;
                case 4: IconID = R.drawable.drawer_1; break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
        }
        vg_drawer.setVisibility(View.INVISIBLE);
        layout.addView(vg_drawer);
        //==========================================================================================
        for(int i=0; i<vg_folder.getChildCount(); i++) {
            imvw_temp = (ImageView) vg_folder.getChildAt(i);
            imvw_temp.setMaxWidth(size.x / 4);
            switch(i)
            {
                case 0: IconID = R.drawable.folder_0; break;
                case 1: IconID = R.drawable.folder_1; break;
                case 2: IconID = R.drawable.folder_2; break;
                case 3: IconID = R.drawable.billing; break;
            }
            imvw_temp.setImageResource(IconID);
            imvw_temp.setAdjustViewBounds(true);
        }
        vg_folder.setVisibility(View.INVISIBLE);
        layout.addView(vg_folder);
        //==========================================================================================
        for(int i=0; i<vg_right_menu.getChildCount(); i++) {
            txvw_temp = (TextView) vg_right_menu.getChildAt(i);
            txvw_temp.setAllCaps(true);
            txvw_temp.setTextColor(Color.WHITE);
            txvw_temp.setTextSize(20.0f);
        }
        vg_right_menu.setVisibility(View.INVISIBLE);
        layout.addView(vg_right_menu);
        //==========================================================================================
        for(int i=0;i<vg_child_billing.getChildCount()-1; i++)
        {
            if(i % 2 == 0){
                txvw_temp = (TextView) vg_child_billing.getChildAt(i);
                txvw_temp.setAllCaps(true);
                txvw_temp.setTextColor(Color.BLACK);
                txvw_temp.setTextSize(15.0f);
                switch (i){
                    case 0:
                        text = "nazwa";
                        break;
                    case 2:
                        text = "ilość";
                        break;
                    case 4:
                        text = "koszt";
                        break;
                    case 6:
                        text = "dochód";
                        break;
                    default:
                        text = "default";
                }
                txvw_temp.setOnClickListener(click_main);
                txvw_temp.setText(text);
            }
            else{
                edvw_temp = (EditText) vg_child_billing.getChildAt(i);
                edvw_temp.setAllCaps(true);
                edvw_temp.setTextColor(Color.BLACK);
                edvw_temp.setTextSize(15.0f);
                edvw_temp.setSingleLine();
                edvw_temp.setInputType(InputType.TYPE_CLASS_NUMBER);
                edvw_temp.setImeOptions(EditorInfo.IME_ACTION_DONE);
                switch (i){
                    case 1:
                        text = "??";
                        edvw_temp.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    default:
                        text = "0";
                }
                edvw_temp.setOnClickListener(click_main);
                edvw_temp.setText(text);
            }
        }
        for(int i=0; i<vg_middle_billing.getChildCount(); i++) {
            if(i==0)
            {
                imvw_temp = (ImageView) vg_middle_billing.getChildAt(i);
                imvw_temp.setImageResource(R.drawable.billing);
                imvw_temp.setAdjustViewBounds(true);
                imvw_temp.setMaxHeight(size.y);
                imvw_temp.setOnClickListener(click_main);
            }
            else
            {
                txvw_temp = (TextView) vg_middle_billing.getChildAt(i);
                txvw_temp.setAllCaps(true);
                txvw_temp.setTextColor(Color.BLACK);
                txvw_temp.setTextSize(20.0f);
                txvw_temp.setOnClickListener(click_main);
            }
        }
        vg_child_billing.setVisibility(View.INVISIBLE);
        vg_middle_billing.setVisibility(View.INVISIBLE);
        vg_middle_billing.addView(vg_child_billing);
        layout.addView(vg_middle_billing);
        //==========================================================================================
        for(int i=0; i<vg_paper_list.getChildCount(); i++) {
            if(i==0)
            {
                imvw_temp = (ImageView) vg_paper_list.getChildAt(i);
                imvw_temp.setImageResource(R.drawable.note);
                imvw_temp.setAdjustViewBounds(true);
                imvw_temp.setMaxHeight(size.y);
                imvw_temp.setOnClickListener(click_main);
            }
            else
            {
                txvw_temp = (TextView) vg_paper_list.getChildAt(i);
                txvw_temp.setAllCaps(true);
                txvw_temp.setTextColor(Color.BLACK);
                txvw_temp.setTextSize(20.0f);
                txvw_temp.setOnClickListener(click_main);
            }
        }
        vg_paper_list.setVisibility(View.INVISIBLE);
        layout.addView(vg_paper_list);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable() {
        @Override
        public void run() {
            View temp;
            int count;
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            for (int j = 0; j < 2; j++) {
                RelativeLayout rl_current = null;
                switch (j) {
                    case 0:
                        rl_current = vg_drawer;
                        break;
                    case 1:
                        rl_current = vg_folder;
                        break;
                }
                if(rl_current == null) break;
                count = rl_current.getChildCount();
                rl_current.setMinimumWidth(size.x / 3);
                rl_current.setMinimumHeight(size.y);
                rl_current.setX(size.x / 3 * j);
                for (int i = 0; i < count; i++) {
                    temp = rl_current.getChildAt(i);
                    while (temp.getWidth() == 0) ;
                    if (j == 0) {
                        if (i == 0) {
                            temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);
                            temp.setY((rl_current.getMinimumHeight() - temp.getHeight()) / 2);
                        } else {
                            temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2 + temp.getWidth() * 0.135f);
                            temp.setY((i - 1) * temp.getHeight() * 1.03f + temp.getHeight() * 0.68f);
                        }
                    } else if (j == 1) {
                        temp.setX((rl_current.getMinimumWidth() - temp.getWidth()) / 2);
                        if (i < count - 1) {
                            temp.setY((rl_current.getMinimumHeight() / 2 - temp.getHeight()) / 2);
                        } else {
                            temp.setY(rl_current.getMinimumHeight() / 2);
                        }
                    }
                    temp.setOnClickListener(click_main);
                }
            }
            vg_drawer.setVisibility(View.VISIBLE);
            vg_folder.setVisibility(View.VISIBLE);
            vg_right_menu.setVisibility(View.VISIBLE);
        }
    };
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw_drawer = new Runnable() {
        @Override
        public void run() {
            View temp;
            int count = vg_drawer.getChildCount();
            if (vg_drawer.getChildAt(act_opened_drawer).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                vg_drawer.getChildAt(act_opened_drawer).bringToFront();
                int static_size = vg_drawer.getChildAt(1).getHeight();
                if(vg_drawer.getChildAt(2).getHeight() < static_size) static_size = vg_drawer.getChildAt(2).getHeight();
                int add = 0;
                for (int i = 1; i < count-1; i++) {
                    temp = vg_drawer.getChildAt(i);
                    if(i==act_opened_drawer)add++;
                    temp.setY((i+add - 1) * static_size * 1.03f + static_size * 0.68f);
                }
                temp = vg_drawer.getChildAt(4);
                temp.setY((act_opened_drawer - 1) * static_size * 1.03f + static_size * 0.68f);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private boolean act_folder_is_empty = false;
    private void act_rearrange(boolean emptyFolder) {
        act_folder_is_empty = emptyFolder;
        if (emptyFolder) {
            vg_folder.getChildAt(1).setVisibility(View.INVISIBLE);
            ((ImageView)vg_folder.getChildAt(3)).setImageResource(R.drawable.folder_1);
        } else {
            vg_folder.getChildAt(1).setVisibility(View.VISIBLE);
            ((ImageView)vg_folder.getChildAt(3)).setImageResource(R.drawable.billing);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void billing_setup(boolean buynotsell)
    {
        String text;
        View tx_vw;
        for(int i=1; i<vg_middle_billing.getChildCount()-1; i++) {
            tx_vw = vg_middle_billing.getChildAt(i);
            switch (i) {
                case 1:
                    switch (act_opened_drawer) {
                        case 1:
                            text = "Kolekcjonerskie";
                            break;
                        case 2:
                            text = "Nieruchomości i ziemie";
                            break;
                        case 3:
                            text = "Biznesy i spółki";
                            break;
                        case 4:
                            text = "Marzenia";
                            break;
                        default:
                            text = "Posiadane";
                    }
                    break;
                case 2:
                    text = buynotsell ? "Kup" : "Sprzedaj";
                    break;
                default:
                    text = "default";
            }
            ((TextView) tx_vw).setText(text);
        }
        for(int i=0;i<vg_child_billing.getChildCount(); i++)
        {
            if(i % 2 == 1){
                tx_vw = vg_child_billing.getChildAt(i);
                if(buynotsell){
                    switch (i){
                        case 1:
                            text = "??";
                            break;
                        default:
                            text = "0";
                    }
                }else{
                    switch (i){
                        case 1:
                            text = String.valueOf(act_game_player.get_buyable(item_to_sell).get_name());
                            break;
                        case 3:
                            text = String.valueOf(act_game_player.get_buyable(item_to_sell).get_quantity());
                            break;
                        case 5:
                            text = String.valueOf(act_game_player.get_buyable(item_to_sell).get_price());
                            break;
                        case 7:
                            text = String.valueOf(act_game_player.get_buyable(item_to_sell).get_income());
                            break;
                        default:
                            text = "0";
                    }
                }
                ((EditText) tx_vw).setText(text);
            }
        }
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable billing_draw = new Runnable()
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

                vg_middle_billing.setMinimumHeight(temp_vw.getHeight());
                vg_middle_billing.setMinimumWidth(temp_vw.getWidth());

                vg_middle_billing.setX((size.x - vg_middle_billing.getMinimumWidth()) / 2);
                vg_middle_billing.setY((size.y - vg_middle_billing.getMinimumHeight()) / 2);

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
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.72f);
                            break;
                        case 3:
                            temp_vw.setX(vg_middle_billing.getMinimumWidth() * 0.1f);
                            temp_vw.setY(vg_middle_billing.getMinimumHeight() * 0.30f);
                            break;
                    }
                }
                vg_child_billing.setMinimumWidth(vg_middle_billing.getMinimumWidth());
                vg_child_billing.setMinimumHeight((int)(vg_middle_billing.getMinimumHeight()*0.4f));
                for(int i=0; i<vg_child_billing.getChildCount(); i++) {
                    temp_vw = vg_child_billing.getChildAt(i);

                    temp_vw.setX((i % 4) * vg_child_billing.getMinimumWidth() / 5);
                    temp_vw.setY((i / 4) * vg_child_billing.getMinimumHeight() / 2);
                }
                vg_child_billing.setVisibility(View.VISIBLE);
                vg_middle_billing.setVisibility(View.VISIBLE);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private final Runnable paperlist_draw = new Runnable() {
        @Override
        public void run() {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            View temp_vw = vg_paper_list.getChildAt(0);

            while(temp_vw.getWidth()==0);

            vg_paper_list.setMinimumHeight(temp_vw.getHeight());
            vg_paper_list.setMinimumWidth(temp_vw.getWidth());

            vg_paper_list.setX((size.x - vg_paper_list.getMinimumWidth()) / 2);
            vg_paper_list.setY((size.y - vg_paper_list.getMinimumHeight()) / 2);
            for (int i = 1; i < vg_paper_list.getChildCount(); i++) {
                temp_vw = vg_paper_list.getChildAt(i);
                while(((TextView)temp_vw).getText().length()!=0 && temp_vw.getWidth()==0);
                if (i == 1) {
                    temp_vw.setX((vg_paper_list.getMinimumWidth() - temp_vw.getWidth()) / 2);
                    temp_vw.setY(vg_paper_list.getMinimumHeight() * 0.015f);
                } else {
                    temp_vw.setX(vg_paper_list.getMinimumWidth() * 0.1f);
                    temp_vw.setY(vg_paper_list.getMinimumHeight() * (0.015f + 0.0526f * i));
                }
            }
            vg_paper_list.setVisibility(View.VISIBLE);
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void billing_show(boolean show, boolean buynotsell)
    {
        View tx_vw;
        billing_in_front = show;
        if(show)
        {
            billing_setup(buynotsell);
            handle.post(billing_draw);
        }
        else
        {
            vg_middle_billing.setVisibility(View.INVISIBLE);
            vg_child_billing.setVisibility(View.INVISIBLE);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void paperlist_setup()
    {
        String text;
        View tx_vw;
        int j=0;
        for(int i=1; i<vg_paper_list.getChildCount() && j<vg_paper_list.getChildCount(); i++) {
            tx_vw = vg_paper_list.getChildAt(i);
            text = "";
            if(i==1) {
                switch (act_opened_drawer) {
                    case 1:
                        text = "Kolekcjonerskie";
                        break;
                    case 2:
                        text = "Nieruchomości i ziemie";
                        break;
                    case 3:
                        text = "Biznesy i spółki";
                        break;
                    case 4:
                        text = "Marzenia";
                        break;
                    default:
                        text = "Posiadane";
                }
            }
            else {
                for(; j<vg_paper_list.getChildCount(); j++)
                {
                    Buyable by_temp = act_game_player.get_buyable(j);
                    if (by_temp != null) {
                        if(by_temp.get_code().ordinal() == act_opened_drawer-1){
                            text = by_temp.Display();
                            tx_vw.setId(j);
                            j++;
                            break;
                        }
                    }
                }
            }
            ((TextView) tx_vw).setText(text);
            ((TextView) tx_vw).setTextSize(15.0f);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private void paperlist_show(boolean show)
    {
        View tx_vw;
        paperlist_in_front = show;
        if(show)
        {
            paperlist_setup();
            handle.post(paperlist_draw);
        }
        else
        {
            vg_paper_list.setVisibility(View.INVISIBLE);
        }
    }
    //==============================================================================================
    //==============================================================================================
    private int act_opened_drawer = 1;
    private void act_open_drawer(int index)
    {
        act_opened_drawer = index;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        for(int i=1; i<vg_drawer.getChildCount(); i++) {
            if (i == index) {
                ((ImageView) vg_drawer.getChildAt(i)).setImageResource(R.drawable.drawer_2);
                ((ImageView) vg_drawer.getChildAt(i)).setMaxHeight((int)(size.y*0.35f));
            } else {
                ((ImageView) vg_drawer.getChildAt(i)).setImageResource(R.drawable.drawer_1);
                ((ImageView) vg_drawer.getChildAt(i)).setMaxHeight(size.y/5);
            }
        }
        handle.post(act_draw_drawer);
    }
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__money__salary);
        init_variables();
        act_game_player = act_gamefile_read_player();
        act_initialize_views();
    }
}
