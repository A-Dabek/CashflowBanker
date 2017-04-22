package com.example.dombeg.cashflowbanker;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class ACT_Logo extends ACT_Parent {
    //==============================================================================================
    //==============================================================================================
    private RelativeLayout vg_logo;
    //==============================================================================================
    //==============================================================================================
    private final View.OnClickListener click_main = new View.OnClickListener()
    {
        @Override
        public void onClick(View arg0) {
            if (arg0 == vg_logo.getChildAt(0)) {
                handle.post(start_game);
            }
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void act_initialize_views()
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.home_screen);
        vg_logo = new RelativeLayout(this);
        ImageView ActImageView;
        ActImageView = new ImageView(this);
        ActImageView.setImageResource(R.drawable.logo);
        ActImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ActImageView.setAlpha(0.0f);
        ActImageView.setOnClickListener(click_main);
        vg_logo.addView(ActImageView);
        layout.addView(vg_logo);
        handle.post(act_draw);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable act_draw = new Runnable() {
        @Override
        public void run() {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            vg_logo.setMinimumWidth(size.x);
            vg_logo.setMinimumHeight(size.y);

            View ActView = vg_logo.getChildAt(0);
            ActView.setX(0.0f);
            ActView.setY(0.0f);
        }
    };
    //==============================================================================================
    //==============================================================================================
    private void animate()
    {
        View ActView = vg_logo.getChildAt(0);
        ActView.animate().alpha(1.0f).setDuration(1000);
        ActView.animate().setDuration(5000);
    }
    //==============================================================================================
    //==============================================================================================
    private final Runnable start_game = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), ACT_Menu_Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };
    //==============================================================================================
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
        init_variables();
        act_initialize_views();
        animate();
    }
    //==============================================================================================
    //==============================================================================================
}
