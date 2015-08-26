package com.cinardere_ramazan_ba_2015.studienplanung;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toolbar;

import adapter.MyFragmentStatePageAdapter;

/**
 * Created by Ramazan Cinardere on 25.08.15.
 */




/**
 * Work with Fragment to show tabs
 * */

public class MyTabActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener{

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ActionBar actionBar = null;
    private ViewPager viewPager = null;

    private MyFragmentStatePageAdapter myFragmentStatePageAdapter = null;



    ////////////////////////////
    //       Constructor      //
    ////////////////////////////


    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view_pager);

        createMyTabs();

        myFragmentStatePageAdapter = new MyFragmentStatePageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.myViewPager_pager);

        viewPager.setAdapter(myFragmentStatePageAdapter);
        viewPager.setOnPageChangeListener(this);

    }

     private void createMyTabs() {

         actionBar = getSupportActionBar();
         actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

         android.support.v7.app.ActionBar.Tab[] tabs = new android.support.v7.app.ActionBar.Tab[3];

         for(int i = 0; i<tabs.length; i++) {
             tabs[i] = actionBar.newTab();
             tabs[i].setText("Tab " +i);
             tabs[i].setTabListener(this);

             actionBar.addTab(tabs[i]);
         }

     }


     @Override
     public void setActionBar(Toolbar toolbar) {


         Log.e("setActionBar()" ," " +toolbar.getChildAt(0).getClass().getName());

     }


     @Override
     public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabSelected() at position: " +tab.getPosition()," name: " +tab.getText());

         if(viewPager != null) {
             viewPager.setCurrentItem(tab.getPosition());
         }

     }

     @Override
     public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabUnselected() at position: " +tab.getPosition()," name: " +tab.getText());

     }

     @Override
     public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         Log.e("onTabReselected() at position: " +tab.getPosition()," name: " +tab.getText());


     }

     @Override
     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

     }

     @Override
     public void onPageSelected(int position) {
         getSupportActionBar().setSelectedNavigationItem(position);
     }

     @Override
     public void onPageScrollStateChanged(int state) {

     }


}






















/**
 *
 * @since Class to show Tabs with Activity
 *
 * public class MyTabActivity extends TabActivity {

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
 requestWindowFeature(Window.FEATURE_ACTION_BAR);

 super.onCreate(savedInstanceState);

 setContentView(R.layout.my_tabhost);

 // create the TabHost that will contain the Tabs
 TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


 TabSpec tab1 = tabHost.newTabSpec("First Tab");
 TabSpec tab2 = tabHost.newTabSpec("Second Tab");
 TabSpec tab3 = tabHost.newTabSpec("Third tab");

 // Set the Tab name and Activity
 // that will be opened when particular Tab will be selected
 tab1.setIndicator("Tab1");
 tab1.setContent(new Intent(this,TestActivity.class).putExtra("check", "Tab1"));

 tab2.setIndicator("Tab2");
 tab2.setContent(new Intent(this,TestActivity.class).putExtra("check", "Tab2"));

 tab3.setIndicator("Tab3");
 tab3.setContent(new Intent(this,TestActivity.class).putExtra("check", "Tab3"));

 //Add the tabs  to the TabHost to display.

 tabHost.addTab(tab1);
 tabHost.addTab(tab2);
 tabHost.addTab(tab3);
 }
 }
  *
  *
  * */

