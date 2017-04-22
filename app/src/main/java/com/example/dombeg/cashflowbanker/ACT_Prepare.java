package com.example.dombeg.cashflowbanker;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;

public class ACT_Prepare extends AppCompatActivity
{
    private int no_of_players=2;
    private int no_of_player=1;
    private int starting_player = 0;
    private int stage = 0;
    private final Handler handle = new Handler();
    private final int[] colors = {Color.rgb(153, 0, 0), Color.rgb(0,0,153), Color.BLACK, Color.rgb(0,153,0), Color.rgb(204,82,0), Color.rgb(102,0,102)};
    private int colors_code = 0;
    private int colors_current = 1;

    private boolean color_used(int color_number)
    {
        int temp_color_code = colors_code;
        for(int i=0; i<no_of_players; i++)
        {
            if((temp_color_code % 10)  == color_number) return true;
            temp_color_code /= 10;
        }
        return false;
    }

    private final OnClickListener click = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            if(arg0.getId() == ViewID[1])
            {
                switch(stage)
                {
                    case 0:
                        no_of_players += 1;
                        if(no_of_players > 6)no_of_players = 2;
                        String text = String.valueOf(no_of_players);
                        ((TextView)findViewById(ViewID[1])).setText(text);
                        break;
                    case 1:
                        colors_current += 1;
                        if (colors_current > 6) colors_current = 1;
                        while(color_used(colors_current))
                        {
                            colors_current += 1;
                            if (colors_current > 6) colors_current = 1;
                        }
                        findViewById(R.id.prepare_screen).setBackgroundColor(colors[colors_current - 1]);
                        break;
                    case 2:
                        break;
                }
            }
            else if(arg0.getId() == ViewID[2])
            {
                switch(stage)
                {
                    case 0:
                        color_init();
                        stage += 1;
                        break;
                    case 1:
                        no_of_player += 1;
                        colors_code = (colors_code+colors_current)*10;
                        if(no_of_player > no_of_players)
                        {
                            stage += 1;
                            rand_init();
                        }
                        else
                        {
                            colors_current += 1;
                            if (colors_current > 6) colors_current = 1;
                            while(color_used(colors_current))
                            {
                                colors_current += 1;
                                if (colors_current > 6) colors_current = 1;
                            }
                            findViewById(R.id.prepare_screen).setBackgroundColor(colors[colors_current - 1]);
                            String text = "#" + String.valueOf(no_of_player);
                            ((TextView)findViewById(ViewID[1])).setText(text);
                        }
                        break;
                    case 2:
                        break;
                }
            }
            handle.post(number_show);
        }
    };

    private final int[] ViewID = {
            View.generateViewId(),
            View.generateViewId(),
            View.generateViewId()
    };

    private void number_init()
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.prepare_screen);
        TextView Title = new TextView(this);
        TextView Number = new TextView(this);
        TextView Next = new TextView(this);

        Title.setId(ViewID[0]);
        Title.setAllCaps(true);
        Title.setText(R.string.no_of_players);
        Title.setTextColor(Color.WHITE);
        Title.setTextSize(50.0f);

        Number.setId(ViewID[1]);
        Number.setText(String.valueOf(no_of_players));
        Number.setTextColor(Color.WHITE);
        Number.setTextSize(250.0f);

        Next.setId(ViewID[2]);
        Next.setAllCaps(true);
        String text = "OK";
        Next.setText(text);
        Next.setTextColor(Color.WHITE);
        Next.setTextSize(50.0f);

        Number.setOnClickListener(click);
        Next.setOnClickListener(click);
        layout.addView(Title);
        layout.addView(Number);
        layout.addView(Next);
        handle.post(number_show);
    }

    private final Runnable number_show = new Runnable() {
        @Override
        public void run()
        {
            View Title = findViewById(ViewID[0]);
            View Number = findViewById(ViewID[1]);
            View Next = findViewById(ViewID[2]);
            if(Next.getWidth() == 0) handle.postDelayed(this,50);
            else
            {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                Title.setX((size.x - Title.getWidth()) / 2);
                Title.setY(0);

                Number.setX((size.x - Number.getWidth()) / 2);
                Number.setY((size.y - Number.getHeight()) / 2);

                Next.setX((size.x - Next.getWidth()) / 2);
                Next.setY(size.y - Next.getHeight());
            }
        }
    };
    private void color_init()
    {
        String text = "kolor dla gracza";
        ((TextView)findViewById(ViewID[0])).setText(text);
        text = "#" + String.valueOf(no_of_player);
        ((TextView)findViewById(ViewID[1])).setText(text);
        findViewById(R.id.prepare_screen).setBackgroundColor(colors[colors_current-1]);
    }

    private void rand_init()
    {
        Random rand = new Random();
        starting_player = Math.abs(rand.nextInt(no_of_players)) + 1;

        int starting_color;

        int temp = colors_code;
        int digits = 0;
        while(temp > 0)
        {
            temp /= 10;
            digits += 1;
        }
        starting_color = (colors_code / (int)Math.pow(10,digits-starting_player)) % 10;

        String text = "zaczyna gracz";
        ((TextView)findViewById(ViewID[0])).setText(text);
        text = "#" + String.valueOf(starting_player);
        ((TextView) findViewById(ViewID[1])).setText(text);
        findViewById(R.id.prepare_screen).setBackgroundColor(colors[starting_color-1]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prepare_screen);

        number_init();
    }
    @Override
    public void onBackPressed() {
    }

}
