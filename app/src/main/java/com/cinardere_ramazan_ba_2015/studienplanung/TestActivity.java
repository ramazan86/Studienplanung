package com.cinardere_ramazan_ba_2015.studienplanung;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Ramazan Cinardere on 25.08.15.
 */
public class TestActivity extends ActionBarActivity {

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////


    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView tv=new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setText(getIntent().getExtras().getString("check"));

        setContentView(tv);


    }
}
