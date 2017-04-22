package com.example.dombeg.cashflowbanker;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ACT_Game_Transfer extends ACT_Parent_Game {
    //==============================================================================================
    //==============================================================================================
    private int act_view_number=0;
    private int[] IconID;
    private int[] ViewID;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            for(int i=0; i<act_view_number - IconID.length; i++)
            {
                if(arg0.getId() == ViewID[i])
                {
                    switch(i) {
                        case 3:
                            //wypłata
                            act_game_player.set_wealth(act_game_player.get_wealth() + Integer.parseInt(((EditText) findViewById(ViewID[5])).getText().toString()));
                            ((TextView) findViewById(ViewID[2])).setText("$" + act_game_player.get_wealth() + " wolnych środków");
                            act_gamefile_overwrite(act_game_player);
                            handle.post(act_draw);
                            break;
                        case 4:
                            act_game_player.set_wealth(act_game_player.get_wealth() - Integer.parseInt(((EditText) findViewById(ViewID[5])).getText().toString()));
                            ((TextView) findViewById(ViewID[2])).setText("$" + act_game_player.get_wealth() + " wolnych środków");
                            act_gamefile_overwrite(act_game_player);
                            handle.post(act_draw);
                            //wpłata
                            break;
                    }
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_initialize()
    {
        IconID[0] = R.drawable.monitor0;
        IconID[1] = R.drawable.monitor1;
    }
    //==============================================================================================
    //==============================================================================================
    private void act_initialize_views()
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ACT_Game_Transfer);
        EditText ActEdViews;
        TextView[]ActTViews = new TextView[act_view_number - IconID.length - 1];
        ImageView[]ActImViews = new ImageView[IconID.length];

        String text;
        for(int i=0; i<ActTViews.length; i++) {
            if (i < ActImViews.length) {
                ViewID[i + ActTViews.length + 1] = View.generateViewId();
                ActImViews[i] = new ImageView(this);
                ActImViews[i].setId(ViewID[i + ActTViews.length + 1]);
                ActImViews[i].setVisibility(View.INVISIBLE);
                ActImViews[i].setImageResource(IconID[i]);
                ActImViews[i].setAdjustViewBounds(true);
                layout.addView(ActImViews[i]);
            }
            if (i == 0) {
                ViewID[ActTViews.length] = View.generateViewId();
                ActEdViews = new EditText(this);
                ActEdViews.setId(ViewID[ActTViews.length]);
                ActEdViews.setVisibility(View.INVISIBLE);
                ActEdViews.setTextColor(Color.GREEN);
                ActEdViews.setTypeface(Typeface.MONOSPACE);
                ActEdViews.setTextSize(20.0f);
                ActEdViews.setText("10000");
                ActEdViews.setSingleLine();
                ActEdViews.setInputType(InputType.TYPE_CLASS_NUMBER);
                ActEdViews.setImeOptions(EditorInfo.IME_ACTION_DONE);
                layout.addView(ActEdViews);
            }
            ViewID[i] = View.generateViewId();
            ActTViews[i] = new TextView(this);
            ActTViews[i].setId(ViewID[i]);
            ActTViews[i].setVisibility(View.INVISIBLE);
            ActTViews[i].setTextColor(Color.GREEN);
            ActTViews[i].setTypeface(Typeface.MONOSPACE);
            ActTViews[i].setTextSize(15.0f);
            switch (i) {
                case 0:
                    ActTViews[i].setTextSize(25.0f);
                    ActTViews[i].setAllCaps(true);
                    text = "cashflow bank";
                    break;
                case 1:
                    text = act_game_player.get_name();
                    break;
                case 2:
                    text = "$" + act_game_player.get_wealth()+" wolnych środków";
                    break;
                case 3:
                    text = " /''''''''\\\n" +
                            "< POBIERZ  |\n" +
                            " \\......../";
                    break;
                case 4:
                    text = " /''''''''\\\n" +
                            "|  WPŁAĆ   >\n" +
                            " \\......../";
                    break;
                default:
                    text = "default";
            }
            ActTViews[i].setText(text);
            layout.addView(ActTViews[i]);
        }
        handle.post(act_init_2);
    }
    private final Runnable act_init_2 = new Runnable() {
        @Override
        public void run() {
            if (findViewById(ViewID[0]).getWidth() == 0) handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                for(int i=0;i<IconID.length;i++)
                {
                    ((ImageView)findViewById(ViewID[act_view_number - 1 - i])).setMaxWidth(size.x);
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
            if(findViewById(ViewID[act_view_number-1]).getWidth()==0)handle.post(this);
            else {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                View monitor = findViewById(ViewID[act_view_number-1]);
                float screenXratio = 472.0f/518.0f;
                float screenYratio = 268.0f/315.0f;
                float screenWidth = screenXratio * monitor.getWidth();
                float screenXoffset = (monitor.getWidth() - screenWidth)/2;
                float screenHeight = screenYratio * monitor.getHeight();
                float screenYoffset = (monitor.getHeight() - screenHeight)/2;

                View temp_vw;
                monitor.setX((size.x - monitor.getWidth()) / 2);
                monitor.setY((size.y - monitor.getHeight())/2);
                float intervalY = screenHeight / (((act_view_number-2)*2));
                float interval = 0;
                monitor.setVisibility(View.VISIBLE);
                for (int i = 0; i < act_view_number-1; i++)
                {
                    temp_vw = findViewById(ViewID[i]);
                    if(i<1)
                    {
                        temp_vw.setX(monitor.getX() + screenXoffset + (screenWidth-temp_vw.getWidth())/2);
                        temp_vw.setY(monitor.getY() + screenYoffset + interval);
                        interval += intervalY + temp_vw.getHeight();
                    }
                    else if(i<3)
                    {
                        temp_vw.setX(monitor.getX() + screenXoffset + screenWidth - temp_vw.getWidth());
                        temp_vw.setY(monitor.getY() + screenYoffset + interval);
                        interval += intervalY + temp_vw.getHeight();
                    }
                    else if(i==3)
                    {
                        temp_vw.setX(monitor.getX() + screenXoffset);
                        temp_vw.setY(monitor.getY() + monitor.getHeight() - screenYoffset - temp_vw.getHeight() - intervalY);

                        temp_vw.setOnClickListener(click_main);
                    }
                    else if(i==4)
                    {
                        temp_vw.setX(monitor.getX() + screenXoffset + screenWidth - temp_vw.getWidth());
                        temp_vw.setY(monitor.getY() + monitor.getHeight() - screenYoffset - temp_vw.getHeight() - intervalY);

                        temp_vw.setOnClickListener(click_main);
                    }
                    else if(i==5)
                    {
                        temp_vw.setX(monitor.getX() + screenXoffset + (screenWidth - temp_vw.getWidth())/2);
                        temp_vw.setY(monitor.getY() + screenYoffset + interval);
                    }
                    else {
                        temp_vw.setX((size.x - temp_vw.getWidth()) / 2);
                        temp_vw.setY((size.y - temp_vw.getHeight()) / 2);
                    }
                    temp_vw.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__game__money);
        init_variables();
        act_view_number = 8;
        ViewID = new int[act_view_number];
        IconID = new int[2];
        act_game_player = act_gamefile_read_player();
        act_initialize();
        act_initialize_views();
    }
}
